package com.spotify.oauth2.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {

    // user id = 2sq0zv6apo7dnbmwjfekmwg3w
    public static Response post(String path, String token, Object requestPlaylist) {
        return  given()
                        .spec(getRequestSpec()).
                        body(requestPlaylist).
                        auth().
                        oauth2(token).
                when()
                    .post(path).// "/v1/users/2sq0zv6apo7dnbmwjfekmwg3w/playlists"
                then()
                        .spec(getResponseSpec()).
                        extract().
                        response();
    }

    public static Response postAccount(HashMap<String, String> formParams) {
        return  given()
                        .spec(getAccountRequestSpec()).
                        baseUri("https://accounts.spotify.com").
                        contentType(ContentType.URLENC).
                        formParams(formParams).
                when()
                        .post(Route.API + Route.TOKEN).
                then()
                        .spec(getResponseSpec()).
                        extract().
                        response();
    }

    public static Response get(String path, String token) {
        return given()
                        .spec(getRequestSpec()).
                        auth().
                        oauth2(token).
                when()
                        .get(path).// "/v1/playlists/" + playlistId
                then()
                        .spec(getResponseSpec()).
                        extract().
                        response();
    }
    // playlistID = 4OSmoPDJuAzarQT4cfYBPv
    public static Response put(String path, String token, Object requestPlaylist) {
        return given()
                .spec(getRequestSpec()).
                body(requestPlaylist).
                auth().
                oauth2(token).
        when()
                .put(path). // "/v1/playlists/" + playlistId
        then()
                .spec(getResponseSpec()).
                extract().
                response();
    }

}
