package com.github.ryneal.techtest.repository.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.ryneal.techtest.model.Person;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.io.Files.asCharSource;
import static com.google.common.io.Files.newWriter;

@Component
public class PersonInputOutputUtil {

    private static File DATA_FILE = new File("data.txt");

    public List<Person> readDataFile() {
        try {
            String dataString = asCharSource(DATA_FILE, Charsets.UTF_8).read();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(dataString,
                    TypeFactory.defaultInstance().constructCollectionType(List.class,
                            Person.class));
        } catch (IOException e) {
            return null;
        }
    }

    public void writeToDataFile(List<Person> list) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String valueAsString = mapper.writeValueAsString(list);
            Files.write(valueAsString.getBytes(), DATA_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
