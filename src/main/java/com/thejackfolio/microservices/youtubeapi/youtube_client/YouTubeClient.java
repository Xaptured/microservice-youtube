package com.thejackfolio.microservices.youtubeapi.youtube_client;

import com.thejackfolio.microservices.youtubeapi.models.YouTubeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "get-youtube-video-details", url = "https://youtube.googleapis.com/youtube")
public interface YouTubeClient {

    @GetMapping(value = "/v3/search")
    YouTubeResponse getVideoDetails(@RequestParam(value = "part") String part,
                                    @RequestParam(value = "fields") String fields,
                                    @RequestParam(value = "channelId") String channelId,
                                    @RequestParam(value = "maxResults") Integer maxResults,
                                    @RequestParam(value = "order") String date,
                                    @RequestParam(value = "key") String key) throws Exception;
}
