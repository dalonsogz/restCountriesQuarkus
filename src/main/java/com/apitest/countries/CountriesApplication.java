package com.apitest.countries;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CountriesApplication {

    public static void main(String[] args) {
        Quarkus.run(args);
    }
}