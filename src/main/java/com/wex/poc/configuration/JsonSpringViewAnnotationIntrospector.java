package com.wex.poc.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

@Component
public class JsonSpringViewAnnotationIntrospector extends
		JacksonAnnotationIntrospector implements Versioned {

	@Override
	protected boolean _isIgnorable(Annotated annotatedField) {

		System.out.println("COming here");
		if (annotatedField.hasAnnotation(JsonSpringView.class)) {
			JsonSpringView jsonSpringView = annotatedField
					.getAnnotation(JsonSpringView.class);
			if (jsonSpringView.springRoles() != null) {
				Object principal = SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				if(!SecurityContextHolder.getContext()
						.getAuthentication().isAuthenticated()){
					return true;
				}
				if (principal != null && principal instanceof UserDetails) {
					UserDetails principalUserDetails = (UserDetails) principal;
					Collection<? extends GrantedAuthority> authorities = principalUserDetails
							.getAuthorities();
					List<String> requiredRoles = Arrays.asList(jsonSpringView
							.springRoles().split(","));

					for (String requiredRole : requiredRoles) {

						System.out.println("Role is:" + requiredRole);
						if (authorities.contains(new SimpleGrantedAuthority(requiredRole)))

							return false;
					}

					return true;
				}
			}
		}

		return super._isIgnorable(annotatedField);
	}

	// SOME METHODS HERE
	public boolean isIgnorableField(AnnotatedField annotatedField) {
		if (annotatedField.hasAnnotation(JsonSpringView.class)) {
			JsonSpringView jsonSpringView = annotatedField
					.getAnnotation(JsonSpringView.class);
			if (jsonSpringView.springRoles() != null) {
				Object principal = SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				if (principal != null && principal instanceof UserDetails) {
					UserDetails principalUserDetails = (UserDetails) principal;
					Collection<? extends GrantedAuthority> authorities = principalUserDetails
							.getAuthorities();
					List<String> requiredRoles = Arrays.asList(jsonSpringView
							.springRoles().split(","));

					for (String requiredRole : requiredRoles) {

						System.out.println("Role is:" + requiredRole);
						if (authorities.contains(requiredRole))
							// if The Method Have @JsonSpringView Behind it, and
							// Current User has The Required Permission(Feature,
							// Right, ... . Anything You may Name It).
							return false;
					}
					// if The Method Have @JsonSpringView Behind it, but the
					// Current User doesn't have The required
					// Permission(Feature, Right, ... . Anything You may Name
					// It).
					return true;
				}
			}
		}
		// if The Method Doesn't Have @JsonSpringView Behind it.
		return false;
	}

	@Override
	public Version version() {
		// TODO Auto-generated method stub
		return null;
	}
}