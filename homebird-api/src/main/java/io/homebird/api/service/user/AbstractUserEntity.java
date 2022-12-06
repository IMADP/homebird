package io.homebird.api.service.user;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.homebird.api.service.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AbstractUserEntity
 *
 * An extention of the AbstractEntity class which provides a User reference.
 *
 * @author Anthony DePalma
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractUserEntity extends AbstractEntity {

	@NotNull
	@JsonIgnore
	@ManyToOne(optional = false)
	private User user;

}