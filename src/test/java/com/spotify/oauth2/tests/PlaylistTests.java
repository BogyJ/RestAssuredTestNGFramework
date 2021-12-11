package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.ErrorRoot;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import com.spotify.oauth2.utils.FakerUtils;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Epic("Spotify OAuth2.0")
@Feature("Playlist APi")
public class PlaylistTests extends BaseTest {

    @Story("Create a playlist story")
    @Link("https://example.org")
    @Link(name = "allure", type = "link-example")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("This method will create request playlist object and send it to Spotify APi via POST method. Epected status code is 201 and the response body should be the same as request payload.")
    @Test(description = "Should be able to create a playlist") // this description is for display name -> title in allure report
    public void shouldBeAbleToCreatePlaylist() {
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generatePlaylistName(), FakerUtils.generateDescription(), false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);

        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToGetAPlaylistByPlaylistId() {
        Playlist requestPlaylist = playlistBuilder("Update Playlist 3", "New playlist 3 updated", false);

        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);

        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToUpdateAPlaylist() {
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generatePlaylistName(), FakerUtils.generateDescription(), false);

        Response response = PlaylistApi.put(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
    }

    @Story("Create a playlist story")
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName() {
        Playlist requestPlaylist = playlistBuilder("", FakerUtils.generateDescription(), false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400);

        assertError(response.as(ErrorRoot.class), StatusCode.CODE_400);
    }

    @Story("Create a playlist story")
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithExpiredToken() {
        Playlist requestPlaylist = playlistBuilder("New Playlist 8", "New playlist 8 desc", false);

        Response response = PlaylistApi.post("12345", requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401);

        assertError(response.as(ErrorRoot.class), StatusCode.CODE_401);
    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public) {
        return Playlist.builder().
                name(name).
                description(description).
                _public(_public).
                build();
    }

    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode) {
        assertThat(actualStatusCode, equalTo(statusCode.code));
    }

    @Step
    public void assertError(ErrorRoot responseErr, StatusCode statusCode) {
        assertThat(responseErr.getError().getStatus(), equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(), equalTo(statusCode.msg));
    }

}
