package org.jboss.jsr299.tck.tests.jbt.quickfixes;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target( { TYPE, METHOD, PARAMETER, FIELD })
@Documented
@Qualifier
public @interface ChangeAnnotation {

}
