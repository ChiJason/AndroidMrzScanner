package com.ogloba.kyc.mrz.scanner.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JasonChi on 2017/5/4.
 */

public class IdentificationDetail implements Parcelable {

    private String surname;
    private String forename;
    private String gender;
    private String dateOfBirth;
    private String documentType;
    private String documentNumber;
    private String expireDate;
    private String nationality;

    public static final Creator<IdentificationDetail> CREATOR = new Creator<IdentificationDetail>() {
        @Override
        public IdentificationDetail createFromParcel(Parcel source) {
            return new IdentificationDetail(source);
        }
        @Override
        public IdentificationDetail[] newArray(int size) {
            return new IdentificationDetail[size];
        }
    };

    public IdentificationDetail(){}

    public IdentificationDetail(Parcel source) {
        this.surname = source.readString();
        this.forename = source.readString();
        this.gender = source.readString();
        this.dateOfBirth = source.readString();
        this.documentType = source.readString();
        this.documentNumber = source.readString();
        this.expireDate = source.readString();
        this.nationality = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(surname);
        dest.writeString(forename);
        dest.writeString(gender);
        dest.writeString(dateOfBirth);
        dest.writeString(documentType);
        dest.writeString(documentNumber);
        dest.writeString(expireDate);
        dest.writeString(nationality);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
