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

package com.Suirui.net;

public class Config {
    public static final String URLFilePath = "E:/data/china/retry/v1/";
    public static final String URLFilePath2 = "E:/data/china/retry/v2/";
    public static final String URLFilePathdone = "E:/data/china/retry/done/";
    public static final String URLFilePatherror = "E:/data/china/retry/error/";
	public static final String PROXY_DIR = "E:/data/china/proxy/";
	public static final String DEFAULT_HTTP_METHOD = "GET";
	public static String DEFAULT_USER_AGENT="Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:36.0) Gecko/20100101 Firefox/36.0";
    public static int MAX_RECEIVE_SIZE = 1000 * 1000;
    public static long requestMaxInterval=1000*60*2;
    public static int retry=3;
    public static long WAIT_THREAD_END_TIME=100000*60;
    public static int MAX_REDIRECT=10;    
     public static int TIMEOUT_CONNECT = 5000;
     public static int TIMEOUT_READ = 20000;
     public static int MAX_RETRY=50;

}
