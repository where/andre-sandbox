package com.andre.http;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.StatusLine;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;

/**
 * Generic HTTP client - all request and response content is byte array.
 * @author amesarovic
 */
// TODO: Clean up RuntimeException throws
public class RestHttpClient {
	private static final Logger logger = Logger.getLogger(RuntimeException.class);
	private MultiThreadedHttpConnectionManager httpConnectionManager;
	private HttpClient httpClient ;
	private static final int SOCKET_TIMEOUT = 5000;
	private static final int CONNECTION_TIMEOUT = 5000 ;
	private int socketTimeoutMillis = SOCKET_TIMEOUT;
	private int connectionTimeoutMillis = CONNECTION_TIMEOUT;
	private int maxTotalConnections = 50;
	private int maxConnectionsPerHost = 10;

	public RestHttpClient() {
		init();
	}

	public RestHttpClient(int socketTimeoutMillis, int connectionTimeoutMillis,
			int maxTotalConnections, int maxConnectionsPerHost) {
		this.socketTimeoutMillis = socketTimeoutMillis;
		this.connectionTimeoutMillis = connectionTimeoutMillis;
		this.maxTotalConnections = maxTotalConnections;
		this.maxConnectionsPerHost = maxConnectionsPerHost;
		init();
	}

	private void init() {
		httpConnectionManager = new MultiThreadedHttpConnectionManager();
		httpConnectionManager.getParams().setConnectionTimeout(connectionTimeoutMillis);
		httpConnectionManager.getParams().setSoTimeout(socketTimeoutMillis);
		httpConnectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
		//httpConnectionManager.getParams().setDefaultMaxPerRoute(
		//httpConnectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
		httpClient = new HttpClient(httpConnectionManager);
	}

	public Result get(String url, Header... headers) throws IOException {
		GetMethod method = new GetMethod(url);
		addHeaders(method,headers);
		try {
			int statusCode = httpClient.executeMethod(method);
			InputStream stream = method.getResponseBodyAsStream();
			if (stream == null)
				throw new IOException("No response for "+url);
			byte [] bytes = IOUtils.toByteArray(stream);
			return new Result(bytes, method);
		} finally {
			method.releaseConnection();
		}
	}

	public Result post(String url, byte [] value, Header... headers) throws IOException {
		logger.debug("put: url="+url);
		PostMethod method = new PostMethod(url);
		addHeaders(method,headers);
		try {
			RequestEntity rentity = new ByteArrayRequestEntity(value);
			method.setRequestEntity(rentity);
			//logger.debug("url="+url);
			int statusCode = httpClient.executeMethod(method);
			InputStream stream = method.getResponseBodyAsStream();
			if (stream == null)
				throw new RuntimeException("Put for url="+url+" failed. HTTP StatusCode="+statusCode);
			byte [] bytes = IOUtils.toByteArray(stream);
			return new Result(bytes, method);
		} finally {
			method.releaseConnection();
		}
	}

	public Result put(String url, byte [] value, Header... headers) throws IOException {
		logger.debug("put: url="+url);
		PutMethod method = new PutMethod(url);
		addHeaders(method,headers);
		try {
			RequestEntity rentity = new ByteArrayRequestEntity(value);
			method.setRequestEntity(rentity);
			int statusCode = httpClient.executeMethod(method);
			InputStream stream = method.getResponseBodyAsStream();
			if (stream == null)
				throw new RuntimeException("Put for url="+url+" failed. HTTP StatusCode="+statusCode);
			byte [] bytes = IOUtils.toByteArray(stream);
			return new Result(bytes, method);
		} finally {
			method.releaseConnection();
		}
	}

	public Result delete(String url) throws IOException {
		DeleteMethod method = new DeleteMethod(url);
		try {
			int statusCode = httpClient.executeMethod(method);
			return new Result(null, method);
		} finally {
			method.releaseConnection();
		}
	}

	public static boolean isError(int statusCode) {
		return statusCode < 200 || statusCode > 299 ;
	}

	public static void checkStatus(int statusCode, String url, String method) {
		if (isError(statusCode))
			throw new RuntimeException(method+" failed for "+url+". HTTP StatusCode="+statusCode);
	}

	public static void checkStatus2(HttpMethodBase method, String url, String methodName) {
		if (isError(method.getStatusCode()))
			throw new RuntimeException(methodName+" failed for "+url+". StatusLine: "+method.getStatusLine());
	}

	public static class Result {
		public Result(byte [] response, HttpMethodBase method) {
			this.response = response ;
			this.method = method ;
			this.statusCode = method.getStatusCode();
		}
		public byte [] response ;
		public int statusCode ;
		public HttpMethodBase method ;

		public String getHeaderValue(String headerName) {
			Header header = method.getResponseHeader(headerName);
			if (header == null)
				return null;
			HeaderElement [] elts = header.getElements();
			if (elts == null || elts.length == 0) 
				return null;
			return elts[0].getName();
		}

		@Override
		public String toString() {
			return 
				"statusCode="+statusCode
				+" method="+method
				+" response="+new String(response)
				;
		}
	}

	private void addHeaders(HttpMethodBase method, Header [] headers) {
		if (headers != null) {
			for (Header header : headers)
				method.addRequestHeader(header);
		}
	}
}
