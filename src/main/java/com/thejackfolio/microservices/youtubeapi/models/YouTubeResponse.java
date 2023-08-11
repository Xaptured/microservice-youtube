package com.thejackfolio.microservices.youtubeapi.models;

import java.util.List;

public class YouTubeResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
