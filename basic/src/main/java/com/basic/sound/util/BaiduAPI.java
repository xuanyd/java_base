package com.basic.sound.util;

import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;

public class BaiduAPI {
	
	private static String appId = "11508869";
	private static String apiKey = "REBYrLBsBt00tU7lkGGN6QDA";
	private static String secretKey = "TMHa8gYpw4ptkyDeIdu9IvYE3On7Sy4d";
	
	private static AipSpeech client = null;
	
	static {
		client = new AipSpeech(appId, apiKey, secretKey);
		client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
	}
	
	public static String request(String filePath) {
		JSONObject res = client.asr(filePath, "wav", 16000, null);
        System.out.println(res.toString(2));
        return res.toString(2);
	}
	
	public static void main(String[] args) {
		BaiduAPI.request("D:\\AudioFile\\zztq.wav");
	}
	
}
