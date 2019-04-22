package com.hostbuddy.smartkitchen.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import com.hostbuddy.smartkitchen.integration.HttpHelper;

public class CallAPI  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PRINT = "PRINT";
	
	private static final String MESSAGE = "MESSAGE";
	
	public static final String SERVER_PATH_URL_MAIN = "https://www.hostbuddy.info";

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		JSONObject json = new JSONObject();
		String devKey = req.getParameter("devKey");
		String email = req.getParameter("email");
		String merchant = req.getParameter("merchant");
		String orderId = req.getParameter("orderId");
		String fifthParam = req.getParameter("fifthParam");
		String api = req.getParameter("api");
		
		try {
			System.err.println("HERE IS ADD" + devKey +"===="
					+ email +"===="
					+ orderId +"===="
					+ merchant +"===="
					+ fifthParam +"====");
			if(devKey!=null && email!=null && merchant!=null && orderId!=null && fifthParam!=null && !devKey.isEmpty() && !email.isEmpty() && !merchant.isEmpty() && !orderId.isEmpty() && !fifthParam.isEmpty()) {
				if(api!=null) {
					if(api.equals(PRINT)) {
						com.hostbuddy.smartkitchen.integration.POSClient.Response response = callAPI(devKey, email, merchant, orderId, fifthParam, SERVER_PATH_URL_MAIN+"/print", "order-url");
						System.err.println("HERE IS PRINT");
						if(response.getStatusCode() == 200) {
							json.put("STATUS", "SUCCESS");
							json.put("MESSAGE", "API CALL SUCCESSFUL");
							json.put("RESPONSE", (org.json.simple.JSONObject) JSONValue.parse(response
					                .getResponse()));
						} else {
							json.put("STATUS", "FAILED");
							json.put("MESSAGE", "API Failed because page error " + response.getStatusCode());
						}
					} else if (api.equals(MESSAGE)) {
						com.hostbuddy.smartkitchen.integration.POSClient.Response response = callAPI(devKey, email, merchant, orderId, fifthParam, SERVER_PATH_URL_MAIN+"/message", "message");
						System.err.println("HERE IS MESSAGE SENT");
						if(response.getStatusCode() == 200) {
							json.put("STATUS", "SUCCESS");
							json.put("MESSAGE", "API CALL SUCCESSFUL");
							json.put("RESPONSE", (org.json.simple.JSONObject) JSONValue.parse(response
					                .getResponse()));
						} else {
							json.put("STATUS", "FAILED");
							json.put("MESSAGE", "API Failed because page error " + response.getStatusCode());
						}
					} else {
						json.put("STATUS", "FAILED");
						json.put("MESSAGE", "Please select proper API");
					}
				} else {
					json.put("STATUS", "FAILED");
					json.put("MESSAGE", "Please select API");
				}
			} else {
				json.put("STATUS", "FAILED");
				json.put("MESSAGE", "Please enter all the parameters");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.err.println("HERE IS ERROR");
			e.printStackTrace();
			try {
			json.put("STATUS", "FAILURE");
			json.put("MESSAGE", e.getMessage());
			} catch(Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			resp.setContentType("application/json");
			resp.getWriter().write(json.toString());
		}
	}
	
	public com.hostbuddy.smartkitchen.integration.POSClient.Response callAPI (String devKey, String email, String merchant, String orderId, String fifthParam, String url, String fifthParamKey) {
		try {
			HttpHelper httpHelper = new HttpHelper();
	        org.json.simple.JSONObject request = new org.json.simple.JSONObject();
	        request.put(fifthParamKey, fifthParam);
	        request.put("orderId", orderId);
	        request.put("development-key", devKey);
	        request.put("development-id", email);
	        request.put("merchant-name", merchant);
			com.hostbuddy.smartkitchen.integration.POSClient.Response response = httpHelper
	                .post(url, request.toJSONString());
	        System.out.println(response.toString());
	        System.out.println(request.toString());
	        return response;
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		doPost(req, resp);
	}

}
