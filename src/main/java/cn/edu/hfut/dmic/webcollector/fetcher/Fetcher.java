/*
 * Copyright (C) 2014 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cn.edu.hfut.dmic.webcollector.fetcher;

import cn.edu.hfut.dmic.webcollector.generator.StandardGenerator;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequester;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Suirui.net.Config;

/**
 * deep web
 *
 * @author hu
 */
public class Fetcher {

	public static final Logger LOG = LoggerFactory.getLogger(Fetcher.class);

	public DbUpdater dbUpdater = null;

	public HttpRequester httpRequester = null;

	public VisitorFactory visitorFactory = null;

	private AtomicInteger activeThreads;
	private AtomicInteger spinWaiting;
	private AtomicLong lastRequestStart;
	private QueueFeeder feeder;
	private FetchQueue fetchQueue;
	private int retry = Config.MAX_RETRY;

	/**
	 *
	 */
	public static final int FETCH_SUCCESS = 1;

	/**
	 *
	 */
	public static final int FETCH_FAILED = 2;
	private int threads = 50;
	private boolean isContentStored = false;

	/**
	 *
	 */
	public static class FetchItem {

		/**
		 *
		 */
		public CrawlDatum datum;

		/**
		 *
		 * @param datum
		 */
		public FetchItem(CrawlDatum datum) {
			this.datum = datum;
		}
	}

	/**
	 *
	 */
	public static class FetchQueue {

		/**
		 *
		 */
		public AtomicInteger totalSize = new AtomicInteger(0);

		/**
		 *
		 */
		public final List<FetchItem> queue = Collections.synchronizedList(new LinkedList<FetchItem>());

		/**
		 *
		 */
		public void clear() {
			queue.clear();
		}

		/**
		 *
		 * @return
		 */
		public int getSize() {
			return queue.size();
		}

		/**
		 *
		 * @param item
		 */
		public synchronized void addFetchItem(FetchItem item) {
			if (item == null) {
				return;
			}
			queue.add(item);

			totalSize.incrementAndGet();
		}

		/**
		 *
		 * @return
		 */
		public synchronized FetchItem getFetchItem() {
			if (queue.isEmpty()) {
				return null;
			}
			return queue.remove(0);
		}

		/**
		 *
		 */
		public synchronized void dump() {
			for (int i = 0; i < queue.size(); i++) {
				FetchItem it = queue.get(i);
				LOG.info("  " + i + ". " + it.datum.getUrl());
			}

		}

	}

	/**
	 *
	 */
	public static class QueueFeeder extends Thread {

		/**
		 *
		 */
		public FetchQueue queue;

		/**
		 *
		 */
		public StandardGenerator generator;

		/**
		 *
		 */
		public int size;

		/**
		 *
		 * @param queue
		 * @param generator
		 * @param size
		 */
		public QueueFeeder(FetchQueue queue, StandardGenerator generator, int size) {
			this.queue = queue;
			this.generator = generator;
			this.size = size;
		}

		public void stopFeeder() {

			while (this.isAlive()) {
				try {
					Thread.sleep(1000);
					LOG.info("stopping feeder......");
				} catch (InterruptedException ex) {
				}
			}
		}

		public boolean running = true;

		@Override
		public void run() {

			boolean hasMore = true;
			running = true;
			while (hasMore && running) {

				int feed = size - queue.getSize();
				if (feed <= 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
					}
					continue;
				}
				while (feed > 0 && hasMore && running) {

					CrawlDatum datum = generator.next();
					hasMore = (datum != null);

					if (hasMore) {
						queue.addFetchItem(new FetchItem(datum));
						feed--;
					}

				}

			}
			generator.close();

		}

	}

	private class FetcherThread extends Thread {

		@Override
		public void run() {
			activeThreads.incrementAndGet();
			FetchItem item = null;
			try {

				while (running) {
					try {
						item = fetchQueue.getFetchItem();
						if (item == null) {
							if (feeder.isAlive() || fetchQueue.getSize() > 0) {
								spinWaiting.incrementAndGet();

								try {
									Thread.sleep(500);
								} catch (Exception ex) {
								}

								spinWaiting.decrementAndGet();
								continue;
							} else {
								return;
							}
						}

						lastRequestStart.set(System.currentTimeMillis());

						String url = item.datum.getUrl();

						HttpResponse response = null;
						CrawlDatum crawlDatum = null;

						int retryCount = 0;

						Exception lastException = null;
						for (; retryCount <= retry; retryCount++) {

							// for (; retryCount <= 500; retryCount++) {
							if (retryCount > 0) {
								@SuppressWarnings("unused")
								String suffix = "th ";
								switch (retryCount) {
								case 1:
									suffix = "st ";
									break;
								case 2:
									suffix = "nd ";
									break;
								case 3:
									suffix = "rd ";
									break;
								default:
									suffix = "th ";
								}

								// LOG.info("retry " + retryCount + suffix+
								// url);
							}
							try {
								response = httpRequester.getResponse(url);
								break;
							} catch (Exception ex) {
								lastException = ex;
								@SuppressWarnings("unused")
								String logMessage = "fetch " + url + " failed," + ex.toString();
								if (retryCount < retry) {
									logMessage += "   retry";
								}
								// LOG.info(logMessage);
							}
						}

						if (response != null) {
							LOG.info("fetch " + url);
							crawlDatum = new CrawlDatum(url, CrawlDatum.STATUS_DB_FETCHED,
									item.datum.getRetry() + retry);

							if (PagenumberGetter.dfgk(url) != 0) {
								File failedUrlDir = new File(Config.URLFilePathdone);
								if (!failedUrlDir.isDirectory())
									failedUrlDir.mkdirs();
								File failedUrlFile = new File(Config.URLFilePathdone + PagenumberGetter.dfgk(url));
								if (failedUrlFile.exists()) {
									failedUrlDir = new File(Config.URLFilePatherror);
									if (!failedUrlDir.isDirectory())
										failedUrlDir.mkdirs();
									failedUrlFile = new File(Config.URLFilePatherror + PagenumberGetter.dfgk(url));
								}
								BufferedWriter output = new BufferedWriter(
										new OutputStreamWriter(new FileOutputStream(failedUrlFile), "utf-8"));
								output.write(url);
								output.close();
							}
						} else {
							LOG.info("failed " + PagenumberGetter.dfgk(url) + "/" + item.datum.getRetry() + " "
									+ lastException.toString());
							crawlDatum = new CrawlDatum(url, CrawlDatum.STATUS_DB_UNFETCHED,
									item.datum.getRetry() + retry);
							if (crawlDatum.getRetry() <= Config.MAX_RETRY) {

								FetchItem FailItem = new FetchItem(crawlDatum);
								fetchQueue.addFetchItem(FailItem);
							} else {
								if (PagenumberGetter.dfgk(url) != 0) {

									File failedUrlDir = new File(Config.URLFilePath);
									if (!failedUrlDir.isDirectory())
										failedUrlDir.mkdirs();
									File failedUrlFile = new File(Config.URLFilePath + PagenumberGetter.dfgk(url));
									if (failedUrlFile.exists()) {
										failedUrlDir = new File(Config.URLFilePath2);
										if (!failedUrlDir.isDirectory())
											failedUrlDir.mkdirs();
										failedUrlFile = new File(Config.URLFilePath2 + PagenumberGetter.dfgk(url));
									}
									BufferedWriter output = new BufferedWriter(
											new OutputStreamWriter(new FileOutputStream(failedUrlFile), "utf-8"));
									output.write(url);
									output.close();
								}
							}

						}

						try {

							dbUpdater.getSegmentWriter().wrtieFetch(crawlDatum);
							if (response == null) {

								continue;
							}
							if (response.getRedirect()) {
								if (response.getRealUrl() != null) {
									dbUpdater.getSegmentWriter().writeRedirect(response.getUrl().toString(),
											response.getRealUrl().toString());
								}
							}
							String contentType = response.getContentType();
							Visitor visitor = visitorFactory.createVisitor(url, contentType);

							Page page = new Page();
							page.setUrl(url);
							page.setResponse(response);
							if (visitor != null) {
								Links nextLinks = null;
								try {

									nextLinks = visitor.visitAndGetNextLinks(page);
								} catch (Exception ex) {
									LOG.info("Exception", ex);
								}

								if (nextLinks != null && !nextLinks.isEmpty()) {

									dbUpdater.getSegmentWriter().wrtieLinks(nextLinks);

								}
							}

						} catch (Exception ex) {
							LOG.info("Exception", ex);

						}

					} catch (Exception ex) {
						LOG.info("Exception", ex);
					}
				}

			} catch (Exception ex) {
				LOG.info("Exception", ex);

			} finally {
				activeThreads.decrementAndGet();
			}

		}

	}

	private void before() throws Exception {
		// DbUpdater recoverDbUpdater = createRecoverDbUpdater();

		try {

			if (dbUpdater.isLocked()) {
				dbUpdater.merge();
				dbUpdater.unlock();
			}

		} catch (Exception ex) {
			LOG.info("Exception", ex);
		}

		dbUpdater.lock();
		dbUpdater.getSegmentWriter().init();
		running = true;
	}

	/**
	 *
	 * 
	 * @param generator
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void fetchAll(StandardGenerator generator) throws Exception {
		if (visitorFactory == null) {
			LOG.info("Please specify a VisitorFactory!");
			return;
		}
		before();

		lastRequestStart = new AtomicLong(System.currentTimeMillis());

		activeThreads = new AtomicInteger(0);
		spinWaiting = new AtomicInteger(0);
		fetchQueue = new FetchQueue();
		feeder = new QueueFeeder(fetchQueue, generator, 1000);
		feeder.start();

		FetcherThread[] fetcherThreads = new FetcherThread[threads];
		for (int i = 0; i < threads; i++) {
			fetcherThreads[i] = new FetcherThread();
			fetcherThreads[i].start();
		}

		do {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
			}
			LOG.info("-activeThreads=" + activeThreads.get() + ", spinWaiting=" + spinWaiting.get()
					+ ", fetchQueue.size=" + fetchQueue.getSize());

			if (!feeder.isAlive() && fetchQueue.getSize() < 5) {
				fetchQueue.dump();
			}

			if ((System.currentTimeMillis() - lastRequestStart.get()) > Config.requestMaxInterval) {
				LOG.info("Aborting with " + activeThreads + " hung threads.");
				System.out.println("Aborting with " + activeThreads + " hung threads.");
				break;
			}

		} while (activeThreads.get() > 0 && running);
		running = false;
		long waitThreadEndStartTime = System.currentTimeMillis();
		if (activeThreads.get() > 0) {
			LOG.info("wait for activeThreads to end");
		}

		while (activeThreads.get() > 0) {
			LOG.info("-activeThreads=" + activeThreads.get());
			try {
				Thread.sleep(500);
			} catch (Exception ex) {
			}
			if (System.currentTimeMillis() - waitThreadEndStartTime > Config.WAIT_THREAD_END_TIME) {
				LOG.info("kill threads");
				for (int i = 0; i < fetcherThreads.length; i++) {
					if (fetcherThreads[i].isAlive()) {
						try {
							fetcherThreads[i].stop();
							LOG.info("kill thread " + i);
						} catch (Exception ex) {
							LOG.info("Exception", ex);
						}
					}
				}
				break;
			}
		}
		LOG.info("clear all activeThread");
		feeder.stopFeeder();
		fetchQueue.clear();
		after();

	}

	boolean running;

	public void stop() {
		running = false;
	}

	private void after() throws Exception {

		dbUpdater.close();
		dbUpdater.merge();
		dbUpdater.unlock();

	}

	/**
	 *
	 *
	 * @return
	 */
	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public boolean isIsContentStored() {
		return isContentStored;
	}

	public void setIsContentStored(boolean isContentStored) {
		this.isContentStored = isContentStored;
	}

	public DbUpdater getDbUpdater() {
		return dbUpdater;
	}

	public void setDbUpdater(DbUpdater dbUpdater) {
		this.dbUpdater = dbUpdater;
	}

	public HttpRequester getHttpRequester() {
		return httpRequester;
	}

	public void setHttpRequester(HttpRequester httpRequester) {
		this.httpRequester = httpRequester;
	}

	public VisitorFactory getVisitorFactory() {
		return visitorFactory;
	}

	public void setVisitorFactory(VisitorFactory visitorFactory) {
		this.visitorFactory = visitorFactory;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {

		this.retry = retry;
	}

}
