package com.ppol.auth.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.ppol.auth.util.constatnt.enums.Provider;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private final Object credentials;
	private Provider provider;

	public CustomAuthenticationToken(Object principal, Object credentials, Provider provider) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.provider = provider;
		setAuthenticated(false);
	}

	public CustomAuthenticationToken(Object principal, Object credentials, Provider provider, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.provider = provider;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
}