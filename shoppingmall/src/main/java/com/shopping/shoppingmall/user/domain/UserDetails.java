package com.shopping.shoppingmall.user.domain;

import java.io.Serializable;
// import java.util.Collection;

// import org.springframework.security.core.GrantedAuthority;

public interface UserDetails extends Serializable{
	
	//    Collection<? extends GrantedAuthority> getAuthorities();

		Long getId();

	    String getPassword(); 

	    String getUsername();

	    boolean isDeleted(); 
	    

}
