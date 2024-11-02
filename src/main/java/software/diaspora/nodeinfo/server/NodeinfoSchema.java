package software.diaspora.nodeinfo.server;

import java.util.List;
import java.util.Map;

public interface NodeinfoSchema {

	String getVersion();
	
	Map<String, String> getSoftware();
	
	List<String> getProtocols();
	
	boolean isOpenRegistrations();
	
	Map<String, List<String>> getServices();
	
	public Map<String, Object> getUsage();
	
	public Map<String, Object> getMetadata();
		
}
