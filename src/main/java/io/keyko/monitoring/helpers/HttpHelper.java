package io.keyko.monitoring.helpers;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public abstract class HttpHelper {

  public static final String UTF8= "UTF-8";

  private HttpHelper() {
  }

  /**
   * Send a HTTP POST request and return the body
   *
   * @param url url to call
   * @return returned http body
   * @throws HttpException                Http error
   * @throws UnsupportedEncodingException Encoding error
   */
  public static final String httpClientPostBody(String url) throws HttpException, UnsupportedEncodingException {
    return httpClientGenericMethod(new PostMethod(url), new ArrayList<>(), null).getBody();
  }

  /**
   * Send a HTTP POST request with parameters and return the body
   *
   * @param url  url to call
   * @param list parameters
   * @return returned http body
   * @throws HttpException                Http error
   * @throws UnsupportedEncodingException Encoding error
   */
  public static final String httpClientPostBody(String url, ArrayList<NameValuePair> list) throws HttpException, UnsupportedEncodingException {
    return httpClientGenericMethod(new PostMethod(url), list, null).getBody();
  }

  /**
   * Send a HTTP POST request and return the HttpResponse object
   *
   * @param url url to call
   * @return HttpResponse returned
   * @throws HttpException                Http error
   * @throws UnsupportedEncodingException Encoding error
   */
  public static final HttpResponse httpClientPost(String url) throws HttpException, UnsupportedEncodingException {
    return httpClientGenericMethod(new PostMethod(url), new ArrayList<>(), null);
  }

  /**
   * Send a HTTP POST request and return the HttpResponse object
   *
   * @param url     url to call
   * @param list    parameters
   * @param payload payload to add to the request
   * @return HttpResponse returned
   * @throws HttpException                Http error
   * @throws UnsupportedEncodingException Encoding error
   */
  public static final HttpResponse httpClientPost(String url, ArrayList<NameValuePair> list, String payload) throws HttpException, UnsupportedEncodingException {
    return httpClientGenericMethod(new PostMethod(url), list, payload);
  }

  /**
   * Send a HTTP PUT request and return the HttpResponse object
   *
   * @param url     url to call
   * @param list    parameters
   * @param payload payload to add to the request
   * @return HttpResponse returned
   * @throws HttpException                Http error
   * @throws UnsupportedEncodingException Encoding error
   */
  public static final HttpResponse httpClientPut(String url, ArrayList<NameValuePair> list, String payload) throws HttpException, UnsupportedEncodingException {
    return httpClientGenericMethod(new PutMethod(url), list, payload);
  }

  /**
   * Send a HTTP request with parameters and return the HttpResponse object
   *
   * @param method  EntityEnclosingMethod
   * @param list    list of params
   * @param payload payload to add to the request
   * @return HttpResponse
   * @throws HttpException                HttpException
   * @throws UnsupportedEncodingException UnsupportedEncodingException
   */

  public static final HttpResponse httpClientGenericMethod(EntityEnclosingMethod method, ArrayList<NameValuePair> list, String payload) throws HttpException, UnsupportedEncodingException {
    return httpClientGenericMethod(new HttpClient(), method, list, payload);
  }

  /**
   * Send a HTTP request with parameters and return the HttpResponse object
   *
   * @param client  HttpClient
   * @param method  EntityEnclosingMethod
   * @param list    list of params
   * @param payload payload to add to the request
   * @return HttpResponse
   * @throws HttpException                HttpException
   * @throws UnsupportedEncodingException UnsupportedEncodingException
   */
  public static final HttpResponse httpClientGenericMethod(HttpClient client, EntityEnclosingMethod method, ArrayList<NameValuePair> list, String payload) throws HttpException, UnsupportedEncodingException {

    HttpResponse response;
    StringRequestEntity requestEntity = null;

    if (null != payload && payload.length() > 0) {
      requestEntity = new StringRequestEntity(
        payload,
        ContentType.APPLICATION_JSON.toString(),
        UTF8);

      method.setRequestEntity(requestEntity);
    }

    try {
      if (list.size() > 0) {
        NameValuePair[] params = new NameValuePair[list.size()];
        for (int i = 0; i < list.size(); i++) {
          params[i] = list.get(i);
        }

        if (method instanceof PostMethod)
          ((PostMethod) method).addParameters(params);
      }

      client.executeMethod(method);
      response = new HttpResponse(
        method.getStatusCode(),
        IOUtils.toString(method.getResponseBodyAsStream(), UTF8),
        method.getResponseCharSet(),
        method.getResponseContentLength()
      );
    } catch (Exception e) {
      throw new HttpException("Error in HTTP Method request: " + e.getMessage());
    } finally {
      method.releaseConnection();
    }
    return response;
  }

  /**
   * Send a HTTP GET request and return the HttpResponse object
   *
   * @param url the url
   * @return HttpResponse
   * @throws HttpException HttpException
   */
  public static final HttpResponse httpClientGet(String url) throws HttpException {
    return httpClientGet(new HttpClient(), new GetMethod(url));
  }


  /**
   * Send a HTTP GET request and return the HttpResponse object
   *
   * @param client    HttpClient
   * @param getMethod GetMethod
   * @return HttpResponse
   * @throws HttpException HttpException
   */
  public static final HttpResponse httpClientGet(HttpClient client, GetMethod getMethod) throws HttpException {
    return httpClientRead(client, getMethod);
  }

  /**
   * Send a HTTP HEAD request and return the HttpResponse object
   *
   * @param client     HttpClient
   * @param headMethod HeadMethod
   * @return HttpResponse
   * @throws HttpException HttpException
   */
  public static final HttpResponse httpClientHead(HttpClient client, HeadMethod headMethod) throws HttpException {
    return httpClientRead(client, headMethod);
  }

  /**
   * Send a HTTP GET or HEAD request and return the HttpResponse object
   *
   * @param client HttpClient
   * @param method HttpMethodBase
   * @return HttpResponse
   * @throws HttpException HttpException
   */
  public static final HttpResponse httpClientRead(HttpClient client, HttpMethodBase method) throws HttpException {

    HttpResponse response;
    try {

      client.executeMethod(method);
      response = new HttpResponse(
        method.getStatusCode(),
        IOUtils.toString(method.getResponseBodyAsStream(), UTF8),
        method.getResponseCharSet(),
        method.getResponseContentLength()
      );
      method.getResponseHeaders();
    } catch (Exception e) {
      throw new HttpException("Error in HTTP request: " + e.getMessage());
    } finally {
      method.releaseConnection();
    }
    return response;

  }


  /**
   * Download the content of a resource
   *
   * @param url             the url of the resource
   * @param destinationPath the path where the resource will be downloaded
   * @throws IOException Exception during the download
   */
  public static void download(final String url, final String destinationPath) throws IOException {

    try {
      URL contentUrl = new URL(url);
      ReadableByteChannel readableByteChannel = Channels.newChannel(contentUrl.openStream());
      FileOutputStream fileOutputStream = FileUtils.openOutputStream(new File(destinationPath));
      fileOutputStream.getChannel()
        .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

    } catch (IOException e) {
      throw e;
    }

  }

  /**
   * Download the content of a resource
   * @param url the url
   * @param isRangeRequest indicates if is a range request
   * @param startRange  the start of the bytes range
   * @param endRange  the end of the bytes range
   * @return an InputStream that represents the binary content
   * @throws IOException Exception during the download
   */
  public static InputStream download(final String url, Boolean isRangeRequest, Integer startRange, Integer endRange) throws IOException {

    try {

      URL contentUrl= new URL(url);

      if (isRangeRequest){

        HttpURLConnection con = (HttpURLConnection)contentUrl.openConnection();
        con.setRequestMethod("GET");
        con.addRequestProperty("Range", "bytes="+startRange+"-"+endRange);
        return con.getInputStream();
      }

      return contentUrl.openStream();

    } catch (IOException e) {
      throw e;
    }

  }

  /**
   * Send a HTTP DELETE request and return the HttpResponse object
   *
   * @param url url to call
   * @return HttpResponse returned
   * @throws HttpException Http error
   */
  public static final HttpResponse httpClientDelete(String url) throws HttpException {
    return httpClientRead(new HttpClient(), new DeleteMethod(url));
  }



}
