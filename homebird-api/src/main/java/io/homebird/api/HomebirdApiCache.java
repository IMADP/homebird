package io.homebird.api;

import lombok.Getter;

/**
 * HomebirdApiCache
 *
 * Enumerated values of cache names.
 *
 * @author Anthony DePalma
 */
@Getter
public enum HomebirdApiCache {
	HOMES(Name.HOMES);

	// the name of the cache
	private final String name;

	// constructor
	private HomebirdApiCache(String name) {
		if(!name.equals(this.name())) {
			throw new IllegalArgumentException();
		}

		this.name = name;
	}

	/**
	 * Name
	 *
	 * Nested constants class to allow access from annotations.
	 *
	 * @author Anthony DePalma
	 */
	public static class Name {
		public static final String HOMES = "HOMES";
	}

}