package com.example.interntaskvendor;

public class uploadinfo {

    public String imageName;
    public String imageCatg,imagePrice;
    public String imageOffer,imageGst,imageCharge;
    public String imageURL;
    public uploadinfo(){}

    public uploadinfo(String imageName,  String imageCatg,String imagePrice,String imageGst,String imageCharge,String imageOffer,String url) {
        this.imageName = imageName;
        this.imageURL = url;
        this.imageCatg = imageCatg;
        this.imagePrice = imagePrice;
        this.imageGst = imageGst;
        this.imageCharge = imageCharge;
        this.imageOffer = imageOffer;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageCatg() {
        return imageCatg;
    }

    public String getImagePrice() {
        return imagePrice;
    }

    public String getImageOffer() {
        return imageOffer;
    }

    public String getImageGst() {
        return imageGst;
    }

    public String getImageCharge() {
        return imageCharge;
    }

    public String getImageURL() {
        return imageURL;
    }


}
