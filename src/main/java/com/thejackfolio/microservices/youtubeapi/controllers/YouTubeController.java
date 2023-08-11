package com.thejackfolio.microservices.youtubeapi.controllers;

import com.thejackfolio.microservices.youtubeapi.exceptions.ResponseException;
import com.thejackfolio.microservices.youtubeapi.exceptions.ThumbnailUrlException;
import com.thejackfolio.microservices.youtubeapi.exceptions.VideoIdException;
import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponse;
import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponseWrapper;
import com.thejackfolio.microservices.youtubeapi.services.YouTubeService;
import com.thejackfolio.microservices.youtubeapi.utilities.StringConstants;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/youtube")
public class YouTubeController {

    @Autowired
    private YouTubeService service;

    @Operation(
            summary = "Get YouTube Videos",
            description = "Gets top 5 YouTube Video according to date from my channel",
            tags = { "videos", "get" }
    )
    @GetMapping("/get-videos")
    public ResponseEntity<YouTubeResponseWrapper> getVideoDetails(){
        YouTubeResponseWrapper wrapper = null;
        try{
            YouTubeResponse youTubeResponse = service.getVideoDetails();
            wrapper = new YouTubeResponseWrapper();
            wrapper.setYouTubeResponse(youTubeResponse);
            wrapper.setMesssage(StringConstants.REQUEST_PROCESSED);
        } catch (ResponseException exception){
            wrapper = new YouTubeResponseWrapper();
            wrapper.setMesssage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(wrapper);
        } catch(VideoIdException | ThumbnailUrlException exception){
            wrapper = new YouTubeResponseWrapper();
            wrapper.setMesssage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wrapper);
        }
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }
}
