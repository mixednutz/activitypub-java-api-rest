package software.diaspora.nodeinfo.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class NodeinfoServer {
		
	public static final String SCHEMA_URI = "/nodeinfo/2.1";
	
	private static final Logger LOG = LoggerFactory.getLogger(NodeinfoServer.class);
		
	@Autowired
	NodeinfoApplicationCallback callback;
			
	public NodeinfoResponse handleNodeinfoRequest() {
		LOG.info("Nodeinfo lookup");
		
		NodeinfoResponse response = new NodeinfoResponse(List.of(new NodeinfoResponse.Link(
				"http://nodeinfo.diaspora.software/ns/schema/2.1", 
				"http://"+callback.getHostName()+SCHEMA_URI)));
		
		return response;
	}
	
	@Cacheable("nodeinfo-schema")
	public NodeinfoSchema handleNodeinfoSchemaRequest() {
		LOG.info("Nodeinfo Schema lookup");
		return callback.getApplicationInformation();
	}

}
