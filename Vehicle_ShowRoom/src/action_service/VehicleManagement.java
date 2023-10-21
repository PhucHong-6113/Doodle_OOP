/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import menuGUI.MainMenu;
import tool.Inputter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author ASUS
 */
public class VehicleManagement extends ArrayList<Vehicle> implements IVehecleManagement {

    @Override
    public void addVehicle() {

        String tempID, tempName, tempColor, tempBrand, tempType, tempProductYear;
        int tempPrice;

        while (true) {
            if (!this.isEmpty()) {
                this.showAllList(this);
            }

            tempID = Inputter.getString("Please input new Vehicle ID: ", "Invalid Vehicle format (Vxxx)!!", "This feild can not be blank!!", Regex.VehicleRegex);
            if (isExist(tempID)) {
                System.out.println("Existed Vehicle!!");
            } else {
                break;
            }
        }

        tempName = Inputter.getName("Please input new Vehicle name: ", "This feild can not be blank!!", 1, 20);
        tempColor = Inputter.getString("Please inpute new Vehicle color: ", "This feild can not be blank!!");
        tempBrand = Inputter.getString("Please input new Vehicle brand: ", "This field can not be blank!!");
        tempType = Inputter.getString("Please input new Vehicle type: ", "This feild can not be blank!!");
        tempProductYear = Inputter.getString("Please input new Vehicle Year: ", "Invalid date (yyyy)", "This feild can not be blank!!", Regex.yearRegex);
        tempPrice = Inputter.getInteger("Please input new Vehicle price ", "Invalid value!!", "Can not exceed 1000000000", 0, 1000000000);

        Vehicle vehicle = new Vehicle(tempID.toUpperCase(), tempName, tempColor, tempBrand, tempType, tempProductYear, tempPrice);
        this.add(vehicle);

        System.out.println("SUCCESSFULLY ADDED");
        showAllList(this);

        if (Inputter.getBoolean("Do you want to add more? (yes/no)", "invalid value")) {
            addVehicle();
        }
    }

    @Override
    public void checkExisted() {
        ArrayList<Vehicle> tempList = loadFromFile("vehicle.dat");
        
        String ID = Inputter.getString("Please input Vehicle ID that you want to check- ", "Invalid Vehicle format (Pxxx)!!", "This feild can not be blank!!", Regex.VehicleRegex);
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getID_Vehicle().equalsIgnoreCase(ID)) {
                tempList.get(i).showInfo();
            }
        }
        System.out.println("No Vehicle Found!");  
    }

    @Override
    public void updateVehicle() {
        if (this.isEmpty()) {
            System.out.println("The list is empty!!!");
            return;
        }

        showAllList(this);

        String ID = Inputter.getString("Please input Vehicle ID that you want to update- ", "Invalid Vehicle format (Pxxx)!!", "This feild can not be blank!!", Regex.VehicleRegex);

        if (!isExist(ID)) {
            System.out.println("The Vehicle with the ID " + ID + " does not exist");
        } else {
            Vehicle v = this.getVehicleByID(ID);

            MainMenu mn = new MainMenu();
            mn.addOptions("Update Vehicle name");
            mn.addOptions("Update Vehicle color");
            mn.addOptions("Update Vehicle brand");
            mn.addOptions("Update Vehicle type");
            mn.addOptions("Update Vehicle year");
            mn.addOptions("Update Vehicle price");
            mn.addOptions("Exit to Show room menu");

            boolean continues = true;
            while (continues) {
                mn.printMenu();
                switch (mn.getOption()) {
                    case 1:
                        v.setName_Vehicle(Inputter.getName2("Please input new name- ", v.getName_Vehicle(), 1, 20));
                        showAllList(this);
                        break;
                    case 2:
                        v.setColor_Vehicle(Inputter.getString2("Please inpute new Vehicle color: ", v.getColor_Vehicle()));
                        showAllList(this);
                        break;
                    case 3:
                        v.setBrand_Vehicle(Inputter.getString2("Please input new Vehicle brand: ", v.getBrand_Vehicle()));
                        showAllList(this);
                        break;
                    case 4:
                        v.setType(Inputter.getString2("Please input new Vehicle type: ", v.getType()));
                        showAllList(this);
                        break;
                    case 5:
                        v.setProductYear(Inputter.getString2("Please input new Vehicle Year: ", "Invalid date (yyyy)", v.getProductYear(), Regex.yearRegex));
                        showAllList(this);
                        break;
                    case 6:
                        v.setPrice_Vehicle(Inputter.getInteger("Please input new Vehicle Price ", "Invalid value!!", "Can not exceed 1000000000", 0, 1000000000));
                        showAllList(this);
                        break;
                    case 7:
                        showAllList(this);
                        System.out.println("SUCCESSFULLY UPDATED");
                        continues = false;
                        break;
                }
            }
        }
    }

    @Override
    public void deleteVehicle() {

        if (this.isEmpty()) {
            System.out.println("There is not any Vehicle in the show room!!!");
            return;
        }

        showAllList(this);
        while (true) {
            String ID = Inputter.getString("Please input Vehicle ID that you want to delete- ", "Invalid Vehicle format (Vxxx)!!", "This feild can not be blank!!", Regex.VehicleRegex);
            Boolean confirm = Inputter.getBoolean("Proceed to delete this Vehicle?", "Invalid value!!");

            if (!confirm) {
                System.out.println("Fail to delete Vehicle " + ID + " because of cancelation");
                break;
            }

            if (isExist(ID)) {
                Vehicle pr = this.getVehicleByID(ID);
                this.remove(pr);
                this.showAllList(this);
                System.out.println("The Vehicle " + ID + " has been deleted successfully");
                break;
            }
            System.out.println("There is not any Vehicle with this input ID!!!");
        }
    }

    @Override
    public void search() {
        MainMenu mn = new MainMenu();

        mn.addOptions("Search Vehicle by Name");
        mn.addOptions("Search Vehicle by ID");

        while (true) {
            mn.printMenu();
            switch (mn.getOption()) {
                case 1:
                    String tempName = Inputter.getName("Please input searching Vehicle name: ", "This feild can not be blank!!", 1, 20);
                    printTableHead();
                    int i = 1;
                    for (Vehicle vehicle : this) {
                        if (vehicle.getName_Vehicle().equalsIgnoreCase(tempName)) {
                            String str = String.format(" %3s |", i++);
                            System.out.println(str + vehicle.getInfo());
                        }
                    }
                    break;
                case 2:
                    String tempID = Inputter.getString("Please input searching Vehicle ID: ", "Invalid Vehicle format (Vxxx)!!", "This feild can not be blank!!", Regex.VehicleRegex);
                    printTableHead();
                    int o = 1;
                    for (Vehicle vehicle : this) {
                        if (vehicle.getID_Vehicle().equalsIgnoreCase(tempID)) {
                            String str = String.format(" %3s |", o++);
                            System.out.println(str + vehicle.getInfo());
                        }
                    }
                    break;
            }
            break;
        }
    }

    @Override
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("There is not any list");
        } else {
            {
                printTableHead();
                int i = 1;
                for (Vehicle vehicle : this) {
                    String str = String.format(" %3s |", i++);
                    System.out.println(str + vehicle.getInfo());
                }
            }
        }
    }

    public Vehicle getVehicleByID(String ID) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID_Vehicle().equalsIgnoreCase(ID)) {
                return this.get(i);
            }
        }
        return null;
    }

    public int getIndexByID(String ID) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID_Vehicle().equalsIgnoreCase(ID)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isExist(String ID) {
        for (Vehicle vehicle : this) {
            if (vehicle.getID_Vehicle().equalsIgnoreCase(ID)) {
                return true;
            }
        }
        return false;
    }

    public void printTableHead() {
        System.out.format(" %-3s | %6s | %10s | %10s | %10s | %10s | %15s | %7s\n", "No.", "ID", "Name", "Color", "Brand", "Type", "Vehicle Year", "Price($)");
    }

    public void showAllList(ArrayList<Vehicle> list) {
        if (this.isEmpty()) {
            System.out.println("There is not any list");
        } else {
            {
                printTableHead();
                int i = 1;
                for (Vehicle vehicle : list) {
                    String str = String.format(" %3s |", i++);
                    System.out.println(str + vehicle.getInfo());
                }
            }
        }
    }

    @Override
    public void displayVehicle() {
        MainMenu mn = new MainMenu();

        mn.addOptions("Display all");
        mn.addOptions("Display Price");

        while (true) {
            mn.printMenu();
            switch (mn.getOption()) {
                case 1:
                    showAll();
                    break;
                case 2:
                    ArrayList<Vehicle> tempList = new ArrayList<>(this);
                    Collections.sort(tempList, new Comparator<Vehicle>() {
                        @Override
                        public int compare(Vehicle p1, Vehicle p2) {
                            return p2.getPrice_Vehicle() - p1.getPrice_Vehicle(); // sort Product theo quantity và ascending
                        }
                    });
                    showAllList(tempList);
            }
            break;
        }
    }

    @Override
    public void printVehicle() {
        ArrayList<Vehicle> tempList;
        MainMenu mn = new MainMenu();

        mn.addOptions("Print all");
        mn.addOptions("Print by Year");

        while (true) {
            mn.printMenu();
            switch (mn.getOption()) {
                case 1:
                    tempList = loadFromFile("vehicle.dat");
                    showAllList(tempList);
                    break;
                case 2:
                    tempList = loadFromFile("vehicle.dat");

                    Collections.sort(tempList, new Comparator<Vehicle>() {
                        @Override
                        public int compare(Vehicle o1, Vehicle o2) {
                            return o2.getProductYear().compareToIgnoreCase(o1.getProductYear()); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                    showAllList(tempList);                   
            }
            break;
        }
    }

    @Override
    public void saveToFile(String filename) {
        if (this.isEmpty()) {
            System.out.println("Empty list.");
            return;
        }
        try {
            FileOutputStream f = new FileOutputStream(filename); // write()
            ObjectOutputStream fo = new ObjectOutputStream(f); // writeObject()
            for (Vehicle vehicle : this) {
                fo.writeObject(vehicle);
            }
            fo.close();
            f.close();
            System.out.println("Save to vehicle.dat Successfully !");
        } catch (IOException e) {
            System.out.println("Exception: File Error !");
            // System.out.println(e);
        }
    }

    @Override
    public ArrayList<Vehicle> loadFromFile(String filename) {
        ArrayList<Vehicle> tempList = new ArrayList<>();
        
        try {
            File f = new File(filename); // checking the file
            if (!f.exists()) {
                System.out.println("There is not any file to load");
                return null;
            }
            FileInputStream fi = new FileInputStream(f); // read()
            ObjectInputStream fo = new ObjectInputStream(fi); // read Object()
            Vehicle p;
            
            while ((p = (Vehicle) (fo.readObject())) != null) {
                tempList.add(p);
            }
            fo.close();
            fi.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception: File Error");
            System.out.println(e);
        }
        return tempList;
    }
}
