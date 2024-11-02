package org.ietf.webfinger.server;

import java.net.URI;
import java.util.List;

import org.ietf.webfinger.WebfingerResponse;
import org.ietf.webfinger.WebfingerResponse.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebfingerServer {
		
	private static final Logger LOG = LoggerFactory.getLogger(WebfingerServer.class);
	
	@Autowired
	private WebfingerApplicationCallback userLookup;
	
	public WebfingerResponse handleWebfingerRequest(URI resource) throws BadRequestException {
		LOG.info("Webfinger lookup for {}",resource);
		String scheme = resource.getScheme();
		URI actorUri = null;
		if ("acct".equals(scheme)) {
			String acc = resource.getSchemeSpecificPart();
			String[] part = acc.split("@");
			if (part.length!=2) {
				throw new BadRequestException("Resource expected to be acct:username@host: "+resource.toString());
			}
			final String username = part[0];
			final String host = part[1];
			LOG.info("Webfinger lookup for username: {} host: {}",username, host);
						
			if (!userLookup.isHostSupported(resource, host)) {
				throw new BadRequestException("Resource host not found on this server: "+host);
			}
			
			actorUri = userLookup.getActorUri(resource, host, username);
			resource = userLookup.newResource(resource, host, username);			
					
		} else {
			throw new BadRequestException("Resource expected to be acct:username@host: "+resource.toString());
		}
		
		WebfingerResponse response = new WebfingerResponse(resource, List.of(
				new Link("self","application/activity+json",actorUri.toString())));
		
		return response;
	}

	public static class BadRequestException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3536512365470851068L;

		public BadRequestException(String message) {
			super(message);
		}
		
	}
}
