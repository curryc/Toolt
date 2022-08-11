package com.curry.util.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-06 16:55
 * @description: 图片的bean
 **/
public class Picture implements Parcelable {
    //原图地址
    private String  path;
    //图片 Uri
    private Uri uri;
    //是否裁剪
    private boolean isCut;

    public Picture() {
    }

    protected Picture(Parcel in) {
        this.path = in.readString();
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.isCut = in.readByte() != 0;
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isCut() {
        return isCut;
    }

    public void setCut(boolean cut) {
        isCut = cut;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeParcelable(this.uri, flags);
        dest.writeByte(this.isCut ? (byte) 1 : (byte) 0);
    }

    @NonNull
    @Override
    public String toString() {
        return "path:" + path + "/uri:" + uri;
    }
}