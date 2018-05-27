package com.mydomain.ldap.entity;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import lombok.Data;

@Data
@Entry(objectClasses = { "top" })
public class Bounce {

	@Id
	private Name dn;

	@Attribute(name = "email")
	@DnAttribute(value = "email", index = 0)
	private String email;
}
