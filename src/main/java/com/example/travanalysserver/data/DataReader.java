package com.example.travanalysserver.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private final String filePath;

    public DataReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getListOfData() throws IOException {
        String tempLine;
        List<String> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            while ((tempLine = br.readLine()) != null) {
                dataList.add(tempLine);
            }
        } catch (Exception e) {
            System.out.println("Det fungerade ej!");
        }
        return dataList;
    }
}
