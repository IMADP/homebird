package io.homebird.api.service.home;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.homebird.api.service.auth.AuthClaim;
import lombok.RequiredArgsConstructor;

/**
 * HomeController
 *
 * @author Anthony DePalma
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/home")
public class HomeController {

	// properties
	private final HomeMapper homeMapper;
	private final HomeService homeService;

	@GetMapping
	public List<HomeResponse> getHomes(AuthClaim authClaim) {
		List<Home> homes = homeService.findHomesByUser(authClaim.getUserId());
		return homeMapper.toResponses(homes);
	}

	@PostMapping
	public HomeResponse createHome(AuthClaim authClaim, @RequestBody HomeRequest request) {
		Home home = homeService.createHome(request);
		return homeMapper.toResponse(home);
	}

	@PutMapping("/{id}")
	public HomeResponse updateHome(@PathVariable UUID id, @RequestBody HomeRequest request) {
		Home home = homeService.updateHome(id, request);
		return homeMapper.toResponse(home);
	}

	@DeleteMapping("/{id}")
	public void deleteHome(@PathVariable UUID id) {
		homeService.deleteHome(id);
	}

}