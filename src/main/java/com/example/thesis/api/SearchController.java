package com.example.thesis.api;

import com.example.thesis.service.SparkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SparkService sparkService;


    @GetMapping("/spark-single-search")
    public String searchSingleDataWithSpark() {
        long startTime = System.currentTimeMillis();
        sparkService.processSingleDataWithSpark();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with Spark: " + (endTime - startTime) + " ms");
        return endTime - startTime + " ms of search";
    }

    @GetMapping("/no-spark-single-search")
    public String searchSingleDataWithoutSpark() {
        try {
            long startTime = System.currentTimeMillis();
            sparkService.processSingleDataWithoutSpark();
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken with Spark: " + (endTime - startTime) + " ms");
            return endTime - startTime + " ms of search";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/spark-multiple-search")
    public String searchMultipleDataWithSpark() {
        long startTime = System.currentTimeMillis();
        sparkService.processMultipleDataWithSpark();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with Spark: " + (endTime - startTime) + " ms");
        return endTime - startTime + " ms of search";
    }

    @GetMapping("/no-spark-multiple-search")
    public String searchMultipleDataWithoutSpark() {
        try {
            long startTime = System.currentTimeMillis();
            sparkService.processMultipleDataWithoutSpark();
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken without Spark: " + (endTime - startTime) + " ms");
            return endTime - startTime + " ms of search";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
