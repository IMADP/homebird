package io.homebird.api.service.home;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQuery;

import io.homebird.api.service.EntityRepository;

/**
 * HomeRepository
 *
 * @author Anthony DePalma
 */
@Repository
public interface HomeRepository extends EntityRepository<Home> {

	// logger
	final Logger log = LoggerFactory.getLogger(HomeRepository.class);

	/**
	 * Finds homes by userId.
	 *
	 * @param userId
	 * @return List<Home>
	 */
	public default List<Home> findByUserId(UUID userId) {
		log.debug("Finding homes by userId [{}]", userId);

		return select()
				.where(QHome.home.user.id.eq(userId))
				.orderBy(QHome.home.name.asc()).fetch();
	}

	/**
	 * Finds a home by userId and name.
	 *
	 * @param userId
	 * @param name
	 * @return Optional<Home>
	 */
	public default Optional<Home> findByName(UUID userId, String name) {
		log.debug("Finding home by user [{}] and name [{}]", userId, name);

		JPQLQuery<Home> query = select()
				.where(QHome.home.user.id.eq(userId)
						.and(QHome.home.name.eq(name)));

		return Optional.ofNullable(query.fetchFirst());
	}

}