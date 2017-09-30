package com.ogloba.kyc.mrz.scanner.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.microblink.recognizers.blinkid.mrtd.MRTDDocumentType;
import com.microblink.results.date.Date;

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
    private String issuer;
    private String opt1;
    private String opt2;

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
        this.issuer = source.readString();
        this.opt1 = source.readString();
        this.opt2 = source.readString();
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
        dest.writeString(issuer);
        dest.writeString(opt1);
        dest.writeString(opt2);
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

    public void setDateOfBirth(Date dateOfBirth) {
        if(dateOfBirth != null){
            this.dateOfBirth = dateOfBirth.getYear() + "/" + dateOfBirth.getMonth() + "/" + dateOfBirth.getDay();
        }
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(MRTDDocumentType documentType) {
        if(documentType != null){
            this.documentType = documentType.toString().replace("MRTD_TYPE_", "");
        }
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        if(documentNumber != null){
            this.documentNumber = documentNumber.replace("<", "");
        }
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        if(dateOfBirth != null){
            this.expireDate = expireDate.getYear() + "/" + expireDate.getMonth() + "/" + expireDate.getDay();
        }
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    @Override
    public String toString() {
        String result =
                "Surname: " + surname + "\n" +
                "Forename: " + forename + "\n" +
                "Gender: " + gender + "\n" +
                "DateOfBirth: " + dateOfBirth + "\n" +
                "DocumentType: " + documentType + "\n" +
                "DocumentNumber: " + documentNumber + "\n" +
                "ExpireDate: " + expireDate + "\n" +
                "Nationality:" + nationality + "\n" +
                "Issuer:" + issuer + "\n" +
                "Opt1: " + opt1 + "\n" +
                "Opt2: " + opt2;
        return result;
    }
}
