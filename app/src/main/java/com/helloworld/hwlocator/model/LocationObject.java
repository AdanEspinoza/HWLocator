package com.helloworld.hwlocator.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationObject implements Parcelable {

    private String name;
    private String address;
    private String address2;
    private String city;
    private String state;
    private String zip_postal_code;
    private String phone;
    private String fax;
    private String latitude;
    private String longitude;
    private String office_image;

    private String distance;

    public LocationObject(){

    }
    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     *
     * @param address2
     * The address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The zipPostalCode
     */
    public String getZipPostalCode() {
        return zip_postal_code;
    }

    /**
     *
     * @param zipPostalCode
     * The zip_postal_code
     */
    public void setZipPostalCode(String zipPostalCode) {
        this.zip_postal_code = zipPostalCode;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The fax
     */
    public String getFax() {
        return fax;
    }

    /**
     *
     * @param fax
     * The fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The officeImage
     */
    public String getOfficeImage() {
        return office_image;
    }

    /**
     *
     * @param officeImage
     * The office_image
     */
    public void setOfficeImage(String officeImage) {
        this.office_image = officeImage;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }



    protected LocationObject(Parcel in) {
        name = in.readString();
        address = in.readString();
        address2 = in.readString();
        city = in.readString();
        state = in.readString();
        zip_postal_code = in.readString();
        phone = in.readString();
        fax = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        office_image = in.readString();
        distance = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(address2);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(zip_postal_code);
        dest.writeString(phone);
        dest.writeString(fax);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(office_image);
        dest.writeString(distance);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LocationObject> CREATOR = new Parcelable.Creator<LocationObject>() {
        @Override
        public LocationObject createFromParcel(Parcel in) {
            return new LocationObject(in);
        }

        @Override
        public LocationObject[] newArray(int size) {
            return new LocationObject[size];
        }
    };
}