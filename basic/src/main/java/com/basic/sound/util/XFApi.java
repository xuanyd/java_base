package com.basic.sound.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author xuanyandong
 *
 */
public class XFApi {
	
	public String audioCall(String audioPath) throws Throwable {
		
		File file = new File(audioPath);
		String fileData = null;
		try {
			byte[] bytes = FileUtil.readFileBytes(file.getAbsolutePath());
			fileData = Base64.encode(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fileData = "data=" + fileData;
		
		String xParam = "{\"auf\":\"16k\",\"aue\":\"raw\",\"scene\":\"main\",\"userid\":\"user_0001\"}";
		
		String xParamBase64 = Base64.encode(xParam.getBytes("UTF-8"));
		String curTime = String.valueOf(System.currentTimeMillis());
		String apiKey = PropertyUtils.get("apiKey");
		String token = apiKey + curTime + xParamBase64 + fileData;
		String xCheckSum = StringUtils.md5Encode(token);
		
		String resBody = "";
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL url = new URL(PropertyUtils.get("aUrl"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(2000);
			conn.setConnectTimeout(1000);
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("X-Appid", PropertyUtils.get("xAppid"));
			conn.setRequestProperty("X-CurTime", curTime);
			conn.setRequestProperty("X-Param", xParamBase64);
			conn.setRequestProperty("X-CheckSum", xCheckSum);
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
			out = new PrintWriter(conn.getOutputStream());
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(fileData);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			// 将返回的输入流转换成字符串
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			in = new BufferedReader(inputStreamReader);
			String line;
			while ((line = in.readLine()) != null) {
				resBody += line;
			}
			System.out.println("result body :" + resBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		try {
			new XFApi().audioCall("D:\\AudioFile\\1529630254415.wav");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
