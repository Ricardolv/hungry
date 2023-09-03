package com.richard.infrastructure.resources;

import com.richard.infrastructure.persistence.entities.DisheEntity;
import com.richard.infrastructure.persistence.entities.RestaurantEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurante")
//@RolesAllowed("propietario")
public class RestaurantResource {

    @GET
    public List<RestaurantEntity> findAll() {
        return RestaurantEntity.listAll();
    }

    @POST
    @Transactional
    public Response create(RestaurantEntity dto) {
        dto.persist();
        return Response
                .status(Response.Status.CREATED)
                .build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void uodate(@PathParam("id") Long id, RestaurantEntity dto) {
        Optional<RestaurantEntity> restaurantOp = RestaurantEntity.findByIdOptional(id);
        if (restaurantOp.isEmpty()){
            throw new NotFoundException();
        }
        RestaurantEntity restaurante = restaurantOp.get();
        restaurante.name = dto.name;
        restaurante.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Optional<RestaurantEntity> restaurantOp = RestaurantEntity.findByIdOptional(id);

        restaurantOp.ifPresentOrElse(RestaurantEntity::delete, () -> {
            throw new NotFoundException();
        });
    }

    // pratos
    @GET
    @Path("/{id}/dishes")
    @Tag(name = "dishe")
    public List<DisheEntity> findDishe(@PathParam("id") Long id) {
        Optional<RestaurantEntity> restauranteOp = RestaurantEntity.findByIdOptional(id);
        if (restauranteOp.isEmpty()){
            throw new NotFoundException("Restaurante não existe");
        }
        return DisheEntity.list("restaurante", restauranteOp.get());
    }

    @POST
    @Path("/{id}/dishes")
    @Transactional
    @Tag(name = "dishe")
    public Response createDishe(@PathParam("id") long id, DisheEntity dto) {

        Optional<RestaurantEntity> restauranteOp = RestaurantEntity.findByIdOptional(id);
        if (restauranteOp.isEmpty()){
            throw new NotFoundException("Restaurante não existe");
        }

        // utilizando o dto, recebi detached entity passed to persist
        DisheEntity dishe = new DisheEntity();
        dishe.name = dto.name;
        dishe.description = dto.description;

        dishe.price = dto.price;
        dishe.restaurant = restauranteOp.get();

        dishe.persist();

        return Response
                .status(Response.Status.CREATED)
                .build();
    }

    @DELETE
    @Path("/{id}/dishes/{idDishe}")
    @Transactional
    @Tag(name = "dishe")
    public void deleteDishe(@PathParam("id") long id,
                            @PathParam("idDishe") Long idDishe) {

        Optional<RestaurantEntity> restauranteOp = RestaurantEntity.findByIdOptional(id);
        if (restauranteOp.isEmpty()){
            throw new NotFoundException("Restaurante não existe");
        }

        Optional<DisheEntity> disheOp = DisheEntity.findByIdOptional(id);

        disheOp.ifPresentOrElse(DisheEntity::delete, () -> {
            throw new NotFoundException();
        });
    }

    @PUT
    @Path("/{id}/dishes/{idDishe}")
    @Transactional
    @Tag(name = "dishe")
    public void updateDishe(@PathParam("id") long id,
                            @PathParam("idDishe") Long idDishe,
                            DisheEntity dto) {

        Optional<RestaurantEntity> restauranteOp = RestaurantEntity.findByIdOptional(id);
        if (restauranteOp.isEmpty()){
            throw new NotFoundException("Restaurante não existe");
        }

        Optional<DisheEntity> disheOp = DisheEntity.findByIdOptional(idDishe);
        if (disheOp.isEmpty()) {
            throw new NotFoundException("Prato não existe");
        }
        DisheEntity dishe = disheOp.get();
        dishe.price = dto.price;
        dishe.persist();
    }

}
