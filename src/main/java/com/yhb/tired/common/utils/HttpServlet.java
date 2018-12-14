package com.yhb.tired.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/7/18 16:33
 * @Description:
 */
public class HttpServlet {

    public static void main(String[] args) {
        HttpServlet.doGet();
        //HttpServlet.doPost();
    }

    private static Log logger = LogFactory.getLog(HttpServlet.class);
    public static void doGet()  {
        logger.info("开始执行HttpGet");
        String url = "http://192.168.2.54:8090/user/getAllUsers.do";
        HttpGet httpRequest = new HttpGet(url);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        judge(response);
        try {
            response.close();//关闭
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("异常");
        }
        logger.info("执行完成");
    }

    public static void doPost() {
        logger.info("开始执行HttpPost");
        String url = "http://192.168.2.54:8090/doLogin.do";
        logger.info("开始执行封装参数");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "yhb"));
        params.add(new BasicNameValuePair("password", "123456"));
        HttpPost httpRequest = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            CloseableHttpClient client = HttpClients.createDefault();
            response = client.execute(httpRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        judge(response);
        try {
            response.close();//关闭
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("异常");
        }
        logger.info("执行完成");
    }

    public static void judge(CloseableHttpResponse response){
        if (response.getStatusLine().getStatusCode()==200){//请求成功
            logger.info("请求成功");
            HttpEntity httpEntity =response.getEntity();
            try {
                //解析JSONJSONArray
                String retSrc = EntityUtils.toString(httpEntity);
                JSONArray jsonArray = JSONArray.fromObject(retSrc);
                List<Map<String,Object>> list = (List<Map<String,Object>>)jsonArray;
                for (int i = 0; i < list.size(); i++) {
                    logger.info("开始打印第"+i+"个对象");
                    System.out.println("id:"+list.get(i).get("id"));
                    System.out.println("age:"+list.get(i).get("age"));
                    System.out.println("username:"+list.get(i).get("username"));
                    System.out.println("password:"+list.get(i).get("password"));
                    System.out.println("email:"+list.get(i).get("email"));
                    System.out.println("gender:"+list.get(i).get("gender"));
                    System.out.println("nickname:"+list.get(i).get("nickname"));
                    System.out.println("mobile:"+list.get(i).get("mobile"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            logger.warn("请求失败");
        }
    }

    public static JSONObject doPoststr(String path,String param) {
        logger.info("开始执行HttpPost");
        logger.info("开始执行封装参数");
        JSONObject jsonMsg = null;
        try {
            URL url=new URL(path);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(param.getBytes("UTF-8"));
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] bt = new byte[size];
            is.read(bt);
            String message=new String(bt,"UTF-8");
            jsonMsg = JSONObject.fromObject(message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("执行完成");
        return jsonMsg;
    }
}
