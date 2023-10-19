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
public class Product implements Serializable {

    private String code, status, expirationDate, manufacturingDate, name;
    private int quantity;

    public Product(String code, String name, String manufacturingDate, String expirationDate, int quantity, String status) {
        this.code = code;
        this.name = name;
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.status = status;
    }

    public Product() {
    }

    public String getCode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void showInfo() {
 
        System.out.println(getInfo());
    }
    
    public String checkStatus(int n){
        if (n<=0){
            return "Not Available";
        }
        else return "Available";
    }
    
    public String getInfo(){
        return String.format("| %-6s | %20s | %20s | %15s | %5s | %15s", code, name, manufacturingDate, expirationDate, quantity, checkStatus(quantity));
    }

    @Override
    public String toString() {
        return String.format("%10s %10s %25s %25s %10s %10s", code, name, manufacturingDate, expirationDate, quantity, checkStatus(quantity));
    }
    
    

}
