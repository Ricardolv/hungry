package com.richard.infrastructure.resources;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.richard.RegisterTestLifecycleManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager.class)
public class RestaurantResourceTest {

    @Test
    @DataSet(value = "dataset/restaurants.yml")
    public void findAll() {
        var result = given()
                .when().get("/restaurants")
                .then()
                .statusCode(200)
                .extract().asString();

        System.out.println("result: " + result);
        //Approvals.verify(result);

    }

}