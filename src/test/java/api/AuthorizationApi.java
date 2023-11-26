package api;

import models.Cookie;
import models.Credentials;
import models.Token;
import models.UserNew;

import static specs.RequestSpecs.jsonRequestSpec;
import static specs.RequestSpecs.requestSpec;
import static specs.ResponseSpecs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyOrNullString;

public class AuthorizationApi {

    public UserNew createUser(Credentials credentials) {
        return given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract().as(UserNew.class);
    }

    public Token getToken(Credentials credentials) {
        return given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(Token.class);
    }

    public Cookie login(Credentials credentials) {
        return given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(Cookie.class);
    }

    public void deleteUser(UserNew user, Token token) {
        given(requestSpec)
                .header("Authorization", "Bearer " + token.getToken())
                .when()
                .delete("/Account/v1/User/" + user.getUserID())
                .then()
                .spec(responseSpec)
                .statusCode(204)
                .body(is(emptyOrNullString()));
    }
}
