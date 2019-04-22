package com.hostbuddy.smartkitchen.service;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MessageListenerWebhook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

		JSONObject json = new JSONObject();
		JsonObject obj = new JsonParser().parse(getRequestBody(req)).getAsJsonObject();

		try {
			
			String message = (obj.has("user-message") && obj.get("user-message") != null
					&& !obj.get("user-message").toString().isEmpty()) ? obj.get("user-message").toString() : null;
			String type = (obj.has("type") && obj.get("type") != null
					&& !obj.get("type").toString().isEmpty()) ? (String) obj.get("type").toString() : null;
			String merchantName = (obj.has("merchant-name") && obj.get("merchant-name") != null
					&& !obj.get("merchant-name").toString().isEmpty()) ? (String) obj.get("merchant-name").toString()
							: null;
			String developmentId = (obj.has("development-id") && obj.get("development-id") != null
					&& !obj.get("development-id").toString().isEmpty()) ? (String) obj.get("development-id").toString()
							: null;
			String orderID = (obj.has("orderId") && obj.get("orderId") != null
							&& !obj.get("orderId").toString().isEmpty()) ? (String) obj.get("orderId").toString()
									: null;
			type = type.replace('\"', ' ').trim();
			message = message.replace('\"', ' ').trim();
			developmentId = developmentId.replace('\"', ' ').trim();
			merchantName = merchantName.replace('\"', ' ').trim();
			
			if (message != null && type != null && developmentId != null
					&& merchantName != null && orderID != null) {
				System.out.println("Successful call to webhook");
				
				//TODO
				// Do something to send message to your customer with given order number for given merchant
				
				json.put("Code", 200);
				json.put("type", "Success");
				json.put("message", "Message sent successfully");
				res.setContentType("application/json");
				res.getWriter().write(json.toString());
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "wrong parameters!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "in exception");
		}
	}
	
	private String getRequestBody(final HttpServletRequest request) {
		final StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = request.getReader();
			if (reader == null) {
				System.out.println("Request body could not be read because it's empty.");
				return null;
			}
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (final Exception e) {
			System.out.println("Could not obtain the saml request body from the http request" + e);
			return null;
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
