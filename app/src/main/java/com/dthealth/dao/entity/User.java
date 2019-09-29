package com.dthealth.dao.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.github.bassaer.chatmessageview.model.IChatUser;
import com.google.gson.annotations.SerializedName;


@Entity
public class User implements IChatUser{
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private String id;
    @ColumnInfo(name = "user_account")
    @SerializedName("userAccount")
    private String userAccount;
    @ColumnInfo(name = "first_name")
    @SerializedName("firstName")
    private String firstName;
    @ColumnInfo(name = "middle_name")
    @SerializedName("middleName")
    private String middleName;
    @ColumnInfo(name = "last_name")
    @SerializedName("lastName")
    private String lastName;
    //0:female, 1:male, 3:other
    @ColumnInfo(name = "gender")
    @SerializedName("gender")
    private String gender;
    @ColumnInfo(name = "date_of_birth")
    @SerializedName("dateOfBirth")
    private String dateOfBirth;
    @ColumnInfo(name = "password")
    @SerializedName("password")
    private String password;
    @ColumnInfo(name = "role_code")
    @SerializedName("roleCode")
    private String roleCode;
    @ColumnInfo(name = "address")
    @SerializedName("address")
    private String address;
    @ColumnInfo(name = "telephone")
    @SerializedName("telephone")
    private String telephone;
    @ColumnInfo(name = "create_time")
    @SerializedName("createTime")
    private String createTime;
    @ColumnInfo(name = "surgery_time")
    @SerializedName("surgeryTime")
    private String surgeryTime;
    @ColumnInfo(name = "symptom")
    @SerializedName("symptom")
    private String symptom;
    @ColumnInfo(name = "reexamination")
    @SerializedName("reexamination")
    private String reexamination;
    //0 normal,1 disable
    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String status;
    @ColumnInfo(name = "shown_gender")
    @SerializedName("shownGender")
    private String shownGender;
    @ColumnInfo(name = "age")
    @SerializedName("age")
    private String age;
    @ColumnInfo(name = "full_name")
    @SerializedName("fullName")
    private String fullName;

    public User() {
    }

    @Ignore
    public User(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    @Ignore
    public User(String id, String userAccount, String firstName, String middleName, String lastName, String gender, String dateOfBirth, String password, String roleCode, String address, String telephone, String createTime, String surgeryTime, String symptom, String reexamination, String status, String shownGender, String age, String fullName) {
        this.id = id;
        this.userAccount = userAccount;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.roleCode = roleCode;
        this.address = address;
        this.telephone = telephone;
        this.createTime = createTime;
        this.surgeryTime = surgeryTime;
        this.symptom = symptom;
        this.reexamination = reexamination;
        this.status = status;
        this.shownGender = shownGender;
        this.age = age;
        this.fullName = fullName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSurgeryTime() {
        return surgeryTime;
    }

    public void setSurgeryTime(String surgeryTime) {
        this.surgeryTime = surgeryTime;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getReexamination() {
        return reexamination;
    }

    public void setReexamination(String reexamination) {
        this.reexamination = reexamination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShownGender() {
        return shownGender;
    }

    public void setShownGender(String shownGender) {
        this.shownGender = shownGender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NonNull
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public Bitmap getIcon() {
        return null;
    }

    @Nullable
    @Override
    public void setIcon(Bitmap bitmap) {

    }

    @Nullable
    @Override
    public String getName() {
        return fullName;
    }

//    public String getShownGender() {
////        return gender.equals("0") ? "female" : "male";
//        return shownGender;
//    }

}
