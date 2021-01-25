package com.joon.springsecurityproject.account;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "joon",roles = {"USER"})
public @interface WithUser {
}
