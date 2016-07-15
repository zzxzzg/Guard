/*
 * 
 */
package org.omapper.mapper;

import java.lang.reflect.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class MapEntry.
 */
class MapEntry {
	/** The source field. */
	private final Field sourceField;
	
	/** The target field. */
	private final Field targetField;

	/**
	 * Instantiates a new map entry.
	 *
	 * @param sourceField the source field
	 * @param targetField the target field
	 */
	public MapEntry(Field sourceField, Field targetField) {
		this.sourceField = sourceField;
		this.targetField = targetField;
	}

	/**
	 * Gets the source field.
	 *
	 * @return the source field
	 */
	public Field getSourceField() {
		return sourceField;
	}

	/**
	 * Gets the target field.
	 *
	 * @return the target field
	 */
	public Field getTargetField() {
		return targetField;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MapEntry [sourceField=");
		builder.append(sourceField);
		builder.append(", targetField=");
		builder.append(targetField);
		builder.append("]");
		return builder.toString();
	}

}
