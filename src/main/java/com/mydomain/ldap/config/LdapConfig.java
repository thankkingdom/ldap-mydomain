package com.mydomain.ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {

	@Bean
	public LdapTemplate ldapTemplate() {
		
		LdapContextSource lcs = new LdapContextSource();
		lcs.setUrl("ldap://localhost:389");
		lcs.setBase("dc=my-domain,dc=com");
		lcs.setUserDn("cn=Manager,dc=my-domain,dc=com");
		lcs.setPassword("secret");
		lcs.setDirObjectFactory(DefaultDirObjectFactory.class);
		lcs.afterPropertiesSet();
		return new LdapTemplate(lcs);
	}
}
