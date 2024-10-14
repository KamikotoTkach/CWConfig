package ru.cwcode.tkach.config.repository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryOptions {
  String name() default "";
}
