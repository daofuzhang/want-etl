/**
 * -------------------------------------------------------
 * @FileName锛欸raylogUtil.java
 * @Description锛氱畝瑕佹弿杩版湰鏂囦欢鐨勫唴瀹�
 * @Author锛歀uke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 娉ㄦ剰锛氭湰鍐呭浠呴檺浜庢椇鏃洪泦鍥㈠唴閮ㄤ紶闃咃紝绂佹澶栨硠浠ュ強鐢ㄤ簬鍏朵粬鍟嗕笟鐩殑
 * -------------------------------------------------------
 */
package com.want.graylog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.want.graylog.to.Log;
import com.want.graylog.to.Message;

public class GraylogServce {

	static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static List<Message> getMessageList(String url, String username, String password) {
		List<Message> list = new ArrayList<Message>();
		try {
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
			provider.setCredentials(AuthScope.ANY, credentials);
			HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

			URIBuilder builder = new URIBuilder(url + "/api/search/universal/relative");
			builder.setParameter("query", "facility:job-worker AND logger:" + getRuuner().replace("/", "\\/"));
//			builder.setParameter("query", "facility:job-worker");
			builder.setParameter("range", "48000");
			builder.setParameter("sort", "timestamp:desc");
			builder.setParameter("decorate", "true");

			HttpGet httpGet = new HttpGet(builder.build().toURL().toString());
			httpGet.setHeader("Accept", "application/json");
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity, "UTF-8");
//			System.out.println(gson.toJson(JsonParser.parseString(json)));
			List<Log> logs = gson.fromJson(gson.toJson(JsonParser.parseString(json).getAsJsonObject().get("messages")),
					new TypeToken<List<Log>>() {
					}.getType());

			for (Log log : logs) {
				list.add(log.getMessage());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static String getRuuner() {
		String runner;
		try {
			runner = java.net.InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			runner = System.getProperty("user.name");
		}
		return runner;
	}

}
