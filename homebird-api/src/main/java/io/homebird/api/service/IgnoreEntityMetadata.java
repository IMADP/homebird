package io.homebird.api.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.mapstruct.Mapping;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(target = "version", ignore = true)
@Mapping(target = "timeCreated", ignore = true)
@Mapping(target = "timeModified", ignore = true)
public @interface IgnoreEntityMetadata {

}
