package Com.jio.util;

import Com.jio.base.BaseScript;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * @author Fazil created on 06-Dec-2022
 */
@Log4j
public class HttpClientCaller extends BaseScript {


    public static HttpResponse micrositeAPIGetCall(String fqdn, String endpoint, String cookie) {
        HttpResponse responseBody = null;
        try  {
            log.info("Request Url is::: "+fqdn + endpoint);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(fqdn + endpoint);
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            if (null != cookie && !cookie.equals(""))
                httpGet.setHeader("Cookie", cookie);
            responseBody = httpclient.execute(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseBody;
    }

    public static HttpResponse micrositeAPIPostCall(String fqdn, String endpoint, String requestBody, String cookie) {
        HttpResponse responseBody = null;
        try  {
            log.info("Request Url is::: "+fqdn + endpoint);
            System.out.println(endpoint);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(fqdn + endpoint);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            if (null != cookie && !cookie.equals(""))
                httpPost.setHeader("api-key", cookie);
            StringEntity stringEntity = new StringEntity(requestBody);
            httpPost.setEntity(stringEntity);
            responseBody = httpclient.execute(httpPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseBody;
    }

    public static HttpResponse micrositeAPIPutCall(String fqdn, String endpoint, String requestBody, String cookie) {
        HttpResponse responseBody = null;
        try  {
            log.info("Request Url is::: "+fqdn + endpoint);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(fqdn + endpoint);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            if (null != cookie && !cookie.equals(""))
                httpPut.setHeader("Cookie", cookie);
            StringEntity stringEntity = new StringEntity(requestBody);
            httpPut.setEntity(stringEntity);
            responseBody = httpclient.execute(httpPut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseBody;
    }


    public static HttpResponse micrositeAPIDeleteCall(String fqdn, String endpoint, String cookie) {
        HttpResponse responseBody = null;
        try  {
            log.info("Request Url is::: "+fqdn + endpoint);
            System.out.println("endpoint iss:::"+endpoint);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpDelete httpDelete = new HttpDelete(fqdn + endpoint);
            if (null != cookie && !cookie.equals(""))
                httpDelete.setHeader("Cookie", cookie);
            responseBody = httpclient.execute(httpDelete);
        } catch (Exception e) {
            System.out.println("exception is:::::" +e);
            throw new RuntimeException(e);
        }
        return responseBody;
    }
}


