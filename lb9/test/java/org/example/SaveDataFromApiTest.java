package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class SaveDataFromApiTest {
    private SaveDataFromApi saveDataFromApi;

    @BeforeEach
    public void setUp() {
        saveDataFromApi = new SaveDataFromApi();
    }

    @Test
    public void testFetchDataFromEndpoint() {
        String endpoint = "https://fakestoreapi.com/products";
        List<String> data = saveDataFromApi.fetchDataFromEndpoint(endpoint);

        assertNotNull(data);
        assertFalse(data.isEmpty());
    }

    @Test
    public void testSaveDataToBook() {
        List<String> endpoints = Arrays.asList(
                "https://fakestoreapi.com/products",
                "https://fakestoreapi.com/users"
        );

        String fileName = "TestOutput.xlsx";
        saveDataFromApi.saveDataToBook(endpoints, fileName);
    }

}