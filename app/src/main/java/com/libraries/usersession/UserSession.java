package com.libraries.usersession;

public class UserSession {

	int id;
	String identity_number;
	String hash_login;
	String full_name;
	String email;
	String phone_number;


	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getPhone_number() {
		return phone_number;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setHash_login(String hash_login) {
		this.hash_login = hash_login;
	}
	public String getHash_login() {
		return hash_login;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}

	public void setIdentity_number(String identity_number) {
		this.identity_number = identity_number;
	}
	public String getIdentity_number() {
		return identity_number;
	}
	
}
