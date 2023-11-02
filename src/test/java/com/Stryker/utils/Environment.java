package com.Stryker.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {

    public static final String URL;
    public static final String DB_URL;
    public static final String DB_USERNAME;
    public static final String DB_PASSWORD;
    public static final String MEMBER_EMAIL;
    public static final String SHOP_BASE_URL;
    public static final String MEMBER_PASSWORD;
    public static final String CAREERS_PAGE_URL;
    public static final String APPIUM_SERVER_IP;
    public static final String INVESTORS_PAGE_URL;
    public static final String ID_PARAM_SCHEMA_PATH;
    public static final String INTERNATIONAL_PAGE_URL;
    public static final String ID_PATH_PARAM_ENDPOINT;
    public static final String ALL_CUSTOMERS_ENDPOINT;
    public static final String ALL_CUSTOMERS_SCHEMA_PATH;


    static {
        Properties properties = null;
        String environment = System.getProperty("environment") != null ? environment = System.getProperty("environment") : ConfigurationReader.getProperty("environment");
        //this field will get its value from configuration.properties file environment key /qa1 qa2 qa3
        //String environment = ConfigurationReader.getProperty("environment");


        try {
            //where is our file ?, path is holding that one
            String path = System.getProperty("user.dir") + "/src/test/resources/Environments/" + environment + ".properties";

            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ID_PATH_PARAM_ENDPOINT = properties.getProperty("idPathParamEndpoint");
        ALL_CUSTOMERS_ENDPOINT = properties.getProperty("endpointAllCustomers");
        ID_PARAM_SCHEMA_PATH = properties.getProperty("idParamSchemaPath");
        ALL_CUSTOMERS_SCHEMA_PATH = properties.getProperty("allCustomersSchemaPath");
        URL = properties.getProperty("stryker.url");
        APPIUM_SERVER_IP=properties.getProperty("appium.server.ip");
        SHOP_BASE_URL = properties.getProperty("shop.API.base.URI");
        CAREERS_PAGE_URL = properties.getProperty("strykerCareersPageUrl");
        INVESTORS_PAGE_URL = properties.getProperty("strykerInvestorsPageUrl");
        INTERNATIONAL_PAGE_URL = properties.getProperty("strykerInternationalPageUrl");
        DB_USERNAME = properties.getProperty("dbUsername");
        DB_PASSWORD = properties.getProperty("dbPassword");
        DB_URL = properties.getProperty("dbURL");
        MEMBER_EMAIL = properties.getProperty("");
        MEMBER_PASSWORD = properties.getProperty("");


    }

}
