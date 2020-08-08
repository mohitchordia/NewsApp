package com.occapp.newsdigest.network.model;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity
public class NewsSource implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public NewsSource() {
    }

    protected NewsSource(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<NewsSource> CREATOR = new Parcelable.Creator<NewsSource>() {
        @Override
        public NewsSource createFromParcel(Parcel source) {
            return new NewsSource(source);
        }

        @Override
        public NewsSource[] newArray(int size) {
            return new NewsSource[size];
        }
    };
}
