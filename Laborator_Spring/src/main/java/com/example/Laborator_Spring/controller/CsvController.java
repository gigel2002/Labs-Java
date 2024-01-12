package com.example.Laborator_Spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CsvController {

    private static final String CSV_FILE = "controller/vegetables.csv";

    private List<String[]> allData = readCsv();

    private List<String[]> readCsv() {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @GetMapping("/getElement")
    public ResponseEntity<String[]> getElement(@RequestParam int index) {
            return ResponseEntity.ok(allData.get(index));
    }

    @PostMapping("/addElements")
    public ResponseEntity<String> addElements(@RequestBody List<String[]> elements) {
        allData.addAll(elements);
        writeCsv();
        return ResponseEntity.status(HttpStatus.CREATED).body("Elements added successfully.");
    }

    @DeleteMapping("/deleteElement")
    public ResponseEntity<String> deleteElement(@RequestParam int index) {
            allData.remove(index);
            writeCsv();
            return ResponseEntity.ok("Element deleted successfully.");
    }

    @PutMapping("/updateElement")
    public ResponseEntity<String> updateElement(@RequestParam int index, @RequestBody String[] newData) {
            allData.set(index, newData);
            writeCsv();
            return ResponseEntity.ok("Element updated successfully.");
    }

    private void writeCsv() {
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            for (String[] rowData : allData) {
                writer.write(String.join(",", rowData) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
