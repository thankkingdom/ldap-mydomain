package com.mydomain.ldap.repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;
import java.util.Optional;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Component;

import com.mydomain.ldap.entity.Person;

@Component
public class PersonRepoImpl implements PersonRepo {

	public static final String BASE_DN = "dc=my-domain,dc=com";

	private LdapTemplate ldapTemplate;

	protected Name buildDn(Person p) {
		//@formatter:off
		return LdapNameBuilder.newInstance(BASE_DN)
	      //.add("cn", p.getName())
	      //.add("sn", p.getFullname())
	      //.add("userPassword", p.getUserPassword())
	      .build();
		//@formatter:on
	}

	public void create(Person p) {
		//Name dn = buildDn(p);
		//ldapTemplate.bind(dn, null, buildAttributes(p));

		//DirContextAdapter context = new DirContextAdapter(dn);
		//context.setAttributeValue("dc", dn);
		//context.setAttributeValues("objectclass", new String[] { "top", "person" });
		//context.setAttributeValue("cn", p.getName());
		//context.setAttributeValue("sn", p.getFullname());
		//context.setAttributeValue("userPassword", p.getUserPassword());
		//ldapTemplate.bind(context);
		
		//p.setDn(dn);
		ldapTemplate.create(p);
	}

	private Attributes buildAttributes(Person p) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add("person");
		attrs.put(ocattr);
		// attrs.put("dn", p.getDn());
		attrs.put("cn", p.getName());
		attrs.put("sn", p.getFullname());
		attrs.put("userPassword", p.getUserPassword());
		return attrs;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	// 1つの属性を取得
	public List<String> getAllPersonNames() {
		return ldapTemplate.search(query().where("objectclass").is("person"), new AttributesMapper<String>() {
			public String mapFromAttributes(Attributes attrs) throws NamingException {
				return attrs.get("cn").get().toString();
			}
		});
	}

	private class PersonAttributesMapper implements AttributesMapper<Person> {
		public Person mapFromAttributes(Attributes attrs) throws NamingException {
			Person person = new Person();
			person.setName((String) attrs.get("cn").get());
			person.setFullname((String) attrs.get("sn").get());
			//person.setUserPassword((byte[]) attrs.get("userPassword").get());
			Optional.ofNullable(attrs.get("userPassword")).ifPresent(consumer -> {
				try {
					person.setUserPassword((byte[]) consumer.get());
				} catch (NamingException e) {
					e.printStackTrace();
				}
			});
			return person;
		}
	}

	// 複数の属性を取得
	public List<Person> getAllPersons() {
		return ldapTemplate.search(query().where("objectclass").is("person"), new PersonAttributesMapper());
	}

	public Person findPerson(String dn) {
		return ldapTemplate.lookup(dn, new PersonAttributesMapper());
	}
}
