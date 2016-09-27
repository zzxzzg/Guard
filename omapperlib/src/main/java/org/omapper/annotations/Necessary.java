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
public @interface Necessary {

	/**
	 * Name.
	 * 
	 * @return the class
	 */
	@SuppressWarnings("rawtypes")
	boolean necessary() default true;  //是否必须要映射
	boolean nullable() default false; // 映射之后是否可以为空
}
