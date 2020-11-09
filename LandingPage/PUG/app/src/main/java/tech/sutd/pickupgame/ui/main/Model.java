package tech.sutd.pickupgame.ui.main;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {

    public long id;
    public String name;

    public Model(long id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Model(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }
}
