/*
 * Copyright (C) 2015 hu
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
package cn.edu.hfut.dmic.webcollector.crawler;

import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BreadthCrawler extends DeepCrawler {


    public static final Logger LOG = LoggerFactory.getLogger(BreadthCrawler.class);

    protected boolean autoParse;

    protected RegexRule regexRule = new RegexRule();

    public BreadthCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath);
        this.autoParse = autoParse;
    }

    /**
     *
     * @param page
     * @return
     */
    public Links visitAndGetNextLinks(Page page) {
        Links nextLinks = new Links();
        if (autoParse) {
            String conteType = page.getResponse().getContentType();
            if (conteType != null && conteType.contains("text/html")) {
                Document doc = page.getDoc();
                if (doc != null) {
                    nextLinks.addAllFromDocument(page.getDoc(), regexRule);
                }
            }
        }
        try {
            visit(page, nextLinks);
        } catch (Exception ex) {
            LOG.info("Exception", ex);
        }
        return nextLinks;
    }

    /**
     * 
     * @param page
     * @param nextLinks 
     */
    public abstract void visit(Page page, Links nextLinks);

    /**
     *
     * @param urlRegex
     */
    public void addRegex(String urlRegex) {
        regexRule.addRule(urlRegex);
    }

    /**
     *
     * @return
     */
    public boolean isAutoParse() {
        return autoParse;
    }

    /**
     * 
     * @param autoParse
     */
    public void setAutoParse(boolean autoParse) {
        this.autoParse = autoParse;
    }

    /**
     *
     * @return
     */
    public RegexRule getRegexRule() {
        return regexRule;
    }

    /**
     *
     * @param regexRule
     */
    public void setRegexRule(RegexRule regexRule) {
        this.regexRule = regexRule;
    }

	

    
}
