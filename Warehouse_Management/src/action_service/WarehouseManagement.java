/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import menuGUI.MainMenu;
import tool.Inputter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WarehouseManagement {

    static int i = 1;
    static int e = 1;
    HashMap<ReceiptHead, ArrayList<ReceiptItem>> importMap = new HashMap<>();
    HashMap<ReceiptHead, ArrayList<ReceiptItem>> exportMap = new HashMap<>();
    public ProductManagement pm = new ProductManagement();
    public ReceiptManagement rm = new ReceiptManagement();

    public WarehouseManagement() {
    }

    public void showReceipt(HashMap<ReceiptHead, ArrayList<ReceiptItem>> map) {
        for (Map.Entry<ReceiptHead, ArrayList<ReceiptItem>> entry : map.entrySet()) {
            entry.getKey().printReceiptHead();
            ReceiptManagement temp = new ReceiptManagement();
            for (ReceiptItem receiptItem : entry.getValue()) {
                temp.add(receiptItem);
            }
            temp.showAll();
        }
    }

    public void printProductReceipt() {
        pm.showAll();

        String productCode = Inputter.getString("Please in put the CODE of the ReceiptItem", "This can not be blank!!");

        for (Map.Entry<ReceiptHead, ArrayList<ReceiptItem>> entry : importMap.entrySet()) { // check import receipt
            for (ReceiptItem receiptItem : entry.getValue()) {
                if (receiptItem.getCode() == null ? productCode == null : receiptItem.getCode().equals(productCode)) {
                    entry.getKey().printReceiptHead();
                    receiptItem.showInfo();
                }
            }
        }

        for (Map.Entry<ReceiptHead, ArrayList<ReceiptItem>> entry : exportMap.entrySet()) { // check export receipt
            for (ReceiptItem receiptItem : entry.getValue()) {
                if (receiptItem.getCode() == null ? productCode == null : receiptItem.getCode().equals(productCode)) {
                    entry.getKey().printReceiptHead();
                    receiptItem.showInfo();
                }
            }
        }
    }

    public boolean isReceiptCodeExists(String receiptCode, HashMap<ReceiptHead, ArrayList<ReceiptItem>> map) {
        for (ReceiptHead receiptHead : map.keySet()) {
            if (receiptHead.getReceiptCode().equals(receiptCode)) {
                return true;
            }
        }
        return false;
    }

    public void createImportReceipt() { // Import receiptItem trong kho
        Boolean continues = true;
        ArrayList<ReceiptItem> receiptList1 = new ArrayList<>();
        ReceiptHead rh = new ReceiptHead();
        

        String code = String.format("%07d", i++);

        rh.setReceiptType("Import");
        rh.setReceiptCode(code);

        while (continues) {

            pm.showAll();
            MainMenu mn = new MainMenu();

            for (Product product : pm) {
                mn.addOptions(product.getCode());
            }
            System.out.println("Choose which receiptItem to import");
            mn.printMenu();
            int m = mn.getOption();

            int tempQuantity;
            tempQuantity = Inputter.getInteger("Please input import quantity of item " + pm.get(m - 1).getCode(), "invalid import number", "invalid number", 0, 1000);
            pm.getProductBycode(pm.get(m - 1).getCode()).setQuantity(tempQuantity + pm.getProductBycode(pm.get(m - 1).getCode()).getQuantity());

            String tempCode = pm.get(m - 1).getCode();
            String tempName = pm.getProductBycode(pm.get(m - 1).getCode()).getName();
            String tempManuDate = pm.getProductBycode(pm.get(m - 1).getCode()).getManufacturingDate();
            String tempExpDate = pm.getProductBycode(pm.get(m - 1).getCode()).getExpirationDate();
            
            ReceiptItem existedImportProduct = new ReceiptItem(tempCode, tempName, tempManuDate, tempExpDate, tempQuantity, "Updated...");

            receiptList1.add(existedImportProduct);

            System.out.println("Successfully added!!");

            continues = Inputter.getBoolean("Do you want to continues adding item to import receipt (yes/no)-",
                    "Invalid value!!");
        }

        importMap.put(rh, receiptList1);
        showReceipt(importMap);
        System.out.println("Returning to main menu...");
    }

    public void createExportReceipt() { // export phai co san trong kho

        Boolean continues = true;
        ArrayList<ReceiptItem> receiptList = new ArrayList<>();
        ReceiptHead rh2 = new ReceiptHead();

        String code = String.format("%07d", e++);

        rh2.setReceiptType("Export");
        rh2.setReceiptCode(code);

        while (continues) {
            pm.showAll();
            MainMenu mn = new MainMenu();

            for (Product product : pm) {
                mn.addOptions(product.getCode());
            }
            System.out.println("Choose which receiptItem to export");
            mn.printMenu();
            int m = mn.getOption();

            int tempQuantity;

            tempQuantity = Inputter.getInteger("Please input export quantity of ReceiptItem " + pm.get(m - 1).getCode(),
                    "invalid import number", "invalid number", 0, 1000);
            pm.getProductBycode(pm.get(m - 1).getCode())
                    .setQuantity(pm.getProductBycode(pm.get(m - 1).getCode()).getQuantity() - tempQuantity);

            String tempCode = pm.get(m - 1).getCode();
            String tempName = pm.getProductBycode(pm.get(m - 1).getCode()).getName();
            String tempManuDate = pm.getProductBycode(pm.get(m - 1).getCode()).getManufacturingDate();
            String tempExpDate = pm.getProductBycode(pm.get(m - 1).getCode()).getExpirationDate();
            ReceiptItem existedImportProduct = new ReceiptItem(tempCode, tempName, tempManuDate, tempExpDate,
                    tempQuantity, "Updated...");

            receiptList.add(existedImportProduct);

            System.out.println("Successfully added!!");

            continues = Inputter.getBoolean("Do you want to continues adding ReceiptItem to export receipt (yes/no)-",
                    "Invalid value!!");
        }
        exportMap.put(rh2, receiptList);
        showReceipt(exportMap);
        System.out.println("Returning to main menu...");
    }

    public HashMap<ReceiptHead, ArrayList<ReceiptItem>> mergeMaps() {
        HashMap<ReceiptHead, ArrayList<ReceiptItem>> mergedMap = new HashMap<>(importMap);

        for (Map.Entry<ReceiptHead, ArrayList<ReceiptItem>> entry : exportMap.entrySet()) {
            ReceiptHead key = entry.getKey();
            ArrayList<ReceiptItem> value = entry.getValue();

            // If the key already exists in the mergedMap, merge the ArrayLists
            if (mergedMap.containsKey(key)) {
                ArrayList<ReceiptItem> existingProducts = mergedMap.get(key);
                existingProducts.addAll(value); // Or perform any other merging logic as needed
            } else {
                // If the key does not exist in mergedMap, simply put it there
                mergedMap.put(key, value);
            }
        }
        return mergedMap;

        // Now, mergedMap contains the merged results.
        // You can proceed with using the mergedMap as necessary.
    }

    public String mapToString(HashMap<ReceiptHead, ArrayList<ReceiptItem>> map) {

        StringBuilder mapAsString = new StringBuilder();
        for (Map.Entry<ReceiptHead, ArrayList<ReceiptItem>> entry : map.entrySet()) {
            mapAsString.append(entry.getKey().toString()).append(":");
            ArrayList<ReceiptItem> receipts = entry.getValue();
            for (ReceiptItem receiptItem : receipts) {
                mapAsString.append(receiptItem.toString()).append(";");
            }
            // Remove the last ";" and append a newline to separate entries
            mapAsString.setLength(mapAsString.length() - 1);
            mapAsString.append("\n");
        }
        return mapAsString.toString();
    }

    public void saveToDAT() {
        try (FileWriter writer = new FileWriter("warehouse.dat")) {
            String importMapAsString = mapToString(mergeMaps());
            writer.write(importMapAsString);
        } catch (IOException e) {
            System.out.println("An error occurred while trying to save importMap to file");
            e.printStackTrace();
        }
        System.out.println("Notice: Save to warehouse.dat Successfully !");
    }

    public void saveWarehouseDataToDAT() {
        mapToString(mergeMaps());
        saveToDAT();
    }
}
