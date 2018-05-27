package com.mydomain.ldap.repository;

import java.util.List;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import com.mydomain.ldap.entity.Person;

@Component
public interface PersonRepo {

	public void setLdapTemplate(LdapTemplate ldapTemplate);
	public List<String> getAllPersonNames();
	public List<Person> getAllPersons();
	public Person findPerson(String dn);
	public void create(Person p);
}
