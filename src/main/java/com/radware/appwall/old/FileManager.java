package com.radware.appwall.old;


import com.radware.appwall.xml.XmlUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;

public abstract class FileManager<E> {
	
	protected AppWall appwall;
	protected boolean refreshed= false;
	protected E element;
	protected ServerResource serverResource;
	public String timeStamp="";
	private String text;
	private ClientContent clientContent;
	


	public ClientContent getClientContent() {
		return clientContent;
	}

	public void setClientContent(ClientContent clientContent) {
		this.clientContent = clientContent;
	}

	public FileManager(){		
		this.appwall = DefaultAppWall.getInstance();
		serverResource = getServerResource();
	}	
	
	public void setAppWall(AppWall appwall){
		this.appwall = appwall;
	}
	
	public E getCache(){
		return element;
	}
	
	
	protected AppwallResponse getReply(UserPass userPass, int timeout) throws Exception {
		serverResource.setClientContent(getClientContent());
		return AppWallUtil.getReply(appwall, serverResource, new byte[0], null, userPass, true,timeout);	
	}
	
	public void refresh(UserPass userPass,ClientContent clientContent) throws Exception {
		this.refresh(userPass, clientContent, 0);
	}
	
	public void refresh(UserPass userPass,ClientContent clientContent, int timeout) throws Exception {
        try {
        	text = null;
			if(DebugDD.isOn() && getFilePath()!=null){
				File file = new File(getFilePath());
				timeStamp = ""+file.lastModified();
				text = FileUtils.readFileToString(file);
			}
			else{
				setClientContent(clientContent);
                AppwallResponse reply = getReply(userPass,timeout);
				timeStamp = reply.timeStamp;
				refreshed = true;
				text= reply.body;
			}	
			
			if(text==null || text.isEmpty()){
				//AccessLog.log("doRequest Alteon \n" + text + "\n\n");
				element= createNewElement();
	        }
	        else{
	        	element = XmlUtil.fromXml(getRootXmlClass(), text);
	        }
			//AccessLog.log("text \n" + text + "\n\n");
		}
		catch(Exception e){
            element = createNewElement();
            refreshed = false;
            throw e;
		}
	}
	
	public String getText(){
		return text;
	}
	
	public void save(UserPass userPass,ClientContent clientContent) throws Exception {
		try{
			String text = XmlUtil.toXml(element);
			serverResource.setClientContent(clientContent);
			AppWallUtil.getReply(appwall, serverResource, text.getBytes(), timeStamp, userPass, false);
		}
		catch(Exception e){
			refreshed = false;
			throw e;
		}
	}
	
	protected abstract E createNewElement();
	public abstract ServerResource getServerResource();
	public abstract Class<E> getRootXmlClass();
	
	protected String getFilePath(){
		return null;
	}
	
}
