/**
 * 
 */
package org.omapper.util;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omapper.annotations.Implementation;
import org.omapper.annotations.Mappable;
import org.omapper.enums.FieldType;
import org.omapper.enums.MappingType;
import org.omapper.exception.IncompatibleFieldsException;
import org.omapper.exception.NonMappableTargetBeanException;
import org.omapper.exception.UnknownTypeException;

/**
 * The Class MapperUtil.
 * 
 * @author Sachin
 */

public class MapperUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MapperUtil.class);

	/**
	 * Gets the field map for all the fields in the passed classes array.
	 * 
	 * @param targetClasses
	 *            the target classes
	 * @return the field map
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Field> getFieldMap(Class... targetClasses) {
		if (logger.isDebugEnabled()) {
			logger.debug("getFieldMap(Class) - start"); //$NON-NLS-1$
		}

		Map<String, Field> fieldMap = new HashMap<String, Field>();
		for (Class targetClass : targetClasses) {

			Field[] fieldArray = targetClass.getDeclaredFields();

			for (Field field : fieldArray) {

				fieldMap.put(constructFieldMappingKey(field), field);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFieldMap(Class) - end"); //$NON-NLS-1$
		}
		return fieldMap;
	}

	/**
	 * Check if compatible.
	 * 
	 * @param sourceField
	 *            the source field
	 * @param targetField
	 *            the target field
	 * @throws IncompatibleFieldsException
	 *             the incompatible fields exception
	 */
	public static void checkIfCompatible(Field sourceField, Field targetField)
			throws IncompatibleFieldsException {
		if (logger.isDebugEnabled()) {
			logger.debug("checkIfCompatible(Field, Field) - start"); //$NON-NLS-1$
		}

		if (!(targetField.getType().isAssignableFrom(sourceField.getType()))) {
			throw new IncompatibleFieldsException("Source Field:"
					+ sourceField.getName() + " Type:" + sourceField.getType()
					+ " is not compatible with target field:"
					+ targetField.getName() + " of Type:"
					+ targetField.getType());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("checkIfCompatible(Field, Field) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Construct field mapping key.
	 * 
	 * @param targetField
	 *            the target field
	 * @return the string
	 */
	public static String constructFieldMappingKey(Field targetField) {
		if (logger.isDebugEnabled()) {
			logger.debug("constructFieldMappingKey(Field) - start"); //$NON-NLS-1$
		}

		StringBuilder key = new StringBuilder(targetField.getDeclaringClass()
				.getCanonicalName()).append('.').append(targetField.getName());
		String returnString = key.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("constructFieldMappingKey(Field) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Check if mappable.
	 * 
	 * @param annotatedElement
	 *            the annotated elements
	 */
	public static void checkIfMappable(AnnotatedElement annotatedElement) {
		if (logger.isDebugEnabled()) {
			logger.debug("checkIfMappable(AnnotatedElement) - start"); //$NON-NLS-1$
		}

		if (annotatedElement != null) {
			if (!annotatedElement.isAnnotationPresent(Mappable.class)) {
				throw new NonMappableTargetBeanException(
						"Target Bean Class:"
								+ annotatedElement
								+ " is not mappable.\n Please add @Mappable annotation to the beans which needs to managed by OMapper");
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("checkIfMappable(AnnotatedElement) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Parses the field and returns the field type
	 * 
	 * @param field
	 * @return
	 */
	public static FieldType getFieldType(Field field) {
		if (logger.isDebugEnabled()) {
			logger.debug("getFieldType(Field) - start"); //$NON-NLS-1$
		}

		Class<?> fieldType = field.getType();
		FieldType fieldTypeEnum = null;
		if (Collection.class.isAssignableFrom(fieldType)) {
			fieldTypeEnum = FieldType.COLLECTION;
		} else if (Map.class.isAssignableFrom(fieldType)) {
			fieldTypeEnum = FieldType.MAP;
		} else if (field.getType().isEnum()) {
			fieldTypeEnum = FieldType.ENUM;
		} else if (field.getType().isArray()) {
			fieldTypeEnum = FieldType.ARRAY;
		} else if (field.getType().isAnnotationPresent(Mappable.class)) {
			fieldTypeEnum = FieldType.USER;
		} else if (fieldType.isInterface()
				|| Modifier.isAbstract(fieldType.getModifiers())) {
			fieldTypeEnum = FieldType.TEMPLATE;
		}

		else {
			fieldTypeEnum = FieldType.JAVA;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFieldType(Field) - end"); //$NON-NLS-1$
		}
		return fieldTypeEnum;

	}

	/**
	 * Checks if the passed field is parameterized or not
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isParameterized(Field field) {
		if (logger.isDebugEnabled()) {
			logger.debug("isParameterized(Field) - start"); //$NON-NLS-1$
		}

		boolean returnboolean = (!(field.getType() == field.getGenericType()));
		if (logger.isDebugEnabled()) {
			logger.debug("isParameterized(Field) - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}

	/**
	 * Returns the class of the parameterized field like for List<String> , this
	 * method would return String class object
	 * 
	 * @param field
	 * @return
	 */
	public static Class<?> getParamterizedType(Field field) {
		if (logger.isDebugEnabled()) {
			logger.debug("getParamterizedType(Field) - start"); //$NON-NLS-1$
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getParamterizedType(Field) - end"); //$NON-NLS-1$
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Object createTargetFieldArrayInstance(Field targetField,
			int length) {
		if (logger.isDebugEnabled()) {
			logger.debug("createTargetFieldArrayInstance(Field, int) - start"); //$NON-NLS-1$
		}

		Class<?> componentType = targetField.getType().getComponentType();
		Object targetObject = null;
		if (componentType.isPrimitive()) {
			targetObject = Array.newInstance(componentType, length);
		} else if (componentType.isInterface()
				|| Modifier.isAbstract(componentType.getModifiers())) {
			if (targetField.isAnnotationPresent(Implementation.class)) {
				Implementation interfaceAnnot = targetField
						.getAnnotation(Implementation.class);
				Class interfaceImpl = interfaceAnnot.name();
				targetObject = Array.newInstance(interfaceImpl, length);
			} else {
				throw new UnknownTypeException(
						"Type of target field could not be determined, use @interface annotaion to specify implementation type for interface types");
			}

		} else {
			targetObject = Array.newInstance(componentType, length);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createTargetFieldArrayInstance(Field, int) - end"); //$NON-NLS-1$
		}
		return targetObject;

	}

	/**
	 * Creates the target field instance.
	 * 
	 * @param targetField
	 *            the target field
	 * @return the object
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object createTargetFieldInstance(Field targetField)
			throws InstantiationException, IllegalAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("createTargetFieldInstance(Field) - start"); //$NON-NLS-1$
		}

		Object targetObject = null;

		if (targetField.getType().isArray()) {
			if (logger.isDebugEnabled()) {
				logger.debug("createTargetFieldInstance(Field) - CType:" + targetField.getType()); //$NON-NLS-1$
			}
			targetObject = targetField.getType().getComponentType()
					.newInstance();
		} else if (targetField.getType().isInterface()
				|| Modifier.isAbstract(targetField.getType().getModifiers())) {
			if (targetField.isAnnotationPresent(Implementation.class)) {
				Implementation interfaceAnnot = targetField
						.getAnnotation(Implementation.class);
				Class interfaceImpl = interfaceAnnot.name();
				targetObject = interfaceImpl.newInstance();
			} else {
				throw new UnknownTypeException(
						"Type of target field could not be determined, use @interface annotaion to specify implementation type for interface types");
			}

		}

		else {
			targetObject = targetField.getType().newInstance();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createTargetFieldInstance(Field) - end"); //$NON-NLS-1$
		}
		return targetObject;
	}

	public static MappingType getMappingType(Class<?> targetClass,
			Class<?>[] sourceClass) {
		if (logger.isDebugEnabled()) {
			logger.debug("getMappingType(Class<?>, Class<?>[]) - start"); //$NON-NLS-1$
		}

		if (targetClass.isAnnotationPresent(Mappable.class)) {
			if (logger.isDebugEnabled()) {
				logger.debug("getMappingType(Class<?>, Class<?>[]) - end"); //$NON-NLS-1$
			}
			return MappingType.TARGET;
		} else {
			boolean isMappable = true;
			for (Class<?> source : sourceClass) {
				if (!source.isAnnotationPresent(Mappable.class)) {
					isMappable = false;
				}
			}

			if (isMappable) {
				if (logger.isDebugEnabled()) {
					logger.debug("getMappingType(Class<?>, Class<?>[]) - end"); //$NON-NLS-1$
				}
				return MappingType.SOURCE;
			} else {
//				throw new NonMappableTargetBeanException(
//						"Either target bean Class or all the source bean classes MUST be mappable."+ Arrays.toString(sourceClass)
//
//								+ "\n Please add @Mappable annotation to the beans which needs to managed by OMapper");

			}
			return MappingType.NONE;
		}
	}

}
