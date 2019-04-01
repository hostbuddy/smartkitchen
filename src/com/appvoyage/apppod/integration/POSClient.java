package com.appvoyage.apppod.integration;

public interface POSClient {
	
	HttpHelper httpHelper = new HttpHelper();
	
	public static class Response {
		int statusCode;
		String response;
		
		public Response(int statusCode, String response) {
			this.statusCode = statusCode;
			this.response = response;
		}

		public int getStatusCode() {
			return statusCode;
		}

		public String getResponse() {
			return response;
		}

		public String toString() {
			return "statusCode=" + statusCode + ", " + response;
		}
	};
	
}
