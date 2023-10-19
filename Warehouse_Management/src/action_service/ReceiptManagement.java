/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ReceiptManagement extends ArrayList<ReceiptItem> {

    public ReceiptManagement() {

    }

    public ReceiptItem getProductBycode(String code) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getCode().equalsIgnoreCase(code)) {
                return this.get(i);
            }
        }
        return null;
    }

    public int getIndexBycode(String code) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getCode().equalsIgnoreCase(code)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isExist(String code) {
        for (ReceiptItem receiptItem : this) {
            if (receiptItem.getCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public void printTableHead() {
        System.out.format("| %6s | %6s | %20s | %20s | %15s | %5s | %15s\n", "No", "Code", "ReceiptItem name", "Manufacturing Date", "Expired Date", "Quan", "Status");
    }
    
    public void showAllList(ArrayList<ReceiptItem> list) {
        if (this.isEmpty()) {
            System.out.println("There is not any list");
        } else {
            {
                printTableHead();
                int i = 1;
                for (ReceiptItem receiptItem : list) {
                    String str = String.format("| %6s ", i++);
                    System.out.println(str + receiptItem.getInfo());
                }
            }
        }
    }
    
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("There is not any list");
        } else {
            {
                printTableHead();
                int i = 1;
                for (ReceiptItem receiptItem : this) {
                    String str = String.format("| %6s ", i++);
                    System.out.println(str + receiptItem.getInfo());
                }
            }
        }
    }
}
    