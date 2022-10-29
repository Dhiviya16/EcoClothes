package com.example.clothingrecycler;

public class RequestModel {

    String ApUid,ApEmail,ApFid,ApName,ApContactNumber,ApAddress,ApQuantity
            ,ApTime,ApDate,ApImage,ApStatus,ApStatusUid,ApRemark;

    public RequestModel() {
    }

    public RequestModel(String apUid, String apEmail, String apFid, String apName, String apContactNumber, String apAddress, String apQuantity, String apTime, String apDate, String apImage, String apStatus, String apStatusUid, String apRemark) {
        ApUid = apUid;
        ApEmail = apEmail;
        ApFid = apFid;
        ApName = apName;
        ApContactNumber = apContactNumber;
        ApAddress = apAddress;
        ApQuantity = apQuantity;
        ApTime = apTime;
        ApDate = apDate;
        ApImage = apImage;
        ApStatus = apStatus;
        ApStatusUid = apStatusUid;
        ApRemark = apRemark;
    }

    public String getApUid() {
        return ApUid;
    }

    public void setApUid(String apUid) {
        ApUid = apUid;
    }

    public String getApEmail() {
        return ApEmail;
    }

    public void setApEmail(String apEmail) {
        ApEmail = apEmail;
    }

    public String getApFid() {
        return ApFid;
    }

    public void setApFid(String apFid) {
        ApFid = apFid;
    }

    public String getApName() {
        return ApName;
    }

    public void setApName(String apName) {
        ApName = apName;
    }

    public String getApContactNumber() {
        return ApContactNumber;
    }

    public void setApContactNumber(String apContactNumber) {
        ApContactNumber = apContactNumber;
    }

    public String getApAddress() {
        return ApAddress;
    }

    public void setApAddress(String apAddress) {
        ApAddress = apAddress;
    }

    public String getApQuantity() {
        return ApQuantity;
    }

    public void setApQuantity(String apQuantity) {
        ApQuantity = apQuantity;
    }

    public String getApTime() {
        return ApTime;
    }

    public void setApTime(String apTime) {
        ApTime = apTime;
    }

    public String getApDate() {
        return ApDate;
    }

    public void setApDate(String apDate) {
        ApDate = apDate;
    }

    public String getApImage() {
        return ApImage;
    }

    public void setApImage(String apImage) {
        ApImage = apImage;
    }

    public String getApStatus() {
        return ApStatus;
    }

    public void setApStatus(String apStatus) {
        ApStatus = apStatus;
    }

    public String getApStatusUid() {
        return ApStatusUid;
    }

    public void setApStatusUid(String apStatusUid) {
        ApStatusUid = apStatusUid;
    }

    public String getApRemark() {
        return ApRemark;
    }

    public void setApRemark(String apRemark) {
        ApRemark = apRemark;
    }
}
