package com.thejackfolio.microservices.youtubeapi.servicehelpers;

import com.thejackfolio.microservices.youtubeapi.client.TheJackFolioDBClient;
import com.thejackfolio.microservices.youtubeapi.exceptions.DataBaseOperationException;
import com.thejackfolio.microservices.youtubeapi.exceptions.MapperException;
import com.thejackfolio.microservices.youtubeapi.exceptions.ResponseException;
import com.thejackfolio.microservices.youtubeapi.models.InterestedGame;
import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponse;
import com.thejackfolio.microservices.youtubeapi.utilities.PropertiesReader;
import com.thejackfolio.microservices.youtubeapi.utilities.StringConstants;
import com.thejackfolio.microservices.youtubeapi.client.YouTubeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YouTubeServiceHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeServiceHelper.class);

    @Autowired
    private YouTubeClient client;
    @Autowired
    private TheJackFolioDBClient dbClient;

    public YouTubeResponse getVideoDetails() throws ResponseException {
        YouTubeResponse youTubeResponse = null;
        try{
            youTubeResponse = client.getVideoDetails(PropertiesReader.getProperty(StringConstants.PART),
                                                    PropertiesReader.getProperty(StringConstants.FIELDS),
                                                    PropertiesReader.getProperty(StringConstants.CHANNEL_ID),
                                                    Integer.valueOf(PropertiesReader.getProperty(StringConstants.MAX_RESULTS)),
                                                    PropertiesReader.getProperty(StringConstants.ORDER),
                                                    PropertiesReader.getProperty(StringConstants.KEY));
        } catch (Exception exception){
            LOGGER.info(StringConstants.BAD_REQUEST_YOUTUBE, exception);
            throw new ResponseException(StringConstants.BAD_REQUEST_YOUTUBE, exception);
        }
        return youTubeResponse;
    }

    public List<InterestedGame> findInterestedGamesForUser(String email) throws MapperException, DataBaseOperationException {
        List<InterestedGame> interestedGames = null;
        ResponseEntity<List<InterestedGame>> response = dbClient.findAllInterestedGamesForUser(email);
        if(response.getStatusCode().is2xxSuccessful()) {
            interestedGames = response.getBody();
        } else {
            interestedGames = response.getBody();
            if(interestedGames.get(0).getMessage().equals(StringConstants.DATABASE_ERROR)) {
                throw new DataBaseOperationException(interestedGames.get(0).getMessage());
            } else if(interestedGames.get(0).getMessage().equals(StringConstants.MAPPING_ERROR)) {
                throw new MapperException(interestedGames.get(0).getMessage());
            }

        }
        return interestedGames;
    }

    public String prepareRequestParamForInterestedGames(List<InterestedGame> interestedGames) {
        String intGamesReqParams = null;
        if(interestedGames != null) {
            for(InterestedGame game : interestedGames) {
                String gameName = game.getGameName();
                if(intGamesReqParams == null) {
                    intGamesReqParams = gameName + "news";
                } else {
                    intGamesReqParams = interestedGames + " %7C " + gameName + "news";
                }
            }
        } else {
            intGamesReqParams = "gaming news";
        }
        return intGamesReqParams;
    }

    public YouTubeResponse getNewsVideoDetails(String interestedGamesReqParam) throws ResponseException {
        YouTubeResponse youTubeResponse = null;
        try{
            youTubeResponse = client.getNewsVideoDetails(PropertiesReader.getProperty(StringConstants.PART),
                    PropertiesReader.getProperty(StringConstants.FIELDS),
                    Integer.valueOf(PropertiesReader.getProperty(StringConstants.MAX_RESULTS)),
                    "relevance",
                    interestedGamesReqParam,
                    "video",
                    PropertiesReader.getProperty(StringConstants.KEY));
        } catch (Exception exception){
            LOGGER.info(StringConstants.BAD_REQUEST_YOUTUBE, exception);
            throw new ResponseException(StringConstants.BAD_REQUEST_YOUTUBE, exception);
        }
        return youTubeResponse;
    }
}
