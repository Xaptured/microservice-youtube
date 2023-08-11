package com.thejackfolio.microservices.youtubeapi.models;

public class Item {

    private Id id;
    private Snippet snippet;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
}
