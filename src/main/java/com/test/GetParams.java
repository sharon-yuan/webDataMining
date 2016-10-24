package com.test;
    				
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetParams extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetParams() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.println("GET " + request.getRequestURL() + " "
				+ request.getQueryString());

		Map<String, String[]> params = request.getParameterMap();
		String queryString = "";
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				queryString += key + "=" + value + "&";
			}
		}
	
		queryString = queryString.substring(0, queryString.length() - 1);
		writer.println("GET " + request.getRequestURL() + " " + queryString);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		Map<String, String[]> params = request.getParameterMap();
		String queryString = "";
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				queryString += key + "=" + value + "&";
			}
		}

		queryString = queryString.substring(0, queryString.length() - 1);
		writer.println("POST " + request.getRequestURL() + " " + queryString);
	}
	
	public static void main(String []args) {
		GetParams gParams=new GetParams();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			gParams.doPost(request, response);
		} catch (ServletException  e) {
			
			e.printStackTrace();
		}
		 catch ( IOException e) {
				
				e.printStackTrace();
			}
		
	}
}

	    			