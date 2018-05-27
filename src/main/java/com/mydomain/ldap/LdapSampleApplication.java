package com.mydomain.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.core.LdapTemplate;

import com.mydomain.ldap.entity.Person;
import com.mydomain.ldap.repository.PersonRepo;

@SpringBootApplication
public class LdapSampleApplication implements CommandLineRunner {

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private PersonRepo personRepo;

	public static void main(String[] args) {
		SpringApplication.run(LdapSampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		personRepo.setLdapTemplate(ldapTemplate);
		println(personRepo.getAllPersonNames());

		// Base DN から追加された部分の属性のみの指定で良い
		println(personRepo.findPerson("cn=Taro"));

		personRepo.create(createPerson("Shiro", "Shiro", "shiro".getBytes()));

		println(personRepo.getAllPersons());
	}

	public Person createPerson(String name, String fullname, byte[] userPassword) {
		Person p = new Person();
		p.setName(name);
		p.setFullname(fullname);
		p.setUserPassword(userPassword);
		return p;
	}

	public static void println(Object o) {
		System.out.println(o.toString());
	}

}
