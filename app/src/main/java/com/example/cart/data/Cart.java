package com.example.cart.data;

public class Cart {
    private String name,quantity,price;
    private int total;

    public Cart(String name, String quantity, String price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }


    public Cart() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTotal() {
        return this.total=Integer.parseInt(quantity)*Integer.parseInt(price);
    }

}
