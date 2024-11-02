package software.diaspora.nodeinfo.server;

public interface NodeinfoApplicationCallback {
	
	String getHostName();
	
	NodeinfoSchema getApplicationInformation();
	
}
