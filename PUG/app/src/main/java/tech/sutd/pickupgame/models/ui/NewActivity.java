package tech.sutd.pickupgame.models.ui;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import tech.sutd.pickupgame.models.User;

@Entity(tableName = "new_activity")
public class NewActivity {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("checked")
    private int checked;

    @SerializedName("sport_img")
    private int sportImg;

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

    @SerializedName("identifier")
    private String identifier;

    @SerializedName("participant_img")
    private int participantImg;

    @SerializedName("participant")
    private String participant;

    @SerializedName("count")
    private String count;

    @SerializedName("notes_img")
    private int notesImg;

    @SerializedName("notes")
    private String notes;

    public NewActivity(@NonNull String id, int checked, int sportImg, String sport, int clockImg, String clock, String endClock, int locationImg, String location, int organizerImg, String organizer, String identifier, int participantImg, String participant, String count, int notesImg, String notes) {
        this.id = id;
        this.checked = checked;
        this.sportImg = sportImg;
        this.sport = sport;
        this.clockImg = clockImg;
        this.clock = clock;
        this.endClock = endClock;
        this.locationImg = locationImg;
        this.location = location;
        this.organizerImg = organizerImg;
        this.organizer = organizer;
        this.identifier = identifier;
        this.participantImg = participantImg;
        this.participant = participant;
        this.count = count;
        this.notesImg = notesImg;
        this.notes = notes;
    }

    public NewActivity(Builder builder) {
        id = builder.id;
        checked = builder.checked;
        sport = builder.sport;
        clockImg = builder.clockImg;
        clock = builder.clock;
        endClock = builder.endClock;
        locationImg = builder.locationImg;
        location = builder.location;
        organizerImg = builder.organizerImg;
        organizer = builder.organizer;
        identifier = builder.identifier;
        sportImg = builder.sportImg;
        participantImg = builder.participantImg;
        participant = builder.participant;
        count = builder.count;
        notesImg = builder.notesImg;
        notes = builder.notes;
    }

    public static class Builder {
        private final String id;
        private int checked;
        private String sport;
        private int clockImg;
        private String clock;
        private String endClock;
        private int locationImg;
        private String location;
        private int organizerImg;
        private String organizer;
        private String identifier;
        private int sportImg;
        private int participantImg;
        private String participant;
        private String count;
        private int notesImg;
        private String notes;

        public Builder(String id) {
            this.id = id;
        }

        public Builder setChecked(int checked) {
            this.checked = checked;
            return this;
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

        public Builder setIdentifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder setSportImg(int sportImg) {
            this.sportImg = sportImg;
            return this;
        }

        public Builder setParticipantImg(int participantImg) {
            this.participantImg = participantImg;
            return this;
        }

        public Builder setParticipant(String participant) {
            this.participant = participant;
            return this;
        }

        public Builder setCount(String count) {
            this.count = count;
            return this;
        }

        public Builder setNotesImg(int notesImg) {
            this.notesImg = notesImg;
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
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

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getSportImg() {
        return sportImg;
    }

    public void setSportImg(int sportImg) {
        this.sportImg = sportImg;
    }

    public int getParticipantImg() {
        return participantImg;
    }

    public void setParticipantImg(int participantImg) {
        this.participantImg = participantImg;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getNotesImg() {
        return notesImg;
    }

    public void setNotesImg(int notesImg) {
        this.notesImg = notesImg;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
