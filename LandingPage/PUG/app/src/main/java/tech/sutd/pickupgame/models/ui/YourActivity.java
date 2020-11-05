package tech.sutd.pickupgame.models.ui;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "your_activity")
public class YourActivity {
    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("sport")
    private String sport;

    @SerializedName("clock_img")
    private int clockImg;

    @SerializedName("clock")
    private String clock;

    @SerializedName("location_img")
    private int locationImg;

    @SerializedName("location")
    private String location;

    @SerializedName("organizer_img")
    private int organizerImg;

    @SerializedName("organizer")
    private String organizer;

    @SerializedName("sport_img")
    private int sportImg;

    public YourActivity(int id, String sport, int clockImg, String clock, int locationImg, String location, int organizerImg, String organizer, int sportImg) {
        this.id = id;
        this.sport = sport;
        this.clockImg = clockImg;
        this.clock = clock;
        this.locationImg = locationImg;
        this.location = location;
        this.organizerImg = organizerImg;
        this.organizer = organizer;
        this.sportImg = sportImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getClockImg() {
        return clockImg;
    }

    public void setClockImg(int clockImg) {
        this.clockImg = clockImg;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public int getLocationImg() {
        return locationImg;
    }

    public void setLocationImg(int locationImg) {
        this.locationImg = locationImg;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOrganizerImg() {
        return organizerImg;
    }

    public void setOrganizerImg(int organizerImg) {
        this.organizerImg = organizerImg;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public int getSportImg() {
        return sportImg;
    }

    public void setSportImg(int sportImg) {
        this.sportImg = sportImg;
    }
}
