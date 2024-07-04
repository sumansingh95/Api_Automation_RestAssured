package org.orgname;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lib.FileReader;
import lib.RandomDataGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class CreateRegistrationTests {

    private String regestrationBaseUri = "";
    private String regestrationBasePath = "";
    private String payload = "";
    private DocumentContext requestBody = null;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {
        payload = FileReader.readFile("src/test/resources/requestPayload/createRegistrationPayload.json");
        Map<String,String> properties = FileReader.getPrperties("src/test/resources/qa-env.properties");
        regestrationBaseUri = properties.get("regestrationBaseURI");
        regestrationBasePath = properties.get("regestrationbBasePath");
        requestBody = JsonPath.using(Configuration.defaultConfiguration()).parse(payload);
    }

    @Test()
    public void verifyCreateReg() throws IOException {
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(201);
    }

    @Test()
    public void verifyCreateRegSuccessfully() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        String response = RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(201).extract().response().asString();
        DocumentContext responseBody = JsonPath.using(Configuration.defaultConfiguration()).parse(response);
        int id = responseBody.read("$.id");
        String message = responseBody.read("$.message");
        Assert.assertEquals(message, "Registration saved");
        Assert.assertNotNull(id);
    }

    @Test()
    public void verifyCreateRegWithNameFieldNullValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        requestBody.set("$.name", null);
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

    @Test()
    public void verifyCreateRegWithEmailFieldNullValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        requestBody.set("$.email", null);
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

    @Test()
    public void verifyCreateRegWithPhoneNumberFieldNullValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        requestBody.set("$.phone", null);
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }


    @Test()
    public void verifyCreateRegWithoutNameField() throws IOException {
        requestBody.delete("$.name");
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

    @Test()
    public void verifyCreateRegWithoutEmailField() throws IOException {
        requestBody.delete("$.email");
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

    @Test()
    public void verifyCreateRegWithRandomMobileNumber() throws IOException {
        String randomMobile = RandomDataGenerator.generateIntNumber(10);
        requestBody.set("$.mobile", randomMobile);
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(201);
    }

    @Test()
    public void verifyRegWithExtraFieldPayload() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        String cityName = RandomDataGenerator.generateAlphabatic(8);
        requestBody.put("$", "city", cityName);
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBaseUri).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(500);
    }


    @Test()
    public void verifyCreateRegWithBlankNameFieldValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        requestBody.set("$.name", "");
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

    @Test()
    public void verifyCreateRegWithBlankEmailFieldValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        requestBody.set("$.phone", "");
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

    @Test()
    public void verifyCreateRegWithPhoneBlankFieldValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        requestBody.set("$.phone", "");
        RestAssured.given().baseUri(regestrationBasePath).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

    @Test()
    public void verifyCreateReghNameFieldWithSpcalCaracterValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        requestBody.set("$.name", "2@#5hhh");
        RestAssured.given().baseUri(regestrationBasePath).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

    @Test()
    public void verifyCreateRegEmailFieldWithSpacalCharacterValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        requestBody.set("$.email", "@rtyyyiiinbb677@gmail.com");
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

    @Test()
    public void verifyCreateRegNameFieldWithSPacalCharcterValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        String randomName = RandomDataGenerator.generateAlphabatic(10) + "#$%^&";
        requestBody.set("$.name", randomName);
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }


    @Test()
    public void verifyCreateRegWithMoreThan10DigtNumberFieldValue() throws IOException {
        String randomEmail = RandomDataGenerator.generateAlphabatic(10) + "@gmail.com";
        requestBody.set("$.email", randomEmail);
        String randomMobile = RandomDataGenerator.generateIntNumber(11);
        requestBody.set("$.mobile", randomMobile);
        RestAssured.given().baseUri(regestrationBaseUri).basePath(regestrationBasePath).body(requestBody.jsonString()).contentType(ContentType.JSON).log().all().when().post().then().log().all().assertThat().statusCode(400);
    }

}
