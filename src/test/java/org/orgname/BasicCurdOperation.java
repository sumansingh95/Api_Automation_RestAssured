package org.orgname;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class BasicCurdOperation {

    @Test(priority = 1)
    public void verifyGetCall() {
        RestAssured.given().baseUri("https://reqres.in").basePath("api/users?page=2").log().all()
                .when().get()
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test(priority = -1)
    public void verifyPostCall() {
        RestAssured.given().baseUri("https://reqres.in").basePath("api/users").body("{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}").log().all().when().post().then().log().all().assertThat().statusCode(201);
    }

    @Test
    public void verifyPutcall() {
        RestAssured.given().baseUri("https://reqres.in").basePath("api/users/2").body("{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}").log().all().when().put().then().log().all().assertThat().statusCode(200);

    }

    @Test
    public void verifyDeleteApi() {
        RestAssured.given().baseUri("https://reqres.in").basePath("api/users/2")
                .log().all().when().delete().then().
                log().all().assertThat().
                statusCode(204);

    }

    @Test
    public void verifyUpdate() {
        RestAssured.given().baseUri("https://reqres.in").basePath("api/users/2").body("{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}").log().all().when().patch().then().log().all().assertThat().statusCode(200);

    }
}