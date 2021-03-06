// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.meta;

import jodd.madvoc.path.ActionNamingStrategy;
import jodd.madvoc.path.RestResourcePath;
import jodd.madvoc.result.ActionResult;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Extension of {@link jodd.madvoc.meta.Action} for REST resources
 * that uses {@link jodd.madvoc.path.RestResourcePath different naming}
 * convention.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface RestAction {

	/**
	 * Action path value. If equals to {@link Action#NONE} action method name
	 * will not be part of the created action path.
	 */
	String value() default "";

	//String extension() default "";

	/**
	 * Defines alias.
	 */
	String alias() default "";

	/**
	 * Defines action method (such as HTTP request method: GET, POST....).
	 * Ignore it or use {@link Action#ANY} to ignore the method.
	 */
	String method() default "";

	/**
	 * Defines if action has to be called asynchronously
	 * using Servlets 3.0 API.
	 */
	boolean async() default false;

	/**
	 * Defines {@link jodd.madvoc.result.ActionResult action result handler}
	 * that is going to render the result object.
	 */
	Class<? extends ActionResult> result() default ActionResult.class;

	/**
	 * Defines the way how mapping is done: using {@link jodd.madvoc.path.RestResourcePath}.
	 */
	Class<? extends ActionNamingStrategy> path() default RestResourcePath.class;

}