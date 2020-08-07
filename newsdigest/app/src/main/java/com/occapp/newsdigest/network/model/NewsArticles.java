package com.occapp.newsdigest.network.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.occapp.newsdigest.database.SourceConverter;

@Entity
public class NewsArticles implements Parcelable {

    @TypeConverters(SourceConverter.class)
    @SerializedName("source")
    NewsSource source;

    @SerializedName("author")
    String author;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("url")
    String url;

    @SerializedName("urlToImage")
    String urlToImage;

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @SerializedName("publishedAt")
    String publishedAt;

    @SerializedName("content")
    String content;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewsArticles() {
    }

    protected NewsArticles(Parcel in) {
        this.source = in.readParcelable(NewsSource.class.getClassLoader());
        this.author = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.urlToImage = in.readString();
        this.publishedAt = in.readString();
        this.content = in.readString();
    }

    public NewsSource getSource() {
        return source;
    }

    public void setSource(NewsSource source) {
        this.source = source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.source, flags);
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.urlToImage);
        dest.writeString(this.publishedAt);
        dest.writeString(this.content);
    }

    public static final Parcelable.Creator<NewsArticles> CREATOR = new Parcelable.Creator<NewsArticles>() {
        @Override
        public NewsArticles createFromParcel(Parcel source) {
            return new NewsArticles(source);
        }

        @Override
        public NewsArticles[] newArray(int size) {
            return new NewsArticles[size];
        }
    };
}
