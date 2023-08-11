package com.thejackfolio.microservices.youtubeapi.services;

import com.thejackfolio.microservices.youtubeapi.exceptions.ResponseException;
import com.thejackfolio.microservices.youtubeapi.exceptions.ThumbnailUrlException;
import com.thejackfolio.microservices.youtubeapi.exceptions.VideoIdException;
import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponse;
import com.thejackfolio.microservices.youtubeapi.servicehelpers.IncomingValidation;
import com.thejackfolio.microservices.youtubeapi.servicehelpers.YouTubeServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YouTubeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeService.class);

    @Autowired
    private IncomingValidation validation;
    @Autowired
    private YouTubeServiceHelper helper;

    public YouTubeResponse getVideoDetails() throws ResponseException, VideoIdException, ThumbnailUrlException {
        YouTubeResponse youTubeResponse = helper.getVideoDetails();
        validation.validateYouTubeResponse(youTubeResponse);
        return youTubeResponse;
    }
}
