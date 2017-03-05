package com.amador.cites.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amador on 5/03/17.
 */

public class Client implements Parcelable {

    private long id;
    private String name;
    private String phone;
    private String surnames;
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.surnames);
        dest.writeString(this.address);
    }

    public Client() {
    }

    protected Client(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.phone = in.readString();
        this.surnames = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel source) {
            return new Client(source);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
}
