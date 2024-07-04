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

public class DeleteRegistrationTests {


    public static final String BASE_URI = "http://localhost:8080";
    public static final String BASE_PATH = "api/v1/registration";

    @Test()
    public void verifyDeleteRegistrationEntry() throws IOException {
        String registrationId = "11";
        RestAssured.given().baseUri(BASE_URI).basePath(BASE_PATH).queryParam("id", registrationId).log().all().when().delete().then().log().all().assertThat().statusCode(200);
    }
}
