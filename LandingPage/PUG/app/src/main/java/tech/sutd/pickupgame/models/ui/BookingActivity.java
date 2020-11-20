package tech.sutd.pickupgame.models.ui;

import com.google.gson.annotations.SerializedName;

public class BookingActivity {

    @SerializedName("sport")
    private String sport;

    @SerializedName("epoch")
    private String epoch;

    @SerializedName("epoch_end")
    private String epochEnd;

    @SerializedName("loc")
    private String loc;

    @SerializedName("part")
    private String part;

    @SerializedName("desc")
    private String desc;

    @SerializedName("org")
    private String organizer;

    public BookingActivity(String sport, String epoch, String epochEnd, String loc, String part, String desc, String organizer) {
        this.sport = sport;
        this.epoch = epoch;
        this.epochEnd = epochEnd;
        this.loc = loc;
        this.part = part;
        this.desc = desc;
        this.organizer = organizer;
    }

    public BookingActivity(Builder builder) {
        sport = builder.sport;
        epoch = builder.epoch;
        epochEnd = builder.epochEnd;
        loc = builder.location;
        part = builder.participant;
        desc = builder.notes;
        organizer = builder.organizer;
    }

    public static class Builder {
        private String sport;
        private String epoch;
        private String epochEnd;
        private String location;
        private String participant;
        private String notes;
        private String organizer;

        public Builder setSport(String sport) {
            this.sport = sport;
            return this;
        }

        public Builder setEpoch(String epoch) {
            this.epoch = epoch;
            return this;
        }

        public Builder setEpochEnd(String epochEnd) {
            this.epochEnd = epochEnd;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setParticipant(String participant) {
            this.participant = participant;
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder setOrganizer(String organizer) {
            this.organizer = organizer;
            return this;
        }

        public BookingActivity build() {
            return new BookingActivity(this);
        }
    }

    public BookingActivity() {
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    public String getEpochEnd() {
        return epochEnd;
    }

    public void setEpochEnd(String epochEnd) {
        this.epochEnd = epochEnd;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
}
