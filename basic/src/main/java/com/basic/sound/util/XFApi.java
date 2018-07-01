package com.basic.sound.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author xuanyandong
 *
 */
public class XFApi {
	
	private static final String URL = "http://openapi.xfyun.cn/v2/aiui";
	private static final String APPID = "5b29c990";

	//private static final String API_KEY = "8d87767697d94e459e210d49ddee7498";
	private static final String API_KEY = "d6328e004592b36ddf310c70ed0bd752";
	private static final String DATA_TYPE = "audio";
	private static final String SCENE = "main";
	private static final String SAMPLE_RATE = "16000";
	private static final String AUTH_ID = "31ef41e0959f9526e7313e89887515d3";
	private static final String AUE = "raw";
	// 个性化参数，注意需进行两层转义
	private static final String PERS_PARAM = "{\"auth_id\":\"31ef41e0959f9526e7313e89887515d3\"}";
	
	
	public static String audioCall(String audioPath) throws Throwable {
		Map<String, String> header = buildHeader();
		byte[] dataByteArray = readFile(audioPath);
		dataByteArray = Base64.encodeBase64(dataByteArray);
		//byte[] textArray = Base64.encodeBase6(new String("郑州天气").getBytes());
		String result = httpPost(URL, header, dataByteArray);
		System.out.println(result);		
		return "";
	}
	
	private static Map<String, String> buildHeader() throws UnsupportedEncodingException, ParseException {
		String curTime = System.currentTimeMillis() / 1000L + "";
		String param = "{\"aue\":\""+AUE+"\",\"sample_rate\":\""+SAMPLE_RATE+"\",\"auth_id\":\""+AUTH_ID+"\",\"data_type\":\""+DATA_TYPE+"\",\"scene\":\""+SCENE+"\"}";		
		//使用个性化参数时参数格式如下：
		//String param = "{\"aue\":\""+AUE+"\",\"sample_rate\":\""+SAMPLE_RATE+"\",\"auth_id\":\""+AUTH_ID+"\",\"data_type\":\""+DATA_TYPE+"\",\"scene\":\""+SCENE+"\",\"pers_param\":\""+PERS_PARAM+"\"}";
		String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
		String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);

		Map<String, String> header = new HashMap<String, String>();
		header.put("X-Param", paramBase64);
		header.put("X-CurTime", curTime);
		header.put("X-CheckSum", checkSum);
		header.put("X-Appid", APPID);
		System.out.println(header);
		return header;
	}
	
	private static byte[] readFile(String filePath) throws IOException {
		InputStream in = new FileInputStream(filePath);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		byte[] data = out.toByteArray();
		in.close();
		return data;
	}
	
	private static String httpPost(String url, Map<String, String> header, byte[] body) {
		String result = "";
		BufferedReader in = null;
		OutputStream out = null;
		try {
			URL realUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
			for (String key : header.keySet()) {
				connection.setRequestProperty(key, header.get(key));
			}
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			//connection.setConnectTimeout(20000);
			//connection.setReadTimeout(20000);
			try {
				out = connection.getOutputStream();
				out.write(body);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) throws UnknownHostException {
		String address = InetAddress.getLocalHost().getHostAddress().toString();
		try {
			new XFApi().audioCall("D:\\AudioFile\\1530263606074.wav");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
