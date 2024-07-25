package com.thejackfolio.microservices.youtubeapi.servicehelpers;

import com.thejackfolio.microservices.youtubeapi.exceptions.ThumbnailUrlException;
import com.thejackfolio.microservices.youtubeapi.exceptions.VideoIdException;
import com.thejackfolio.microservices.youtubeapi.models.Item;
import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponse;
import com.thejackfolio.microservices.youtubeapi.utilities.StringConstants;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomingValidation {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingValidation.class);

    public void validateYouTubeResponse(YouTubeResponse youTubeResponse) throws VideoIdException, ThumbnailUrlException {
        if(youTubeResponse != null && youTubeResponse.getItems() != null && !youTubeResponse.getItems().isEmpty()){
            for(Item item : youTubeResponse.getItems()){
//                if(Strings.isEmpty(item.getId().getVideoId()) || Strings.isBlank(item.getId().getVideoId())){
//                    LOGGER.info("VideoId is null in response item:{}", item);
//                    throw new VideoIdException(StringConstants.VIDEO_ID_NULL);
//                }
                if(Strings.isEmpty(item.getSnippet().getThumbnails().getMedium().getUrl()) || Strings.isBlank(item.getSnippet().getThumbnails().getMedium().getUrl())){
                    LOGGER.info("Thumbnail URL is null in response item:{}", item);
                    throw new ThumbnailUrlException(StringConstants.THUMBNAIL_URL_NULL);
                }
            }
        }
    }
}
