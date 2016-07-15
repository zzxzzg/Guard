/**
 * 
 */
package org.omapper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * The Annotation Source.
 *
 * @author Sachin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Source {
	
	/**
	 * Type-This value defines the source class.
	 * 
	 * @return the class
	 */
	@SuppressWarnings("rawtypes")
	public Class type();
	
	/**
	 * Property-This value defines the property value which corresponds to
	 * mapped value.
	 * 
	 * @return the string
	 */
	public String property();
	
	
	
	

}
