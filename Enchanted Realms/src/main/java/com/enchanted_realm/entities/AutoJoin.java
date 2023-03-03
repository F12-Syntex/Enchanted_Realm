package com.enchanted_realm.entities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * marker annotation responsibile for marking entities who are to be dynamically rendered
 * @author https://github.com/F12-Syntex
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoJoin {}
