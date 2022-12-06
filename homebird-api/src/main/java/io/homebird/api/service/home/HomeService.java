package io.homebird.api.service.home;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * HomeService
 *
 * @author Anthony DePalma
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {

	// properties
	private final HomeMapper homeMapper;
	private final HomeRepository homeRepository;
	private final HomeRequestValidator validator;

	/**
	 * Returns a list of homes.
	 *
	 * @return Homes
	 */
	public List<Home> findHomesByUser(UUID userId) {
		return homeRepository.findByUserId(userId);
	}

	/**
	 * Creates a home from a request.
	 *
	 * @param request
	 * @return Home
	 */
	public Home createHome(HomeRequest request) {
		log.debug("Creating home [{}]", request.getName());

		// validate the request
		validator.validate(request);

		Home home = homeMapper.toHome(request);
		home = saveHome(home);
		return home;
	}

	/**
	 * Updates a home from a request.
	 *
	 * @param id
	 * @param request
	 * @return Home
	 */
	public Home updateHome(UUID id, HomeRequest request) {
		log.debug("Updating home [{}]", id);

		// validate the request
		validator.validate(request);

		Home home = homeRepository.getById(id);
		homeMapper.merge(request, home);
		home = saveHome(home);
		return home;
	}

	/**
	 * Deletes a home by id.
	 *
	 * @param id
	 */
	public void deleteHome(UUID id) {
		log.debug("Deleting home [{}]", id);
		homeRepository.deleteById(id);
	}

	/**
	 * Validates and saves a home.
	 *
	 * @param home
	 * @return Home
	 */
	private Home saveHome(Home home) {
		log.debug("Saving home [{}]", home.getId());
		return homeRepository.save(home);
	}

}