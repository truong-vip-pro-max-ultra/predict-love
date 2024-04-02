package com.truongjae.predictlove.utils;

import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ResponseUtils {
    public static String respGetRequest(String link,String cookies,boolean userAgent) {
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn = (HttpURLConnection) url.openConnection();
            if(userAgent)
                conn.setRequestProperty("user-agent","Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.7 (KHTML, like Gecko) Chrome/7.0.517.41 Safari/534.7");
            if(cookies!=null)
                conn.setRequestProperty("Cookie", cookies);
            conn.setRequestMethod("GET");
            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            conn.disconnect();
            return StringEscapeUtils.unescapeJava(result.toString());
        }
        catch (Exception e){
            return "";
        }

    }
    public static String respPostRequest(String link,String cookies,String data,boolean userAgent) {

        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn = (HttpURLConnection) url.openConnection();
            if(cookies!=null)
                conn.setRequestProperty("Cookie", cookies);
            if(userAgent)
                conn.setRequestProperty("user-agent","Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.7 (KHTML, like Gecko) Chrome/7.0.517.41 Safari/534.7");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte[] out = data.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = conn.getOutputStream();
            stream.write(out);
            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            conn.disconnect();
//            System.out.println(result.toString());
            return StringEscapeUtils.unescapeJava(result.toString());
        }
        catch (Exception e){

            return "";
        }

    }

    public static String respRedirect(String url,String cookies){
        try {
            URLConnection con = new URL(url).openConnection();
            con.setRequestProperty("Cookie", cookies);
            con.connect();
            InputStream is = con.getInputStream();
            String urlRedirect = con.getURL().toString();
            is.close();
            return urlRedirect;
        }
        catch (Exception e){
            return null;
        }
    }
}
