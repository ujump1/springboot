package com.yj.springboot.service.utils;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求
 * @author yujiang
 * @create 2019/12/4
 */
public class HTTPClientUtil {

    public static String HttpGet(String URL){
        String resultData ="";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        RequestConfig config=RequestConfig.custom()
                .setConnectTimeout(600000)
                .setConnectionRequestTimeout(600000)
                .setSocketTimeout(600000)
                .build();
        HttpGet httpGet=new HttpGet(URL);
        httpGet.setConfig(config);
        CloseableHttpResponse response=null;
        try{
            //执行httpGet请求
            response=httpClient.execute(httpGet);
            //判断是否执行成功
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                //返回数据
            resultData= EntityUtils.toString(response.getEntity(),"UTF-8");
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultData;
    }


    public static String HttpGetWithParams(String URL,Map<String, String> paramsMap){
        String resultData="";
        CloseableHttpClient httpClient=HttpClientBuilder.create().build();
        RequestConfig config=RequestConfig.custom()
                .setConnectTimeout(600000)
                .setConnectionRequestTimeout(600000)
                .setSocketTimeout(600000)
                .build();
        //拼接参数
        StringBuilder url=new StringBuilder(URL);
        url.append("?");
        for(Map.Entry<String,String>  entry: paramsMap.entrySet())
        {
            url.append(entry.getKey());
            url.append("&");
            url.append(entry.getValue());
        }
        URL=url.toString();
        HttpGet httpGet=new HttpGet(URL);
        httpGet.setConfig(config);
        CloseableHttpResponse response=null;
        try{
            //执行httpGet请求
            response=httpClient.execute(httpGet);
            //判断是否执行成功
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                //返回数据
                resultData= EntityUtils.toString(response.getEntity(),"UTF-8");
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultData;
    }

    public static String HttpPostWithParams(String URL,Map<String, String> paramsMap){
        String resultData="";
        CloseableHttpClient httpClient=HttpClientBuilder.create().build();
        RequestConfig config=RequestConfig.custom()
                .setConnectTimeout(600000)
                .setConnectionRequestTimeout(600000)
                .setSocketTimeout(600000)
                .build();
        HttpPost httpPost=new HttpPost(URL);
        List<NameValuePair> list=new ArrayList<>();
        for(Map.Entry<String,String> entry:paramsMap.entrySet()){
            list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        try {
            UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(list, "utf-8");  // form 用这个  如果时body 的话 用stringEntity
            httpPost.setEntity(uefe);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setConfig(config);
        CloseableHttpResponse response=null;
        try{
            //执行httpGet请求
            response=httpClient.execute(httpPost);
            //判断是否执行成功
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                //返回数据
                resultData= EntityUtils.toString(response.getEntity(),"UTF-8");
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultData;
    }
}
