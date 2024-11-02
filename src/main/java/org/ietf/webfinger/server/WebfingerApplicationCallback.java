package org.ietf.webfinger.server;

import java.net.URI;

public interface WebfingerApplicationCallback {
	
	boolean isHostSupported(URI resource, String host);
	
	URI getActorUri(URI resource, String host, String username);
	
	URI newResource(URI resource, String host, String username);
	
}
