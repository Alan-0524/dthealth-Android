package com.dthealth.dao.entity;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.bassaer.chatmessageview.model.IChatUser;

/**
 * Class exposing authenticated user details to the UI.
 */
@Entity
public class LoggedInUser implements IChatUser {
    @NonNull
    @PrimaryKey
    private String id;
    @ColumnInfo(name = "user_account")
    private String userAccount;
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "middle_name")
    private String middleName;
    @ColumnInfo(name = "last_name")
    private String lastName;
    //0:female, 1:male, 3:other
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "date_of_birth")
    private String dateOfBirth;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "role_code")
    private String roleCode;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "telephone")
    private String telephone;
    @ColumnInfo(name = "create_time")
    private String createTime;
    @ColumnInfo(name = "surgery_time")
    private String surgeryTime;
    @ColumnInfo(name = "symptom")
    private String symptom;
    @ColumnInfo(name = "reexamination")
    private String reexamination;
    //0 normal,1 disable
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "shown_gender")
    private String shownGender;
    @ColumnInfo(name = "age")
    private String age;
    @ColumnInfo(name = "full_name")
    private String fullName;

    public LoggedInUser() {
    }

    public LoggedInUser(User user) {
        this.id = user.getId();
        this.userAccount = user.getUserAccount();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.password = user.getPassword();
        this.roleCode = user.getRoleCode();
        this.address = user.getAddress();
        this.telephone = user.getTelephone();
        this.createTime = user.getCreateTime();
        this.surgeryTime = user.getSurgeryTime();
        this.symptom = user.getSymptom();
        this.reexamination = user.getReexamination();
        this.status = user.getStatus();
        this.shownGender = user.getShownGender();
        this.age = user.getAge();
        this.fullName = user.getFullName();
    }



    public void setId(String id) {
        this.id = id;
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

    @Nullable
    @Override
    public Bitmap getIcon() {
        return null;
    }

    @Nullable
    @Override
    public String getName() {
        return fullName;
    }

    @NonNull
    @Override
    public void setIcon(Bitmap bitmap) {

    }

    @NonNull
    @Override
    public String getId() {
        return id;
    }
}
