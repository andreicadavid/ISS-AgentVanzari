package com.example.program_agentvanzari.Domain;

import java.io.Serializable;

public class Product extends Entity implements Serializable {
    private String name;
    private double price;
    private int nr;
    public Product(int id, String name, double price, int nr) {
        super(id);
        this.name = name;
        this.price = price;
        this.nr = nr;
    }

    @Override
    public int getId() {return super.getId();}
    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getNr() {return nr;}
    @Override public void setId(int id) {super.setId(id);}
    public void setName(String name) {this.name = name;}
    public void setPrice(double price) {this.price = price;}
    public void setNr(int nr) {this.nr = nr;}

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", nr=" + nr +
                '}';
    }
}
