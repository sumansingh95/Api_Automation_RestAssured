package org.orgname;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lib.FileReader;
import lib.RandomDataGenerator;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateRegistration {

    @Test()
    public void verifyUpdateRegWithNameFieldValue() throws IOException {
        String regPayload = FileReader.readFile("src/test/resources/requestPayload/createRegistrationPayload.json");
        DocumentContext requestBody = JsonPath.using(Configuration.defaultConfiguration()).parse(regPayload);
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        String registrationId = "6";
        RestAssured.given().baseUri("http://localhost:8080").basePath("api/v1/registration").queryParam("id", registrationId).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().put().then().log().all().assertThat().statusCode(200);
    }

}
