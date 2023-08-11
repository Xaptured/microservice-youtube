package com.thejackfolio.microservices.youtubeapi.models;

public class YouTubeResponseWrapper {

    private YouTubeResponse youTubeResponse;
    private String messsage;

    public YouTubeResponse getYouTubeResponse() {
        return youTubeResponse;
    }

    public void setYouTubeResponse(YouTubeResponse youTubeResponse) {
        this.youTubeResponse = youTubeResponse;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
