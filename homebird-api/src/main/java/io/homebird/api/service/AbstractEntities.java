package io.homebird.api.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * AbstractEntities
 *
 * Base class for collections of entities.
 *
 * @author Anthony DePalma
 */
public abstract class AbstractEntities<V extends AbstractEntity> implements Iterable<V> {

	// the list of all entities
	private final List<V> all;

	// the map of id to user
	private final Map<UUID, V> idMap;

	// constructor
	public AbstractEntities(List<V> entities) {
		this.all = entities;
		this.idMap = entities.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
	}

	/**
	 * Finds an entity by id.
	 *
	 * @param id
	 * @return Optional<V>
	 */
	public Optional<V> findById(UUID id) {
		return Optional.ofNullable(idMap.get(id));
	}

	/**
	 * Returns the count of all users.
	 *
	 * @return long
	 */
	public long getCount() {
		return all.size();
	}

	/**
	 * Returns all entities.
	 *
	 * @return List<V>
	 */
	public List<V> getAll() {
		return all;
	}

	@Override
	public Iterator<V> iterator() {
		return all.iterator();
	}

}