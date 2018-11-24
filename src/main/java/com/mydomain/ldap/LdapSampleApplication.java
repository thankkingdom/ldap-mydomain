package com.mydomain.ldap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.core.LdapTemplate;

import com.mydomain.ldap.entity.Bounce;
import com.mydomain.ldap.entity.Person;
import com.mydomain.ldap.repository.BounceRepo;
import com.mydomain.ldap.repository.PersonRepo;

@SpringBootApplication
public class LdapSampleApplication implements CommandLineRunner {

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private PersonRepo personRepo;
	
	@Autowired
	private BounceRepo bounceRepo;

	public static void main(String[] args) {
		SpringApplication.run(LdapSampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//runPerson();
		
		//println(bounceRepo.findByDn("email=test01@my-domain.com"));
		
		for(int i=1; i<=10; i++) {
			String email = String.format("test%05d@my-domain.com", i);
			if (!bounceRepo.findByDn("dn:" + email + ",cn:bounce").isPresent()) {
				bounceRepo.create(email);
			}
		}
		
		List<Bounce> list = bounceRepo.findAll();
		list.forEach(action -> println(action.getDn()));
	}

	private void runPerson() {
		personRepo.setLdapTemplate(ldapTemplate);
		println(personRepo.getAllPersonNames());
		// Base DN から追加された部分の属性のみの指定で良い
		println(personRepo.findPerson("cn=Taro"));
		personRepo.create(createPerson("Shiro", "Shiro", "shiro".getBytes()));
		println(personRepo.getAllPersons());
	}
	
	private Person createPerson(String name, String fullname, byte[] userPassword) {
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
