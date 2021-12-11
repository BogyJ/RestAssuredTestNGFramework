package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.api.Route;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {

    @Step("Send POST payload with \"name\"={requestPlaylist.name} and \"description\"={requestPlaylist.description}")
    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(Route.USERS + "/" + ConfigLoader.getInstance().getUserId() + Route.PLAYLISTS, getToken(), requestPlaylist);
    }

    public static Response post(String token, Playlist requestPlaylist) {
        return RestResource.post(Route.USERS + "/" + ConfigLoader.getInstance().getUserId() + Route.PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String playlistId) {
        return RestResource.get(Route.PLAYLISTS + "/" + playlistId, getToken());
    }
    // playlistID = 4OSmoPDJuAzarQT4cfYBPv
    public static Response put(String playlistId, Playlist requestPlaylist) {
        return RestResource.put(Route.PLAYLISTS + "/" + playlistId, getToken(), requestPlaylist);
    }

}
