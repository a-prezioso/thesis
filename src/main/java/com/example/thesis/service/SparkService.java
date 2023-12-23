package com.example.thesis.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SparkService {

    @Autowired
    private SparkSession sparkSession;

    public void processSingleDataWithSpark() {
            // Lettura del file JSON in un DataFrame
            Dataset<Row> studentsDf = sparkSession.read().option("multiline", "true").json("data/students1.json");


        // Visualizzazione dei dati
            System.out.println("Dati originali:");
            studentsDf.show();

            // Selezionare specifiche colonne
            System.out.println("Solo nome e corso di laurea:");
            studentsDf.select("name", "major").show();

            // Filtrare i dati
            System.out.println("Studenti con età superiore ai 21 anni:");
            studentsDf.filter("age > 21").show();

            // Ordinare i dati
            System.out.println("Studenti ordinati per età:");
            studentsDf.sort("age").show();

            // Raggruppare e aggregare dati
            System.out.println("Numero di studenti per corso di laurea:");
            studentsDf.groupBy("major").count().show();

            // Creare una nuova colonna
            System.out.println("Età degli studenti l'anno prossimo:");
            studentsDf.withColumn("age_next_year", functions.col("age").plus(1)).show();
    }

    public void processSingleDataWithoutSpark() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> students = objectMapper.readValue(new File("data/students1.json"), new TypeReference<>() {});

        // Display original data
        System.out.println("Dati originali:");
        students.forEach(System.out::println);

        // Select specific columns
        System.out.println("\nSolo nome e corso di laurea:");
        students.stream()
            .map(student -> Map.of("name", student.get("name"), "major", student.get("major")))
            .forEach(System.out::println);

        // Filter data
        System.out.println("\nStudenti con età superiore ai 21 anni:");
        students.stream()
            .filter(student -> (int) student.get("age") > 21)
            .forEach(System.out::println);

        // Sort data
        System.out.println("\nStudenti ordinati per età:");
        students.stream()
            .sorted((s1, s2) -> Integer.compare((int) s1.get("age"), (int) s2.get("age")))
            .forEach(System.out::println);

        // Group and aggregate data
        System.out.println("\nNumero di studenti per corso di laurea:");
        Map<String, Long> countByMajor = students.stream()
            .collect(Collectors.groupingBy(student -> (String) student.get("major"), Collectors.counting()));
        countByMajor.forEach((major, count) -> System.out.println(major + ": " + count));

        // Create a new column
        System.out.println("\nEtà degli studenti l'anno prossimo:");
        students.stream()
            .map(student -> Map.of("name", student.get("name"), "age_next_year", (int) student.get("age") + 1))
            .forEach(System.out::println);
    }

    public void processMultipleDataWithSpark() {
        // Lettura del file JSON in un DataFrame
        String[] filePaths = generateFilePaths(20000);
        Dataset<Row> studentsDf = sparkSession.read().option("multiline", "true").json(filePaths);


        // Visualizzazione dei dati
        System.out.println("Dati originali:");
        studentsDf.show();

        // Selezionare specifiche colonne
        System.out.println("Solo nome e corso di laurea:");
        studentsDf.select("name", "major").show();

        // Filtrare i dati
        System.out.println("Studenti con età superiore ai 21 anni:");
        studentsDf.filter("age > 21").show();

        // Ordinare i dati
        System.out.println("Studenti ordinati per età:");
        studentsDf.sort("age").show();

        // Raggruppare e aggregare dati
        System.out.println("Numero di studenti per corso di laurea:");
        studentsDf.groupBy("major").count().show();

        // Creare una nuova colonna
        System.out.println("Età degli studenti l'anno prossimo:");
        studentsDf.withColumn("age_next_year", functions.col("age").plus(1)).show();
    }

    public void processMultipleDataWithoutSpark() throws IOException {

        for (int i = 1; i <= 20000; i++) {
            File file = new File("data/students" + i + ".json");
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> students = objectMapper.readValue(file, new TypeReference<>() {});


            // Display original data
            System.out.println("Dati originali:");
            students.forEach(System.out::println);

            // Select specific columns
            System.out.println("\nSolo nome e corso di laurea:");
            students.stream()
                .map(student -> Map.of("name", student.get("name"), "major", student.get("major")))
                .forEach(System.out::println);

            // Filter data
            System.out.println("\nStudenti con età superiore ai 21 anni:");
            students.stream()
                .filter(student -> (int) student.get("age") > 21)
                .forEach(System.out::println);

            // Sort data
            System.out.println("\nStudenti ordinati per età:");
            students.stream()
                .sorted((s1, s2) -> Integer.compare((int) s1.get("age"), (int) s2.get("age")))
                .forEach(System.out::println);

            // Group and aggregate data
            System.out.println("\nNumero di studenti per corso di laurea:");
            Map<String, Long> countByMajor = students.stream()
                .collect(Collectors.groupingBy(student -> (String) student.get("major"), Collectors.counting()));
            countByMajor.forEach((major, count) -> System.out.println(major + ": " + count));

            // Create a new column
            System.out.println("\nEtà degli studenti l'anno prossimo:");
            students.stream()
                .map(student -> Map.of("name", student.get("name"), "age_next_year", (int) student.get("age") + 1))
                .forEach(System.out::println);
        }

    }

    private String[] generateFilePaths(int numberOfFiles) {
        String[] filePaths = new String[numberOfFiles];
        for (int i = 0; i <= numberOfFiles-1; i++) {
            filePaths[i] = "data/students" + (i + 1) + ".json";
        }
        return filePaths;
    }

}
