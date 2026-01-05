package com.onlineecommerce.model;

public class DigitalProduct extends Product {
    private String link;

    // Constructor matching Product(int id, String name, String description, double price)
    public DigitalProduct(int id, String name, String description, double price, String link) {
        super(id, name, description, price);
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return super.toString() + " | Digital Link: " + link;
    }
}