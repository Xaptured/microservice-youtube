/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.thejackfolio.microservices.youtubeapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InterestedGame {

    private String email;
    private String gameName;
    private Integer gameNumber;
    private String message;

    public InterestedGame() {
    }

    public InterestedGame(String email, String gameName, Integer gameNumber, String message) {
        this.email = email;
        this.gameName = gameName;
        this.gameNumber = gameNumber;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(Integer gameNumber) {
        this.gameNumber = gameNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestedGame that = (InterestedGame) o;
        return Objects.equals(email, that.email) && Objects.equals(gameName, that.gameName) && Objects.equals(gameNumber, that.gameNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, gameName, gameNumber);
    }

    @Override
    public String toString() {
        return "InterestedGame{" +
                "email='" + email + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gameNumber=" + gameNumber +
                '}';
    }
}
