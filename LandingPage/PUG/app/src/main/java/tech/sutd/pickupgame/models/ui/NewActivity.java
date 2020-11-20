package tech.sutd.pickupgame.models.ui;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import tech.sutd.pickupgame.models.User;

@Entity(primaryKeys = {"id"}, tableName = "new_activity")
public class NewActivity {

    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("sport")
    private String sport;

    @SerializedName("clock_img")
    private int clockImg;

    @SerializedName("clock")
    private String clock;

    @SerializedName("end_clock")
    private String endClock;

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

    public NewActivity(@NonNull String id, String sport, int clockImg, String clock, String endClock, int locationImg, String location, int organizerImg, String organizer, int sportImg) {
        this.id = id;
        this.sport = sport;
        this.clockImg = clockImg;
        this.clock = clock;
        this.endClock = endClock;
        this.locationImg = locationImg;
        this.location = location;
        this.organizerImg = organizerImg;
        this.organizer = organizer;
        this.sportImg = sportImg;
    }

    public NewActivity(Builder builder) {
        id = builder.id;
        sport = builder.sport;
        clockImg = builder.clockImg;
        clock = builder.clock;
        endClock = builder.endClock;
        locationImg = builder.locationImg;
        location = builder.location;
        organizerImg = builder.organizerImg;
        organizer = builder.organizer;
        sportImg = builder.sportImg;
    }

    public static class Builder {
        private String id;
        private String sport;
        private int clockImg;
        private String clock;
        private String endClock;
        private int locationImg;
        private String location;
        private int organizerImg;
        private String organizer;
        private int sportImg;

        public Builder(String id) {
            this.id = id;
        }

        public Builder setSport(String sport) {
            this.sport = sport;
            return this;
        }

        public Builder setClockImg(int clockImg) {
            this.clockImg = clockImg;
            return this;
        }

        public Builder setClock(String clock) {
            this.clock = clock;
            return this;
        }

        public Builder setEndClock(String endClock) {
            this.endClock = endClock;
            return this;
        }

        public Builder setLocationImg(int locationImg) {
            this.locationImg = locationImg;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setOrganizerImg(int organizerImg) {
            this.organizerImg = organizerImg;
            return this;
        }

        public Builder setOrganizer(String organizer) {
            this.organizer = organizer;
            return this;
        }

        public Builder setSportImg(int sportImg) {
            this.sportImg = sportImg;
            return this;
        }

        public NewActivity build() {
            return new NewActivity(this);
        }
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    public String getEndClock() {
        return endClock;
    }

    public void setEndClock(String endClock) {
        this.endClock = endClock;
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
