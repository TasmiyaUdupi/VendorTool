package com.ot.VendorTool.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtResponse {

	private String jwtToken;

	private String role;

	private long id;

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public JwtResponse(String jwtToken, String role, long id) {
		super();
		this.jwtToken = jwtToken;
		this.role = role;
		this.id = id;
	}

}
