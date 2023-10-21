/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class Vehicle implements Serializable {

    private String ID_Vehicle, Name_Vehicle, color_Vehicle, brand_Vehicle, type, productYear;
    private int price_Vehicle;
    
    public Vehicle(){


    }

    public Vehicle(String iD_Vehicle, String name_Vehicle, String color_Vehicle, String brand_Vehicle, String type, String productYear, int price_Vehicle) {
        this.ID_Vehicle = iD_Vehicle;
        this.Name_Vehicle = name_Vehicle;
        this.color_Vehicle = color_Vehicle;
        this.brand_Vehicle = brand_Vehicle;
        this.type = type;
        this.productYear = productYear;
        this.price_Vehicle = price_Vehicle;
    }

    
    public String getID_Vehicle() {
        return ID_Vehicle;
    }



    public void setID_Vehicle(String iD_Vehicle) {
        ID_Vehicle = iD_Vehicle;
    }



    public String getName_Vehicle() {
        return Name_Vehicle;
    }



    public void setName_Vehicle(String name_Vehicle) {
        Name_Vehicle = name_Vehicle;
    }



    public String getColor_Vehicle() {
        return color_Vehicle;
    }



    public void setColor_Vehicle(String color_Vehicle) {
        this.color_Vehicle = color_Vehicle;
    }



    public String getBrand_Vehicle() {
        return brand_Vehicle;
    }



    public void setBrand_Vehicle(String brand_Vehicle) {
        this.brand_Vehicle = brand_Vehicle;
    }



    public String getType() {
        return type;
    }



    public void setType(String type) {
        this.type = type;
    }



    public String getProductYear() {
        return productYear;
    }



    public void setProductYear(String productYear) {
        this.productYear = productYear;
    }



    public int getPrice_Vehicle() {
        return price_Vehicle;
    }



    public void setPrice_Vehicle(int price_Vehicle) {
        this.price_Vehicle = price_Vehicle;
    }



    public void showInfo() {
 
        System.out.println(getInfo());
    }

    
    public String getInfo(){
        return String.format(" %6s | %10s | %10s | %10s | %10s | %15s | %7s ", ID_Vehicle, Name_Vehicle,  color_Vehicle, brand_Vehicle, type, productYear, price_Vehicle);
    }

    @Override
    public String toString() {
        return null;
    }

    
   
}
