package com.richard.domain;


import com.richard.infrastructure.persistence.entities.RestaurantEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RestaurantService {
    public void save(RestaurantEntity entity) {
        entity.persist();
    }

    public List<RestaurantEntity> listAll() {
        return RestaurantEntity.listAll();
    }
}
