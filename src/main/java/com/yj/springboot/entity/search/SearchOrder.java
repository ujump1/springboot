package com.yj.springboot.entity.search;

import java.io.Serializable;
import java.util.Locale;

public class SearchOrder implements Serializable {

	private String property;
	private Direction direction;

	public SearchOrder() {

	}

	public SearchOrder(String property) {
		this.property = property;
		this.direction = Direction.ASC;
	}

	public SearchOrder(String property, Direction direction) {
		this.property = property;
		this.direction = direction;
	}

	public static SearchOrder asc(String property) {
		return new SearchOrder(property, Direction.ASC);
	}

	public static SearchOrder desc(String property) {
		return new SearchOrder(property, Direction.DESC);
	}

	public String getProperty() {
		return property;
	}

	public Direction getDirection() {
		return direction;
	}

	/**
	 * Enumeration for sort directions.
	 *
	 * @author Oliver Gierke
	 */
	public enum Direction {

		ASC, DESC;

		/**
		 * Returns the {@link Direction} enum for the given {@link String} value.
		 *
		 * @param value
		 * @return
		 * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value.
		 */
		public static Direction fromString(String value) {
			try {
				return Direction.valueOf(value.toUpperCase(Locale.US));
			} catch (Exception e) {
				throw new IllegalArgumentException(String.format(
						"Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
			}
		}
	}
}
