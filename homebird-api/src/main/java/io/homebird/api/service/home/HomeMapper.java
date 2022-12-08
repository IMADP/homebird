package io.homebird.api.service.home;

import java.util.List;
import java.util.UUID;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import io.homebird.api.service.IgnoreEntityMetadata;
import io.homebird.api.service.home.HomeMapper.HomeMapperDecorator;
import io.homebird.api.service.user.User;
import io.homebird.api.service.user.UserService;


/**
 * HomeMapper
 *
 * @author Anthony DePalma
 */
@Mapper(componentModel = "spring")
@DecoratedWith(HomeMapperDecorator.class)
public interface HomeMapper {

	/**
	 * Converts a home to a response.
	 *
	 * @param home
	 * @return UserTokenResponse
	 */
	public HomeResponse toResponse(Home home);

	/**
	 * Converts a list of homes to responses.
	 *
	 * @param homes
	 * @return List<UserTokenResponse>
	 */
	public List<HomeResponse> toResponses(List<Home> homes);

	/**
	 * Clones a home.
	 *
	 * @param home
	 * @return Home
	 */
	public Home clone(Home home);

	/**
	 * Converts a request to a home.
	 *
	 * @param request
	 * @return Home
	 */
	@IgnoreEntityMetadata
	@Mapping(target = "user", ignore = true)
	public Home toHome(HomeRequest request);

	/**
	 * Merges a request into a home.
	 *
	 * @param request
	 * @param home
	 */
	@IgnoreEntityMetadata
	@Mapping(target = "user", ignore = true)
	public void merge(HomeRequest request, @MappingTarget Home home);

	/**
	 * HomeMapperDecorator
	 *
	 * @author Anthony DePalma
	 */
	@Mapper(componentModel = "spring")
	public static abstract class HomeMapperDecorator implements HomeMapper {

		@Autowired
		@Qualifier("delegate")
		private HomeMapper delegate;

		@Autowired
		private UserService userService;

		@Override
		public Home toHome(HomeRequest request) {
			Home home = delegate.toHome(request);
			setUser(request.getUserId(), home);
			return home;
		}

		@Override
		public void merge(HomeRequest request, Home home) {
			delegate.merge(request, home);
			setUser(request.getUserId(), home);
		}

		/**
		 * Sets the template into the lab.
		 *
		 * @param request
		 * @param lab
		 */
		private void setUser(UUID userId, Home home) {
			User user = userService.getById(userId);
			home.setUser(user);
		}

	}

}