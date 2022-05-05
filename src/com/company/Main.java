package com.company;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.company.Converter.*;

public class Main
{

    // I'm going to create a function that takes in original_currency( currency I want to convert ),
    // amount X , destination_currency ( currency I want to convert to )
    // then, it will return X * rates ( rate between currencies ).

    // API I'm using in this project: https://www.currencyconverterapi.com/


    /*
        02/05/2022

        Create a while loop so the user can do multiple conversions
        Check user inputs (String, doubles)
        Uppercase all string inputs

        To-do

        Done
        validate amount input (check if it has comma (,) and 2 decimals after comma
        fix exit/repeat flow

        Several refactor - change main parts into methods


     */

    public static void main(String[] args) throws IOException
    {

        // Add currency code and name

        Map<String, String> currencies = new HashMap<>();
        currencies.put("BRL", "Brazilian Real");
        currencies.put("USD", "United States Dollar");
        currencies.put("CAD", "Canadian Dollar");
        currencies.put("AUD", "Australian Dollar");
        currencies.put("JPY", "Japanese Yen");
        currencies.put("ARS", "Argentine Peso");
        currencies.put("CNY", "Chinese Yuan");
        currencies.put("MXN", "Mexican Peso");
        currencies.put("HKD", "Hong Kong Dollar");
        currencies.put("KRW", "South Korean Won");

        String currency_from;
        String currency_to;
        double amountUser; // amount >= 0 && amount must have 2 decimal points
        boolean quit = false;


        System.out.println("Hello, and welcome to my currency converter, made by Lucas Kikkawa Perpetua.\n");

        availableCurrencies(currencies);

        Scanner sc = new Scanner(System.in);


        while (!quit)
        {
            currency_from = validateCurrency(sc, currencies);
            currency_to = validateCurrency(sc, currencies);
            amountUser = validateAmount(sc);

            convert(currency_from, currency_to, amountUser);

            quit = exitCheck(sc); // quit = true, exits while loop - false, stay in while loop

        }
        System.out.println("Thanks for using my currency converter! See you soon!");
    }
}
