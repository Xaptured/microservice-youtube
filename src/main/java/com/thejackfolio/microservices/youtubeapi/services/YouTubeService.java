package com.thejackfolio.microservices.youtubeapi.services;

import com.thejackfolio.microservices.youtubeapi.exceptions.*;
import com.thejackfolio.microservices.youtubeapi.models.InterestedGame;
import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponse;
import com.thejackfolio.microservices.youtubeapi.servicehelpers.IncomingValidation;
import com.thejackfolio.microservices.youtubeapi.servicehelpers.YouTubeServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YouTubeService {

    @Autowired
    private IncomingValidation validation;
    @Autowired
    private YouTubeServiceHelper helper;

    public YouTubeResponse getVideoDetails() throws ResponseException, VideoIdException, ThumbnailUrlException {
        YouTubeResponse youTubeResponse = helper.getVideoDetails();
        validation.validateYouTubeResponse(youTubeResponse);
        return youTubeResponse;
    }

    public YouTubeResponse getNewsVideoDetails(String email) throws MapperException, DataBaseOperationException, ResponseException, VideoIdException, ThumbnailUrlException {
        List<InterestedGame> interestedGames = helper.findInterestedGamesForUser(email);
        String interestedGamesReqParam = helper.prepareRequestParamForInterestedGames(interestedGames);
        YouTubeResponse response = helper.getNewsVideoDetails(interestedGamesReqParam);
        validation.validateYouTubeResponse(response);
        return response;
    }
}
