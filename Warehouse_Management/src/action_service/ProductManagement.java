/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import menuGUI.MainMenu;
import tool.Inputter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author ASUS
 */
public class ProductManagement extends ArrayList<Product> {

    public ProductManagement() {

    }

    public Product getProductBycode(String code) {
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
        for (Product aThi : this) {
            if (aThi.getCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public void printTableHead() {
        System.out.format("| %6s | %6s | %20s | %20s | %15s | %5s | %15s\n", "No", "Code", "Product name", "Manufacturing Date", "Expired Date", "Quan", "Status");
    }
    
    public void showAllList(ArrayList<Product> list) {
        if (this.isEmpty()) {
            System.out.println("There is not any list");
        } else {
            {
                printTableHead();
                int i = 1;
                for (Product product : list) {
                    String str = String.format("| %6s ", i++);
                    System.out.println(str + product.getInfo());
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
                for (Product product : this) {
                    String str = String.format("| %6s ", i++);
                    System.out.println(str + product.getInfo());
                }
            }
        }
    }

    public void addProduct() {

        if (!this.isEmpty()) {
            this.showAll();
        }

        String tempcode, tempName, tempManufaturingDate, tempExpirationDate, tempStatus;
        int tempQuantity;

        while (true) {
            tempcode = Inputter.getString("Please input new Product code- ", "Invalid Product format (Pxxx)!!",
                    "This feild can not be blank!!", Regex.ProductRegex);
            if (isExist(tempcode)){
                System.out.println("Existed product!!");
            }else break;
        }
        
        tempName = Inputter.getName("Please input new Product name- ", "This feild can not be blank!!", 1, 20);
        tempManufaturingDate = Inputter.getString("Please input new manufacturing date- ",
                "Invalid date (yyyy-MM-dd)", "This feild can not be blank!!", Regex.dateUpdateRegex);
        tempExpirationDate = Inputter.getString("Please input new expiration date- ", "Invalid date (yyyy-MM-dd)",
                "This feild can not be blank!!", Regex.dateUpdateRegex);
        tempQuantity = Inputter.getInteger("Please input new quantity- ", "Invalid value!!", "Can not exceed 300", 0,
                300);
        if (tempQuantity == 0) {
            tempStatus = "Not Available";
        } else {
            tempStatus = "Available";
        }

        Product product = new Product(tempcode.toUpperCase(), tempName, tempManufaturingDate, tempExpirationDate,
                tempQuantity, tempStatus);
        this.add(product);
        
        System.out.println("SUCCESSFULLY ADDED");

        if (Inputter.getBoolean("Do you want to add more? (yes/no)", "invalid value")) {
            addProduct();
        }
    }

    public void updateProduct() {
        if (this.isEmpty()) {
            System.out.println("The list is empty!!!");
            return;
        }
        this.showAll(); 
        
        String code = Inputter.getString("Please input Product code that you want to update- ", "Invalid Product format (Pxxx)!!",
                "This feild can not be blank!!", Regex.ProductRegex);
        if (!isExist(code)) {
            System.out.println("The Product with the code " + code + " does not exist");
        } else {
            Product p = this.getProductBycode(code);
            MainMenu mn = new MainMenu();
            mn.addOptions("Update product name");
            mn.addOptions("Update product manufacturing date");
            mn.addOptions("Update product expiration date");
            mn.addOptions("Update product quantity");
            mn.addOptions("Exit to Product menu");

            boolean continues = true;

            while (continues) {
                mn.printMenu();
                switch (mn.getOption()) {
                    case 1:
                        p.setName(Inputter.getName("Please input new name- ", "This feild can not be blank!!", 1, 20));

                        this.showAll();
                        break;
                    case 2:
                        p.setManufacturingDate(Inputter.getString("Please input updated manufacturing date- ",
                                "Invalid date (YYYY-MM-DD)", "This feild can not be blank!!", Regex.dateUpdateRegex));
                        this.showAll();
                        break;
                    case 3:
                        p.setExpirationDate(Inputter.getString("Please input updated expiration date- ",
                                "Invalcid date (YYYY-MM-DD)", "This feild can not be blank!!", Regex.dateUpdateRegex));
                        this.showAll();
                        break;
                    case 4:
                        p.setQuantity(Inputter.getInteger("Please input new quantity- ", "Invalid value!!",
                                "Can not exceed 300", 0, 300));
                        this.showAll();
                        break;
                    case 5:
                        this.showAll();
                        System.out.println("SUCCESSFULLY UPDATED");
                        continues = false;
                        break;
                }
            }
        }
    }

    public void deleteProduct() {
        if (this.isEmpty()) {
            System.out.println("There is not any Product!!!");
        }
        this.showAll();
        while (true){
            String code = Inputter.getString("Please input Product code that you want to delete- ", "Invalid Product format (Pxxx)!!", "This feild can not be blank!!", Regex.ProductRegex);
            if(isExist(code)){
                Product pr = this.getProductBycode(code);
                this.remove(pr);
                this.showAll();
                System.out.println("The Product " + code + " has been deleted successfully");              
                break;
            }
            System.out.println("There is not any product with this input code!!!");
        }
        
    }

    public void expiredProduct() {
        ArrayList<Product> list = new ArrayList<>();
        
        for (Product product : this) {
            // Re-format date input
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate enteredExpDate = LocalDate.parse(product.getExpirationDate(), formatter);
            // lấy thời gian thực
            LocalDate currentDate = LocalDate.now();
            LocalDate enteredManuDate = LocalDate.parse(product.getManufacturingDate(), formatter);

            if (enteredExpDate.isBefore(currentDate) || enteredManuDate.isAfter(enteredExpDate)) {
                list.add(product);
            }
        }
        
        showAllList(list);
        
    }

    public void sellingProduct() {
        ArrayList<Product> list = new ArrayList<>();
        
        for (Product product : this) {
            // Re-format date input
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate enteredDate = LocalDate.parse(product.getExpirationDate(), formatter);
            // lấy thời gian thực
            LocalDate currentDate = LocalDate.now();

            if ((enteredDate.isAfter(currentDate) || enteredDate.isEqual(currentDate)) && product.getQuantity() > 0) {
                list.add(product);
            }
        }
        
        showAllList(list);
    }

    public void outOfStockProduct() {
        Collections.sort(this, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getQuantity() - p2.getQuantity(); // sort Product theo quantity và ascending
            }
        });
        ArrayList<Product> list = new ArrayList<>();
        for (Product product : this) {
            if (product.getQuantity() <= 3) {
                list.add(product);
            }
        }
        
        showAllList(list);
    }

    public void loadProductDataFromFile(String fname) {
        // clear current list before loading codes
        if (this.size() > 0) {
            this.clear();
        }
        try {
            File f = new File(fname); // checking the file
            if (!f.exists()) {
                System.out.println("There is not any file to load");
                return;
            }
            FileInputStream fi = new FileInputStream(f); // read()
            ObjectInputStream fo = new ObjectInputStream(fi); // read Object()
            Product p;
            while ((p = (Product) (fo.readObject())) != null) {
                this.add(p);
            }
            fo.close();
            fi.close();
        } catch (EOFException e) {
            System.out.println("Notice: Load Product.dat Successfully !");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception: File Error");
            System.out.println(e);
        }
    }

    // save the list to file
    public void saveProductDataToFile(String fname) {
        if (this.isEmpty()) {
            System.out.println("Empty list.");
            return;
        }
        try {
            FileOutputStream f = new FileOutputStream(fname); // write()
            ObjectOutputStream fo = new ObjectOutputStream(f); // writeObject()
            for (Product product : this) {
                fo.writeObject(product);
            }
            fo.close();
            f.close();
            System.out.println("Exception: Save to product.dat Successfully !");
        } catch (IOException e) {
            System.out.println("Exception: File Error !");
            // System.out.println(e);
        }
    }

    void add(ArrayList<Product> value) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }
}