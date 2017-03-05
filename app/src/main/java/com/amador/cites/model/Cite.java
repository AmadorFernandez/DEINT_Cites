package com.amador.cites.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amador on 5/03/17.
 */

public class Cite implements Parcelable {

    private long id;
    private String date;
    private String timeStart;
    private String timeEnd;
    private String completeNameClien;
    private long idClient;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getCompleteNameClien() {
        return completeNameClien;
    }

    public void setCompleteNameClien(String completeNameClien) {
        this.completeNameClien = completeNameClien;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.date);
        dest.writeString(this.timeStart);
        dest.writeString(this.timeEnd);
        dest.writeString(this.completeNameClien);
        dest.writeLong(this.idClient);
    }

    public Cite() {
    }

    protected Cite(Parcel in) {
        this.id = in.readLong();
        this.date = in.readString();
        this.timeStart = in.readString();
        this.timeEnd = in.readString();
        this.completeNameClien = in.readString();
        this.idClient = in.readLong();
    }

    public static final Parcelable.Creator<Cite> CREATOR = new Parcelable.Creator<Cite>() {
        @Override
        public Cite createFromParcel(Parcel source) {
            return new Cite(source);
        }

        @Override
        public Cite[] newArray(int size) {
            return new Cite[size];
        }
    };
}
