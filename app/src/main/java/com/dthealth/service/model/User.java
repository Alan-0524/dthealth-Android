package com.dthealth.service.model;

import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("id")
    private String id;
    @SerializedName("userAccount")
    private String userAccount;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("middleName")
    private String middleName;
    @SerializedName("lastName")
    private String lastName;
    //0:female, 1:male, 3:other
    @SerializedName("gender")
    private String gender;
    @SerializedName("dateOfBirth")
    private String dateOfBirth;
    @SerializedName("password")
    private String password;
    @SerializedName("roleCode")
    private String roleCode;
    @SerializedName("address")
    private String address;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("surgeryTime")
    private String surgeryTime;
    @SerializedName("symptom")
    private String symptom;
    @SerializedName("reexamination")
    private String reexamination;
    //0 normal,1 disable
    @SerializedName("status")
    private String status;

    /**
     * for format
     */

    private String fullName;

    private String shownGender;

    public User() {
    }

    public User(String id, String userAccount, String firstName, String middleName, String lastName, String gender, String dateOfBirth, String password, String roleCode, String address, String telephone, String createTime, String surgeryTime, String symptom, String reexamination, String status) {
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
    }

    public String getId() {
        return id;
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

    public String getFullName() {
        StringBuilder stringBuilder = new StringBuilder("");
        if(firstName != null){
            stringBuilder.append(firstName);
            stringBuilder.append(" ");
        }
        if(middleName != null){
            stringBuilder.append(middleName);
            stringBuilder.append(" ");
        }
        if(lastName != null){
            stringBuilder.append(lastName);
        }
        return stringBuilder.toString();
    }

    public String getShownGender() {
        return gender.equals("0") ? "female" : "male";
    }

}
