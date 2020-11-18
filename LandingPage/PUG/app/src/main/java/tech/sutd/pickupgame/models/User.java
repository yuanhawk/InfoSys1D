package tech.sutd.pickupgame.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("passwd")
    private String passwd;

    public User(int id, String name, String email, String passwd) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwd = passwd;
    }

    public User(final Builder builder) {
        id = builder.id;
        name = builder.name;
        email = builder.email;
        passwd = builder.passwd;
    }

    public static User defaultUser() {
        return new User(0, "-1", "-1", "-1");
    }

    public static class Builder {
        private int id;
        private String name;
        private String email;
        private String passwd;

        public Builder(int id) {
            this.id = id;
        }

        public Builder setName(final String name) {
            this.name = name;
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

        public User build() {
            return new User(this);
        }
    }

    @Ignore
    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
