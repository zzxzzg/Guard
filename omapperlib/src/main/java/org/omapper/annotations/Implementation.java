/**
 * 
 */
package org.omapper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface Implementation.
 * 
 * @author Sachin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Implementation {

	/**
	 * Name.
	 * 
	 * @return the class
	 */
	@SuppressWarnings("rawtypes")
	public Class name();
}
