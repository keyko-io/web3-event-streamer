package io.keyko.monitoring.helpers;

import org.apache.commons.httpclient.Header;

public class HttpResponse {

  private int statusCode;
  private String body;
  private String charset;
  private long contentLength;
  private Header[] headers;

  /**
   * Constructor
   *
   * @param statusCode    status code
   * @param body          body string
   * @param charset       charset
   * @param contentLength content length
   */
  public HttpResponse(int statusCode, String body, String charset, long contentLength) {
    this.statusCode = statusCode;
    this.body = body;
    this.charset = charset;
    this.contentLength = contentLength;
  }

  /**
   * Ge the http status code
   *
   * @return status code
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * Set the http status code
   *
   * @param statusCode status code
   */
  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  /**
   * Get the body
   *
   * @return string body
   */
  public String getBody() {
    return body;
  }

  /**
   * Set the body
   *
   * @param body string
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Get the charset
   *
   * @return charset
   */
  public String getCharset() {
    return charset;
  }

  /**
   * Set the charset
   *
   * @param charset charset
   */
  public void setCharset(String charset) {
    this.charset = charset;
  }

  /**
   * Get content length
   *
   * @return content length
   */
  public long getContentLength() {
    return contentLength;
  }

  /**
   * Set content length
   *
   * @param contentLength content length
   */
  public void setContentLength(long contentLength) {
    this.contentLength = contentLength;
  }

  /**
   * Get HTTP response headers
   *
   * @return headers
   */
  public Header[] getHeaders() {
    return headers;
  }

  /**
   * Get HTTP response header giving a header name
   * @param name name of the header
   * @return header
   */
  public Header getHeader(String name) {
    for (Header header : headers) {
      if (header.getName().equalsIgnoreCase(name))
        return header;
    }
    return null;
  }


  /**
   * Set HTTP response headers
   *
   * @param headers headers
   */
  public void setHeaders(Header[] headers) {
    this.headers = headers;
  }

  /**
   * Get the string representation of the object
   *
   * @return string
   */
  @Override
  public String toString() {
    return "HttpResponse{" +
      "statusCode=" + statusCode +
      ", body='" + body + '\'' +
      ", charset='" + charset + '\'' +
      ", contentLength=" + contentLength +
      '}';
  }
}
