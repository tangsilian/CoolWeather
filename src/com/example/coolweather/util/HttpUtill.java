package com.example.coolweather.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtill {

	public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
		// 新启一个线程来获取服务器的数据
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(connection.getInputStream()));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						response.append(line);

					}
					if (listener != null) {
						listener.onFinish(response.toString());

					}
				} catch (Exception e) {
					// TODO: handle exception
					if (listener != null) {
						listener.onEerror(e);
						;

					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();

	}
}
