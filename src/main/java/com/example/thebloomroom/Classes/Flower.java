package com.example.thebloomroom.Classes;

public class Flower {

    private int id;
    private String flowerName;
    private String flowerDesc;
    private String imgName;
    private int catId;
    private double price;

    public Flower(int id, String flowerName, String flowerDesc, String imgName,  double price, int catId) {
        this.id = id;
        this.flowerName = flowerName;
        this.flowerDesc = flowerDesc;
        this.imgName = imgName;
        this.catId = catId;
        this.price = price;
    }

    public Flower() {
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getFlowerDesc() {
        return flowerDesc;
    }

    public void setFlowerDesc(String flowerDesc) {
        this.flowerDesc = flowerDesc;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
