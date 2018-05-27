package com.mydomain.ldap.entity;


import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Attribute.Type;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import lombok.Data;

@Data
@Entry(objectClasses = { "Person", "top" })
public class Person {

	@Id
	private Name dn;

	@Attribute(name = "cn")
	@DnAttribute(value = "cn", index = 0)
	private String name;

	@Attribute(name = "sn")
	private String fullname;

	@Attribute(name = "userPassword", type = Type.BINARY)
	private byte[] userPassword;
}
