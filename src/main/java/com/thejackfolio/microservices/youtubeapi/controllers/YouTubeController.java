package com.thejackfolio.microservices.youtubeapi.controllers;

import com.thejackfolio.microservices.youtubeapi.exceptions.*;
import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponse;
import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponseWrapper;
import com.thejackfolio.microservices.youtubeapi.services.YouTubeService;
import com.thejackfolio.microservices.youtubeapi.utilities.StringConstants;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/youtube")
public class YouTubeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeController.class);
    private boolean isRetryEnabled = false;
    @Autowired
    private YouTubeService service;

    @Operation(
            summary = "Get YouTube Videos",
            description = "Gets top 5 YouTube Video according to date from my channel"
    )
    @GetMapping("/get-videos")
    @Retry(name = "get-videos-retry", fallbackMethod = "getVideosRetry")
    public ResponseEntity<YouTubeResponseWrapper> getVideoDetails(){
        YouTubeResponseWrapper wrapper = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
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
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }

    public ResponseEntity<YouTubeResponseWrapper> getVideosRetry(Exception exception){
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        YouTubeResponseWrapper wrapper = new YouTubeResponseWrapper();
        wrapper.setMesssage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }

    @Operation(
            summary = "Get YouTube Videos",
            description = "Gets top 5 YouTube Video according to date from my channel"
    )
    @GetMapping("/get-news-videos/{email}")
    @Retry(name = "get-news-videos-retry", fallbackMethod = "getNewsVideoDetailsRetry")
    public ResponseEntity<YouTubeResponseWrapper> getNewsVideoDetails(@PathVariable String email){
        YouTubeResponseWrapper wrapper = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            YouTubeResponse youTubeResponse = service.getNewsVideoDetails(email);
            wrapper = new YouTubeResponseWrapper();
            wrapper.setYouTubeResponse(youTubeResponse);
            wrapper.setMesssage(StringConstants.REQUEST_PROCESSED);
        } catch (ResponseException exception){
            wrapper = new YouTubeResponseWrapper();
            wrapper.setMesssage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(wrapper);
        } catch(VideoIdException | ThumbnailUrlException | DataBaseOperationException | MapperException exception){
            wrapper = new YouTubeResponseWrapper();
            wrapper.setMesssage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wrapper);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }

    public ResponseEntity<YouTubeResponseWrapper> getNewsVideoDetailsRetry(Exception exception){
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        YouTubeResponseWrapper wrapper = new YouTubeResponseWrapper();
        wrapper.setMesssage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }
}
