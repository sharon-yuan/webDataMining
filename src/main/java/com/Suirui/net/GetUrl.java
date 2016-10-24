package com.Suirui.net;

import java.util.regex.*;

import org.springframework.context.support.StaticApplicationContext;

public class GetUrl {
	public static String fromHref(String href, String superUrl) {
		if(href.contains("#"))return href+superUrl;
		String anString = null;
		Pattern p = Pattern.compile("href=\".*amp");
		Matcher m = p.matcher(href);
		if (m.find()) {
			anString = m.group();
			anString = anString.substring(6, anString.length());
			return anString;
		} else
			System.err.println("not find url from" + href);

		return anString;

	}

	public static String addSupandHref(String sub, String sup) {
		if(sub.contains("#"))return sup+sub;
		if (sub.startsWith("http"))
			return sub;
		if (sub.startsWith("/")){
			if(htmlRoot(sup).endsWith("/"))sub=sub.substring(1);
			return htmlRoot(sup) + sub;}
		if (sub.startsWith("./")) {
			while (sub.startsWith("./")) {
				sup = htmlSuper(sup);
				sub = sub.substring(2);
			}
			return sup + sub;
		}
		if(!sup.endsWith("/"))
		{
			
			if(sub.startsWith(".")||sub.startsWith("/"));
			else {
				return htmlSuper(sup)+sub;}
		}
		return sub;

	}

	public static String htmlSuper(String aString) {
		String regEx = "/[^/]+$";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(aString.substring(0, aString.length() - 1));

		if (mat.find()) {
			aString = aString.substring(0, mat.start() + 1);
		}
		return aString;
	}

	public static String htmlRoot(String aString) {
		String regEx = "^(https?://)?[a-z\\.-]*/";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(aString);

		if (mat.find()) {
			aString = aString.substring(mat.start(), mat.end());
		}
		return aString;
	}

	
}
