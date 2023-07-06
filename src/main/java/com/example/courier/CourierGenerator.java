package com.example.courier;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public Courier generic() {
        return new Courier("Tester", "P@ssw0rd123", "Ivan");
    }
    public Courier random() {
        return new Courier(RandomStringUtils.randomAlphanumeric(5, 10), "P@ssw0rd123", "Ivan");
    }
}
