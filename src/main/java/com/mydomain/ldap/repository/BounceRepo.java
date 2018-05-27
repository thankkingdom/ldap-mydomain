package com.mydomain.ldap.repository;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.stereotype.Component;

import com.mydomain.ldap.entity.Bounce;

@Component
public class BounceRepo extends RepoBase {

	public Bounce findByDn(String dn) {
		
		return ldapTemplate.lookup(dn, new BounceAttributesMapper());
	}
	
	public void create(String email) {
		Bounce bounce = new Bounce();
		bounce.setEmail(email);
		ldapTemplate.create(bounce);
	}
	
	private class BounceAttributesMapper implements AttributesMapper<Bounce> {

		@Override
		public Bounce mapFromAttributes(Attributes attributes) throws NamingException {
			Bounce bounce = new Bounce();
			bounce.setEmail((String) attributes.get("email").get());
			return bounce;
		}
		
	}
}
