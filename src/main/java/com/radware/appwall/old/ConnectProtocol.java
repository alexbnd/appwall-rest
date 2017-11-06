package com.radware.appwall.old;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class ConnectProtocol {

	public static final String UTF_8 = "UTF-8";
	public static final String AW_REQ = "/aw/?req=";
	public static final String HTTP = "http://";
	public static final String SLASH = "/";
	public static final String EMPTY_STRING = " ";
	public static final String TAB_SIGN = "\t";
	public static final String LINE_SEPARATOR = "\r\n";

	public static AppwallResponse analyzeReply(ConnectArgs args) throws ResponseException {
		OutputStream writer = null;
		BufferedReader reader = null;
		URLConnection conn = null;
		try{
			String subSystem = URLEncoder.encode(args.subSystem, UTF_8);
			String method = URLEncoder.encode(args.method, UTF_8);
			String fileString = URLEncoder.encode(args.fileString, UTF_8);
			String urlInfo =subSystem+ SLASH +method+ SLASH +fileString;
			String urlString = HTTP +args.host+":"+args.port+ AW_REQ;
			urlString = urlString +urlInfo;
			URL url = new URL(urlString);
			conn = url.openConnection();
			conn.setDoOutput(true);
			String timestap ="";
			String credentials ="";
			ClientContent clientContent= args.getClientContent();
			if (clientContent != null) {
				HashMap<String,String> outHeaders= clientContent.getOutHeaders();
				for (String iter : outHeaders.keySet()) {
					String value = outHeaders.get(iter);
					conn.addRequestProperty(iter,value);
				}
			}
			if(args.timeStamp == null){
				timestap = EMPTY_STRING;
			}else{
				timestap = args.timeStamp;
			}
			if(args.credentials == null){
				credentials = EMPTY_STRING;
			}else{
				credentials = args.credentials;
			}

			conn.setConnectTimeout(1000 * args.timeout);
			writer = conn.getOutputStream();
			String full = args.method + TAB_SIGN + args.subSystem + TAB_SIGN + args.version + TAB_SIGN
					+ credentials + TAB_SIGN + args.body.length + TAB_SIGN + timestap + TAB_SIGN + args.fileString
					+ LINE_SEPARATOR;
			writer.write(full.getBytes());
			writer.write(args.body);
			writer.write(LINE_SEPARATOR.getBytes());
			writer.flush();
			if (clientContent != null && clientContent.getRequestId()!= null && clientContent.getRequestId() > 0) {
				//AppWallLogger.trace(ConnectProtocol.class, "INTERNAL_REQUESTx1", full);
			}
			String line;
			InputStream inputStream = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder builder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				builder.append(line+"\n");
			}
			String responseText = builder.toString();
			/*if (LogApiConnection.shouldLog) {
				Print.out("\n\n");
				Print.out("================================================================================================================");
				Print.out(full.trim()+ LINE_SEPARATOR +new String(args.body)+ LINE_SEPARATOR);
				Print.out(responseText);
			}
			if (clientContent != null && clientContent.getRequestId()!= null && clientContent.getRequestId() > 0) {
				AppWallLogger.trace(ConnectProtocol.class, "INTERNAL_RESPONSEx2", clientContent.getRequestId(),
						responseText);
			}*/
			AppwallResponse reply = new AppwallResponse(responseText);
			return reply;
		}
		catch(Exception e){
			//AppWallLogger.error(ConnectProtocol.class, e,  "GENERAL_LOGGINGx1");
			throw new ResponseException(AppwallResponse.NO_REPLY);
		}
		finally{
			try{
				if(writer!=null){
					writer.close();
				}
				HttpURLConnection connection = (HttpURLConnection) conn;
				connection.disconnect();
			}
			catch(Exception e){
				//AppWallLogger.error(ConnectProtocol.class, e,  "APPWALL_UTIL_CLOSING_ERROR_LOG2x1");
			}
		}
	}

}