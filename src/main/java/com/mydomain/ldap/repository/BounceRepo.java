package com.mydomain.ldap.repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import com.mydomain.ldap.entity.Bounce;

@Component
public class BounceRepo {

	Logger logger = LoggerFactory.getLogger(BounceRepo.class);
	
	@Autowired
	protected LdapTemplate ldapTemplate;
	
	public Optional<Bounce> findByDn(String dn) {
		Bounce bounce  = ldapTemplate.lookup(dn, new BounceAttributesMapper());
		return Optional.ofNullable(bounce);
	}
	
	public List<Bounce> findAll() {
		return ldapTemplate.search(query().where("dn").like("%bounce%"), new BounceAttributesMapper());
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
