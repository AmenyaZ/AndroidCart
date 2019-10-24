package com.example.cart.data;

public class Utensils {

    private String id,name,price,imageName;
    private int image;

    public Utensils(String id,String name, String price, int image,String imageName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.imageName = imageName;
    }

    public Utensils() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
