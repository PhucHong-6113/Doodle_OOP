/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public class ReceiptHead {
    
    private String receiptType;
    private String receiptCode;
    private LocalDate currentDate = LocalDate.now();

    public ReceiptHead() {
    }

    public ReceiptHead(String receiptType, String receiptCode) {
        this.receiptType = receiptType;
        this.receiptCode = receiptCode;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }
    
    public ReceiptHead(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    public void printReceiptHead() {
        System.out.print(receiptType);
        System.out.format("%15s %30s\n", " receipt code: " + receiptCode, "Receipt Date: " + currentDate);
    }
    
    
    @Override
    public String toString() {
        return super.toString();
    }

}
