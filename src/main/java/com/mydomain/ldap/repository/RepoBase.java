package com.mydomain.ldap.repository;

import org.springframework.ldap.core.LdapTemplate;

public abstract class RepoBase {

	protected LdapTemplate ldapTemplate;
	
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}
}
