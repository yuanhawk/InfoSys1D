package tech.sutd.pickupgame.models;

import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private String age;

    @SerializedName("email")
    private String email;

    @SerializedName("passwd")
    private String passwd;

    public UserProfile(String name, String age, String email, String passwd) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.passwd = passwd;
    }

    public UserProfile(final Builder builder) {
        name = builder.name;
        age = builder.age;
        email = builder.email;
        passwd = builder.passwd;
    }

    public static class Builder {
        private String name;
        private String age;
        private String email;
        private String passwd;

        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(String age) {
            this.age = age;
            return this;
        }

        public Builder setEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder setPasswd(final String passwd) {
            this.passwd = passwd;
            return this;
        }

        public UserProfile build() {
            return new UserProfile(this);
        }
    }

    public UserProfile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
