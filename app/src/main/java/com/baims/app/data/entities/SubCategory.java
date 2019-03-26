package com.baims.app.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Radwa Elsahn on 10/17/2018.
 */
public class SubCategory implements Parcelable {
    private String name;
    private String content;
    private int parent_id;
    private String link;

    protected SubCategory(Parcel in) {
        name = in.readString();
        content = in.readString();
        parent_id = in.readInt();
    }

    public static final Creator<SubCategory> CREATOR = new Creator<SubCategory>() {
        @Override
        public SubCategory createFromParcel(Parcel in) {
            return new SubCategory(in);
        }

        @Override
        public SubCategory[] newArray(int size) {
            return new SubCategory[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(content);
        dest.writeInt(parent_id);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
