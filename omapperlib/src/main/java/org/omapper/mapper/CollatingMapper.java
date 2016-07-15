/**
 * 
 */
package org.omapper.mapper;

import org.apache.log4j.Logger;


/**
 * This mapper is used to collate data from mutiple beans to one bean.
 *
 * @param <T> the generic type
 * @author Sachin
 */

public class CollatingMapper<T> extends AbstractMapper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CollatingMapper.class);

	/**
	 * Instantiates a new collating mapper.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param sourceClasses
	 *            the source classes
	 */
	public CollatingMapper(Class<T> targetClass,
			Class<? extends Object>... sourceClasses) {

		super(targetClass, sourceClasses);
	}

	/* (non-Javadoc)
	 * @see org.omapper.mapper.AbstractMapper#mapBean(java.lang.Object, java.lang.Object[])
	 */
	public void mapBean(T target, Object... source) {
		if (logger.isDebugEnabled()) {
			logger.debug("mapBean(Object, Object) - start"); //$NON-NLS-1$
		}
	
		super.mapBeanDefault(target, source);
		
		if (logger.isDebugEnabled()) {
			logger.debug("mapBean(Object, Object) - end"); //$NON-NLS-1$
		}
	}
	

}
