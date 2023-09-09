package com.richard.infrastructure.resources.mapper;

import com.richard.infrastructure.persistence.entities.RestaurantEntity;
import com.richard.infrastructure.resources.dto.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestauratMapper {

    RestauratMapper INSTANCE = Mappers.getMapper( RestauratMapper.class );

    Restaurant toRestaurant(RestaurantEntity entity);
    RestaurantEntity toRestaurantEntity(Restaurant request);
}
