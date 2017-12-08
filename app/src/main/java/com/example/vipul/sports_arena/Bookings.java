package com.example.vipul.sports_arena;

/**
 * Created by vipul on 5/12/17.
 */

public class Bookings {

    String arenaId,arenaName,dateBooking,timeBooking,numPlayers,kitStatus;

    public Bookings(String arenaId, String arenaName, String dateBooking, String timeBooking, String numPlayers, String kitStatus) {
        this.arenaId = arenaId;
        this.arenaName = arenaName;
        this.dateBooking = dateBooking;
        this.timeBooking = timeBooking;
        this.numPlayers = numPlayers;
        this.kitStatus = kitStatus;
    }

    public String getArenaId() {
        return arenaId;
    }

    public void setArenaId(String arenaId) {
        this.arenaId = arenaId;
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public void setTimeBooking(String timeBooking) {
        this.timeBooking = timeBooking;
    }

    public String getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(String numPlayers) {
        this.numPlayers = numPlayers;
    }

    public String getKitStatus() {
        return kitStatus;
    }

    public void setKitStatus(String kitStatus) {
        this.kitStatus = kitStatus;
    }
}
