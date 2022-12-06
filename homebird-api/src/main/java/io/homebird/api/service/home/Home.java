package io.homebird.api.service.home;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import io.homebird.api.service.user.AbstractUserEntity;
import lombok.Getter;
import lombok.Setter;


/**
 * Home
 *
 * @author Anthony DePalma
 */
@Getter
@Setter
@Entity
public class Home extends AbstractUserEntity {

	@NotNull
	private String name;

}
