package com.abmtech.mybillingsolution.models;

public class UsersModel {
    private String userId,
            email,
            password,
            userName,
            shopName,
            mobileNumber,
            gstNumber,
            addressLineOne,
            addressLineTwo,
            addressLineThree,
            description,
            bankName,
            accountHolderName,
            accountNumber,
            bankIfscCode,
            bankBranchAddress;

    public UsersModel() {
    }

    public UsersModel(String userId, String email, String password, String userName, String shopName,
                      String mobileNumber, String gstNumber, String addressLineOne, String addressLineTwo,
                      String addressLineThree, String description, String bankName, String accountHolderName,
                      String accountNumber, String bankIfscCode, String bankBranchAddress) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.shopName = shopName;
        this.mobileNumber = mobileNumber;
        this.gstNumber = gstNumber;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.addressLineThree = addressLineThree;
        this.description = description;
        this.bankName = bankName;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.bankIfscCode = bankIfscCode;
        this.bankBranchAddress = bankBranchAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getAddressLineThree() {
        return addressLineThree;
    }

    public void setAddressLineThree(String addressLineThree) {
        this.addressLineThree = addressLineThree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankIfscCode() {
        return bankIfscCode;
    }

    public void setBankIfscCode(String bankIfscCode) {
        this.bankIfscCode = bankIfscCode;
    }

    public String getBankBranchAddress() {
        return bankBranchAddress;
    }

    public void setBankBranchAddress(String bankBranchAddress) {
        this.bankBranchAddress = bankBranchAddress;
    }
}
