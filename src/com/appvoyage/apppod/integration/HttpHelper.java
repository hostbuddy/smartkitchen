package com.appvoyage.apppod.integration;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.net.URL;

import com.appvoyage.apppod.integration.POSClient.Response;

public class HttpHelper {

	private HttpClient client;
	public HttpHelper() {
               ClassLoader classLoader = com.appvoyage.apppod.integration.HttpHelper.class.getClassLoader();
               URL resource = classLoader.getResource("org/apache/http/impl/io/DefaultHttpRequestWriterFactory.class");
               System.out.println("###############DefaultHttpRequestWriterFactory coming from=> #############");
               System.out.println(resource);

               resource = classLoader.getResource("org/apache/http/impl/conn/PoolingHttpClientConnectionManager.class");
               System.out.println("###############org/apache/http/impl/conn/PoolingHttpClientConnectionManager coming from=> #############");
               System.out.println(resource);

               resource = classLoader.getResource("org/apache/http/impl/conn/ManagedHttpClientConnectionFactory.class");
               System.out.println("###############org/apache/http/impl/conn/ManagedHttpClientConnectionFactory coming from=> #############");
               System.out.println(resource);

               resource = classLoader.getResource("com/sun/jersey/server/impl/model/method/dispatch/ResourceJavaMethodDispatcher.class");
               System.out.println("###############com/sun/jersey/server/impl/model/method/dispatch/ResourceJavaMethodDispatcher coming from=> #############");
               System.out.println(resource);

               resource = classLoader.getResource("org/apache/http/message/BasicLineFormatter.class");
               System.out.println("############### org/apache/http/message/BasicLineFormatter coming from=> #############");
               System.out.println(resource);
             
		client = HttpClientBuilder.create().setUserAgent("NextSeat 2.0 Client").build();
	}
	
	protected void setHeaders(HttpRequestBase request, String accessToken) {
		request.setHeader("Accept", "application/json");
		request.setHeader("Authorization", "Bearer " + accessToken);
	}
	
	public Response getWithHeaders(String url, String headerAuthKey) {
		int statusCode = 500;
		String responseStr = null;
		try {
			HttpGet request = new HttpGet(url);
			setHeaders(request,headerAuthKey);
			
			RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(3000)
                    .setConnectTimeout(3000)
                    .build();
			
			request.setConfig(requestConfig);
			System.out.println("Header req = = = = "+request.getAllHeaders());
			HttpResponse response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			responseStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(statusCode, responseStr);
	}
	
	public Response getWithoutHeaders(String url) {
		int statusCode = 500;
		String responseStr = null;
		try {
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			responseStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(statusCode, responseStr);
	}

	public Response postWithHeaders(String url, String payload, String headerAuthKey) {
		int statusCode = 500;
		String responseStr = null;
		try {
			HttpPost request = new HttpPost(url);
			setHeaders(request, headerAuthKey);
			request.setEntity(new StringEntity(payload));

			HttpResponse response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			responseStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(statusCode, responseStr);
	}
	
	public Response deleteWithHeaders(String url, String headerAuthKey) {
		int statusCode = 500;
		String responseStr = null;
		try {
			HttpDelete request = new HttpDelete(url);
			setHeaders(request, headerAuthKey);

			HttpResponse response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			responseStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(statusCode, responseStr);
	}
	
	public Response post(String url, String payload) {
		int statusCode = 500;
		String responseStr = null;
		try {
			HttpPost request = new HttpPost(url);
			request.addHeader("content-type", "application/json");
			request.setEntity(new StringEntity(payload));

			HttpResponse response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			responseStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(statusCode, responseStr);
	}
	
	public Response get(String url) {
		int statusCode = 500;
		String responseStr = null;
		try {
			HttpGet request = new HttpGet(url);
			request.addHeader("content-type", "application/json");
			
			HttpResponse response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			responseStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(statusCode, responseStr);
	}
}
