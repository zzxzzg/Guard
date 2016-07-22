/*
 * 
 */
package org.omapper.mapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omapper.annotations.Mappable;
import org.omapper.annotations.Sink;
import org.omapper.annotations.Source;
import org.omapper.enums.FieldType;
import org.omapper.enums.MappingType;
import org.omapper.exception.IncompatibleFieldsException;
import org.omapper.exception.UnableToMapException;
import org.omapper.exception.UnknownPropertyException;
import org.omapper.exception.UnknownTypeException;
import org.omapper.util.MapperUtil;

/**
 * The Class AbstractMapper.
 * 
 * @author Sachin
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractMapper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractMapper.class);

	/** The field mapping map. */
	protected final Map<String, MapEntry> fieldMappingMap;

	/**
	 * Instantiates a new abstract mapper.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param sourceClass
	 *            the source class
	 */
	public AbstractMapper(Class targetClass, Class... sourceClass) {

		fieldMappingMap = new HashMap<String, MapEntry>();
		initFieldMaps(targetClass, sourceClass);

	}

	/**
	 * Inits the field maps.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param sourceClass
	 *            the source class
	 */
	protected void initFieldMaps(Class targetClass, Class... sourceClass) {
		if (logger.isDebugEnabled()) {
			logger.debug("initFieldMaps(Class, Class) - start"); //$NON-NLS-1$
	}

		Map<String, Class> sourceClassMap = new HashMap<String, Class>();
		// cache class objects in map
		for (Class source : sourceClass) {
			sourceClassMap.put(source.getCanonicalName(), source);
		}

		MappingType mappingType = MapperUtil.getMappingType(targetClass,
				sourceClass);

		switch (mappingType) {
		case SOURCE:
			if (logger.isDebugEnabled()) {
				logger.debug("initFieldMaps(Class, Class) - MApping Type:Source"); //$NON-NLS-1$
			}
			initFieldMapFromSource(targetClass, sourceClassMap);
			break;
		case TARGET:
			if (logger.isDebugEnabled()) {
				logger.debug("initFieldMaps(Class, Class) - MApping Type:Target"); //$NON-NLS-1$
			}
			initFieldMapFromTarget(targetClass, sourceClassMap);
			break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("initFieldMaps(Class, Class) - " + fieldMappingMap); //$NON-NLS-1$
		}

		if (logger.isDebugEnabled()) {
			logger.debug("initFieldMaps(Class, Class) - end"); //$NON-NLS-1$
	}
	}

	private void initFieldMapFromSource(Class targetClass,
			Map<String, Class> sourceClassMap) {
		if (logger.isDebugEnabled()) {
			logger.debug("initFieldMapFromSource(Class, Map<String,Class>) - start"); //$NON-NLS-1$
	}

		// TODO Auto-generated method stub

		for (Map.Entry<String, Class> entry : sourceClassMap.entrySet()) {
			Field[] sourceFieldArray = entry.getValue().getDeclaredFields();
			for (Field sourceField : sourceFieldArray) {
				String sourceFieldName = sourceField.getName();
				sourceField.setAccessible(true);

				FieldType fieldType = MapperUtil.getFieldType(sourceField);
				if (logger.isDebugEnabled()) {
					logger.debug("initFieldMapFromSource(Class, Map<String,Class>) - Found target Field:" + sourceFieldName + " of type:" + fieldType); //$NON-NLS-1$ //$NON-NLS-2$
				}

				if (!sourceField.isAnnotationPresent(Sink.class)) {

					if (logger.isDebugEnabled()) {
						logger.debug("initFieldMapFromSource(Class, Map<String,Class>) - No annotation mapping found for field:" + sourceField + " so skipping it"); //$NON-NLS-1$ //$NON-NLS-2$
					}
					continue;
				}

				Sink targetAnnotation = sourceField.getAnnotation(Sink.class);

				if (null == targetAnnotation) {
					if (logger.isDebugEnabled()) {
						logger.debug("initFieldMapFromSource(Class, Map<String,Class>) - No target annotation found for field :" + sourceField + " so skipping it"); //$NON-NLS-1$ //$NON-NLS-2$
					}
					continue;
				}

				String targetFieldName = targetAnnotation.property();
				Class targetClassName = targetAnnotation.type();
				if (!targetClassName.getCanonicalName().equals(
						targetClass.getCanonicalName())) {
					if (logger.isDebugEnabled()) {
						logger.debug("initFieldMapFromSource(Class, Map<String,Class>) - Target Class specified is different from parameters for field:" + sourceFieldName + " Specified:" + targetClassName.getCanonicalName() + " Allowed Target Class:" + targetClass.getCanonicalName() + " so skipping it"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					}
					continue;
				}

				try {
					Field targetField = targetClassName
							.getDeclaredField(targetFieldName);

					targetField.setAccessible(true);
					MapEntry entryFieldMap = new MapEntry(sourceField,
							targetField);
					String fieldMappingKey = MapperUtil
							.constructFieldMappingKey(targetField);
					if (!fieldMappingMap.containsKey(fieldMappingKey)) {
						fieldMappingMap.put(fieldMappingKey, entryFieldMap);
					}
					if (sourceField.getType().isAnnotationPresent(
							Mappable.class)
							|| targetField.getType().isAnnotationPresent(
									Mappable.class)) {
						initFieldMaps(targetField.getType(),
								sourceField.getType());
					} else if (Collection.class.isAssignableFrom(targetField
							.getType())
							|| Map.class
									.isAssignableFrom(targetField.getType())) {
						if ((targetField.getGenericType() == targetField
								.getType())
								&& (sourceField.getGenericType() == sourceField
										.getType())) {
							if (logger.isDebugEnabled()) {
								logger.debug("initFieldMapFromSource(Class, Map<String,Class>) - Found non parameterized collections field:" + targetField + " skipping it as not supported yet"); //$NON-NLS-1$ //$NON-NLS-2$
							}
						} else {
							initFieldMaps(
									(ParameterizedType) targetField
											.getGenericType(),
									(ParameterizedType) sourceField
											.getGenericType());

						}

					} else if (targetField.getType().isArray()
							&& sourceField.getType().isArray()) {
						if (!sourceField.getType().getComponentType()
								.isPrimitive()
								&& (!sourceField.getType().getComponentType()
										.equals(String.class))) {
							initFieldMaps(targetField.getType()
									.getComponentType(), sourceField.getType()
									.getComponentType());
						}
					}
				} catch (NoSuchFieldException e) {
					logger.error("initFieldMapFromSource(Class, Map<String,Class>)", e); //$NON-NLS-1$

					throw new UnknownPropertyException("Sink Property:"
							+ targetFieldName
							+ " defined in annotation for field :"
							+ sourceFieldName
							+ " is not prsent in target type:"
							+ targetClassName);
				}

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("initFieldMapFromSource(Class, Map<String,Class>) - end"); //$NON-NLS-1$
	}
	}

	private void initFieldMapFromTarget(Class targetClass,
			Map<String, Class> sourceClassMap) {
		if (logger.isDebugEnabled()) {
			logger.debug("initFieldMapFromTarget(Class, Map<String,Class>) - start"); //$NON-NLS-1$
	}

		Field[] targetFieldsArray = targetClass.getDeclaredFields();
		for (Field targetField : targetFieldsArray) {

			String targetFieldName = targetField.getName();
			targetField.setAccessible(true);

			FieldType fieldType = MapperUtil.getFieldType(targetField);
			if (logger.isDebugEnabled()) {
				logger.debug("initFieldMapFromTarget(Class, Map<String,Class>) - Found target Field:" + targetFieldName + " of type:" + fieldType); //$NON-NLS-1$ //$NON-NLS-2$
			}

			if (!targetField.isAnnotationPresent(Source.class)) {

				if (logger.isDebugEnabled()) {
					logger.debug("initFieldMapFromTarget(Class, Map<String,Class>) - No annotation mapping found for field:" + targetField + " so skipping it"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			} else {

				Source sourceAnnotation = targetField
						.getAnnotation(Source.class);
				if (null == sourceAnnotation) {
					if (logger.isDebugEnabled()) {
						logger.debug("initFieldMapFromTarget(Class, Map<String,Class>) - No source annotation found for field :" + targetField + " so skipping it"); //$NON-NLS-1$ //$NON-NLS-2$
					}
					continue;
				}

				if (null != sourceAnnotation) {
					String sourceFieldName = sourceAnnotation.property();
					Class sourceClassName = sourceAnnotation.type();
					if (!sourceClassMap.containsKey(sourceClassName
							.getCanonicalName())) {
						throw new UnknownTypeException(
								"The source class in annotation :"
										+ sourceClassName
										+ " is not valid, valid values for the type: "
										+ sourceClassMap.keySet());

					}

					try {
						Field sourceField = sourceClassName
								.getDeclaredField(sourceFieldName);

						sourceField.setAccessible(true);
						MapEntry entry = new MapEntry(sourceField, targetField);
						String fieldMappingKey = MapperUtil
								.constructFieldMappingKey(targetField);
						if (!fieldMappingMap.containsKey(fieldMappingKey)) {
							fieldMappingMap.put(fieldMappingKey, entry);
						}
						if (targetField.getType().isAnnotationPresent(
								Mappable.class)) {
							initFieldMaps(targetField.getType(),
									sourceField.getType());
						} else if (Collection.class
								.isAssignableFrom(targetField.getType())
								|| Map.class.isAssignableFrom(targetField
										.getType())) {
							if ((targetField.getGenericType() == targetField
									.getType())
									&& (sourceField.getGenericType() == sourceField
											.getType())) {
								if (logger.isDebugEnabled()) {
									logger.debug("initFieldMapFromTarget(Class, Map<String,Class>) - Found non parameterized collections field:" + targetField + " skipping it as not supported yet"); //$NON-NLS-1$ //$NON-NLS-2$
								}
							} else {
								initFieldMaps(
										(ParameterizedType) targetField
												.getGenericType(),
										(ParameterizedType) sourceField
												.getGenericType());

							}

						}else if (targetField.getType().isArray()
								&& sourceField.getType().isArray()) {
							if (!sourceField.getType().getComponentType()
									.isPrimitive()
									&& (!sourceField.getType().getComponentType()
											.equals(String.class))) {
								initFieldMaps(targetField.getType()
										.getComponentType(), sourceField.getType()
										.getComponentType());
							}
						}
					} catch (NoSuchFieldException e) {
						logger.error("initFieldMapFromTarget(Class, Map<String,Class>)", e); //$NON-NLS-1$

						throw new UnknownPropertyException("Source Property:"
								+ sourceFieldName
								+ " defined in annotation for field :"
								+ targetField
								+ " is not prsent in source type:"
								+ sourceClassName);
					}
				}
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("initFieldMapFromTarget(Class, Map<String,Class>) - end"); //$NON-NLS-1$
	}
	}

	public void mapCollectionBean(Collection targetCollection, Class targetClass,
								  Collection sourceCollection, Class sourceClass) {
		try {
			if (targetClass == sourceClass) {
				targetCollection.addAll(sourceCollection);
			} else {
				Iterator sourceIterator = sourceCollection.iterator();
				while (sourceIterator.hasNext()) {
					Object targetCollectionElementObject = targetClass.newInstance();
					mapBeanDefault(targetCollectionElementObject, sourceIterator.next());
					targetCollection.add(targetCollectionElementObject);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Map bean.
	 * 
	 * @param target
	 *            the target
	 * @param source
	 *            the source
	 */
	protected void mapBeanDefault(Object target, Object... source) {
		if (logger.isDebugEnabled()) {
			logger.debug("mapBeanDefault(Object, Object) - start"); //$NON-NLS-1$
	}

		try {

			Map<String, Object> sourceObjectMap = new HashMap<String, Object>();

			for (Object sourceObject : source) {
				sourceObjectMap.put(sourceObject.getClass().getCanonicalName(),
						sourceObject);
			}

			Field[] targetFields = target.getClass().getDeclaredFields();
			for (Field targetField : targetFields) {
				targetField.setAccessible(true);
				MapEntry entry = fieldMappingMap.get(MapperUtil
						.constructFieldMappingKey(targetField));
				if (entry != null) {
					Field sourceField = entry.getSourceField();

					Object sourceObject = sourceObjectMap.get(sourceField
							.getDeclaringClass().getCanonicalName());

					if (!isSourceFieldSet(sourceField, sourceObject)) {
						if (logger.isDebugEnabled()) {
							logger.debug("mapBeanDefault(Object, Object) - Source Field:" + sourceField.getName() + " not set so skipping it"); //$NON-NLS-1$ //$NON-NLS-2$
						}
						continue;
					}
					// recursively map the enclosed beans too
					if (targetField.getType().isAnnotationPresent(
							Mappable.class)
							|| sourceField.getType().isAnnotationPresent(
									Mappable.class)) {
						Object targetObject = MapperUtil
								.createTargetFieldInstance(targetField);
						mapBeanDefault(targetObject, sourceField.get(sourceObject));
						targetField.set(target, targetObject);
					} else if (Collection.class.isAssignableFrom(targetField
							.getType())
							|| Map.class
									.isAssignableFrom(targetField.getType())) {
						Object targetObject = MapperUtil
								.createTargetFieldInstance(targetField);
						mapCollectionBeans(targetObject, sourceObject,
								targetField, sourceField);
						targetField.set(target, targetObject);
						// To be done

					} else if (targetField.getType().isArray()
							&& sourceField.getType().isArray()) {
						Object targetObject = MapperUtil
								.createTargetFieldArrayInstance(targetField,
										Array.getLength(sourceField
												.get(sourceObject)));
						mapArrayBeans(targetObject, sourceObject, targetField,
								sourceField);
						targetField.set(target, targetObject);
					} else {

						targetField.set(target, sourceField.get(sourceObject));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("mapBeanDefault(Object, Object)", e); //$NON-NLS-1$

			throw new UnableToMapException(
					"Unable to map beans successfully due to an unexpected error: ",
					e);
		} catch (IllegalAccessException e) {
			logger.error("mapBeanDefault(Object, Object)", e); //$NON-NLS-1$

			throw new UnableToMapException(
					"Unable to map beans successfully due to an unexpected error: ",
					e);
		} catch (InstantiationException e) {
			logger.error("mapBeanDefault(Object, Object)", e); //$NON-NLS-1$

			throw new UnableToMapException(
					"Unable to map beans successfully due to an unexpected error: ",
					e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mapBeanDefault(Object, Object) - end"); //$NON-NLS-1$
	}
	}

	private boolean isSourceFieldSet(Field sourceField, Object sourceObject) {
		if (logger.isDebugEnabled()) {
			logger.debug("isSourceFieldSet(Field, Object) - start"); //$NON-NLS-1$
	}

		// TODO Auto-generated method stub
		boolean returnValue = false;
		try {
			if (null != sourceField.get(sourceObject)) {
				returnValue = true;
			} else {
				returnValue = false;
			}
		} catch (IllegalArgumentException e) {
			logger.error("isSourceFieldSet(Field, Object)", e); //$NON-NLS-1$

			// TODO Auto-generated catch block
			logger.error("isSourceFieldSet(Field, Object)", e); //$NON-NLS-1$
			returnValue = false;
		} catch (IllegalAccessException e) {
			logger.error("isSourceFieldSet(Field, Object)", e); //$NON-NLS-1$

			// TODO Auto-generated catch block
			logger.error("isSourceFieldSet(Field, Object)", e); //$NON-NLS-1$
			returnValue = false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isSourceFieldSet(Field, Object) - end"); //$NON-NLS-1$
	}
		return returnValue;
	}

	/**
	 * Overloaded Methods to initialize field map for parameterized bean classes
	 * like collections and maps etc.
	 * 
	 * @param genericTypeTarget
	 *            the generic type target
	 * @param genericTypeSource
	 *            the generic type source
	 */
	private void initFieldMaps(ParameterizedType genericTypeTarget,
			ParameterizedType genericTypeSource) {
		if (logger.isDebugEnabled()) {
			logger.debug("initFieldMaps(ParameterizedType, ParameterizedType) - start"); //$NON-NLS-1$
	}

		Type[] targetFieldTypes = genericTypeTarget.getActualTypeArguments();
		Type[] sourceFieldTypes = genericTypeSource.getActualTypeArguments();
		if (targetFieldTypes.length != sourceFieldTypes.length) {
			throw new IncompatibleFieldsException("Paramterized target type :"
					+ genericTypeTarget
					+ " is not compatible with source type:"
					+ genericTypeSource);
		}

		for (int typeCount = 0; typeCount < targetFieldTypes.length; typeCount++) {
			initFieldMaps((Class) targetFieldTypes[typeCount],
					(Class) sourceFieldTypes[typeCount]);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("initFieldMaps(ParameterizedType, ParameterizedType) - end"); //$NON-NLS-1$
	}
	}

	/**
	 * This method maps collection fields.
	 * 
	 * @param targetObject
	 *            the target object
	 * @param sourceObject
	 *            the source object
	 * @param targetField
	 *            the target field
	 * @param sourceField
	 *            the source field
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	@SuppressWarnings({ "unchecked" })
	private void mapCollectionBeans(Object targetObject, Object sourceObject,
			Field targetField, Field sourceField)
			throws InstantiationException, IllegalAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("mapCollectionBeans(Object, Object, Field, Field) - start"); //$NON-NLS-1$
	}

		// TODO Auto-generated method stub
		ParameterizedType targetFieldType = (ParameterizedType) targetField
				.getGenericType();

		ParameterizedType sourceFieldType = (ParameterizedType) sourceField
				.getGenericType();


		Collection targetCollection = (Collection) targetObject;
		Collection sourceCollection = (Collection) sourceField
				.get(sourceObject);

		if(((Class) targetFieldType
				.getActualTypeArguments()[0])==((Class) sourceFieldType
				.getActualTypeArguments()[0])){
			targetCollection.addAll(sourceCollection);
		}else {
			Iterator sourceIterator = sourceCollection.iterator();
			while (sourceIterator.hasNext()) {
				Object targetCollectionElementObject = ((Class) targetFieldType
						.getActualTypeArguments()[0]).newInstance();
				mapBeanDefault(targetCollectionElementObject, sourceIterator.next());
				targetCollection.add(targetCollectionElementObject);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("mapCollectionBeans(Object, Object, Field, Field) - end"); //$NON-NLS-1$
	}
	}

	/**
	 * @param targetObject
	 * @param sourceObject
	 * @param targetField
	 * @param sourceField
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 */
	private void mapArrayBeans(Object targetObject, Object sourceObject,
			Field targetField, Field sourceField)
			throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {
		if (logger.isDebugEnabled()) {
			logger.debug("mapArrayBeans(Object, Object, Field, Field) - start"); //$NON-NLS-1$
	}

		Object sourceArray = sourceField.get(sourceObject);
		Object targetArray = targetObject;
		int length = Array.getLength(sourceArray);
		for (int i = 0; i < length; i++) {
			Object sourceArrayElement = Array.get(sourceArray, i);
			if (sourceField.getType().getComponentType().isPrimitive()) {
				Array.set(targetArray, i, sourceArrayElement);
			} else if (sourceField.getType().getComponentType()
					.equals(String.class)) {
				
				Array.set(targetArray, i, new String(
						(String) sourceArrayElement));
			} else if (targetField.getType().getComponentType()
					.isAnnotationPresent(Mappable.class)
					|| sourceField.getType().getComponentType()
							.isAnnotationPresent(Mappable.class)) {
				Object targetArrayElement = MapperUtil
						.createTargetFieldInstance(targetField);

				mapBeanDefault(targetArrayElement, sourceArrayElement);
				Array.set(targetArray, i, targetArrayElement);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("mapArrayBeans(Object, Object, Field, Field) - Array Type not supported yet:" + sourceField.getType().getComponentType()); //$NON-NLS-1$
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mapArrayBeans(Object, Object, Field, Field) - end"); //$NON-NLS-1$
	}
	}

}
