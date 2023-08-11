package com.thejackfolio.microservices.youtubeapi.servicehelpers;

import com.thejackfolio.microservices.youtubeapi.exceptions.ResponseException;
import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponse;
import com.thejackfolio.microservices.youtubeapi.utilities.PropertiesReader;
import com.thejackfolio.microservices.youtubeapi.utilities.StringConstants;
import com.thejackfolio.microservices.youtubeapi.youtube_client.YouTubeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YouTubeServiceHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeServiceHelper.class);

    @Autowired
    private YouTubeClient client;

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
            LOGGER.info("Bad request to YouTube API", exception);
            throw new ResponseException("Bad request to YouTube API", exception);
        }
        return youTubeResponse;
    }
}
