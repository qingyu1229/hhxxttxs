package com.httpclient;

import java.io.IOException;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;


public class HttpRequestProxy {
	private CrawlConfig config;
	protected DefaultHttpClient httpClient;
	protected ThreadSafeClientConnManager connectionManager;
	
	public void fentch(){
		
		//HttpParams 接口代表了定义组件运行时行为的一个不变的值的集合
		HttpParams params = new BasicHttpParams();
		
		HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
		//设置HTTP版本
		paramsBean.setVersion(HttpVersion.HTTP_1_1);
		//设置内容编码
		paramsBean.setContentCharset("UTF-8");
		//遇到异常是否继续
		paramsBean.setUseExpectContinue(false);
		//User_agent
		params.setParameter(CoreProtocolPNames.USER_AGENT, config.getUserAgentString());
		//Socket连接超时
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, config.getSocketTimeout());
		//连接超时
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, config.getConnectionTimeout());
		//是否自动处理重定向
		params.setBooleanParameter("http.protocol.handle-redirects", false);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

		if (config.isIncludeHttpsPages()) {
			schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		}
		
		connectionManager = new ThreadSafeClientConnManager(schemeRegistry);
		connectionManager.setMaxTotal(config.getMaxTotalConnections());
		connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsPerHost());
		httpClient = new DefaultHttpClient(connectionManager, params);
		
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
				@Override
			   public boolean retryRequest(IOException exception,
			   int executionCount,HttpContext context) {
			        if (executionCount >= 5) {
			             // 如果超过最大重试次数，那么就不要继续了
			             return false;
			        }
			        if (exception instanceof NoHttpResponseException) {
			             // 如果服务器丢掉了连接，那么就重试
			             return true;
			        }
			        if (exception instanceof SSLHandshakeException) {
			            // 不要重试SSL握手异常
			            return false;
			        }
			        HttpRequest request = (HttpRequest) context.getAttribute(
			        ExecutionContext.HTTP_REQUEST);
			        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
			        if (idempotent) {
			             // 如果请求被认为是幂等的，那么就重试
			             return true;
			        }
			        return false;
			   }
			};
			httpClient.setHttpRequestRetryHandler(myRetryHandler);
			
			//设置代理
			if (config.getProxyHost() != null) {

				if (config.getProxyUsername() != null) {
					httpClient.getCredentialsProvider().setCredentials(
							new AuthScope(config.getProxyHost(), config.getProxyPort()),
							new UsernamePasswordCredentials(config.getProxyUsername(), config.getProxyPassword()));
				}

				HttpHost proxy = new HttpHost(config.getProxyHost(), config.getProxyPort());
				httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	        }
			
			  httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
		            @Override
		            public void process(final HttpResponse response, final HttpContext context) throws HttpException,
		                    IOException {
		                HttpEntity entity = response.getEntity();
		                Header contentEncoding = entity.getContentEncoding();
		                if (contentEncoding != null) {
		                    HeaderElement[] codecs = contentEncoding.getElements();
		                    for (HeaderElement codec : codecs) {
		                        if (codec.getName().equalsIgnoreCase("gzip")) {
		                            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
		                            return;
		                        }
		                    }
		                }
		            }

		        });
			  
			
	}
}
