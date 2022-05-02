package com.company;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.company.Converter.convert;

public class Main {

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

        validate amount input (check if it has comma (,) and 2 decimals after comma

     */

    public static void main(String[] args) throws IOException
    {

        // Add currency code and name

        Map<String,String> currencies = new HashMap<>();
        currencies.put("BRL", "Brazilian Real");
        currencies.put("USD","United States Dollar");
        currencies.put("CAD","Canadian Dollar");
        currencies.put("AUD","Australian Dollar");
        currencies.put("JPY","Japanese Yen");
        currencies.put("ARS","Argentine Peso");
        currencies.put("CNY","Chinese Yuan");
        currencies.put("MXN","Mexican Peso");
        currencies.put("HKD","Hong Kong Dollar");
        currencies.put("KRW","South Korean Won");

        String currency_from = "";
        String currency_to = "";
        String exitInput;
        String amountInput;
        double amount = 0; // amount >= 0 && amount must have 2 decimal points
        boolean exit = false;
        boolean validateCurrencyFrom = false;
        boolean validateCurrencyTo = false;
        boolean validateAmount = false;



        System.out.println("Hello, and welcome to my currency converter, made by Lucas Kikkawa Perpetua.\n");

        availableCurrencies(currencies);

        Scanner sc = new Scanner(System.in);

        while(!exit)
        {
            while(!validateCurrencyFrom)
            {
                System.out.println("Please, type in which currency you want to convert FROM:");
                currency_from = sc.nextLine().toUpperCase();
                String check = currencies.get(currency_from);
                if (check == null)
                {
                    System.out.println("Invalid input. Please type a valid currency.");
                } else
                {
                    validateCurrencyFrom = true;
                }
            }

            while(!validateCurrencyTo)
            {
                System.out.println("Please, type in which currency you want to convert TO:");
                currency_to = sc.nextLine().toUpperCase();
                String check = currencies.get(currency_to);
                if(check == null)
                {
                    System.out.println("Invalid input. Please type a valid currency.");
                } else
                {
                    validateCurrencyTo = true;
                }
            }

            while(!validateAmount)
            {
                System.out.println("Please, insert the amount you want to convert using a comma (,) for cents : ");
                amountInput = sc.nextLine();
                amount = Double.parseDouble(amountInput);
                if (amount <= 0.00)
                {
                    System.out.println("Invalid input. Please type a valid amount");
                } else
                {
                    validateAmount = true;
                }

            }
            convert(currency_from, currency_to, amount);

            System.out.println("Do you want to convert again? Type Y for Yes, N for no:");
            exitInput = sc.nextLine().toUpperCase();

            if (exitInput.equals("Y"))
            {
                continue;
            } else if (exitInput.equals("N"))
            {
                exit = true;
                sc.close();
            }


        }

        System.out.println("Thanks for using my currency converter! See you soon!");
    }


    public static void availableCurrencies(Map<String,String> map)
    {
        System.out.println("These are my currency converter's available currencies for now.\n");
        for (Map.Entry<String,String> entry : map.entrySet())
        {
            System.out.println(String.format("%s (%s)",entry.getKey(),entry.getValue()));
        }
        System.out.println();
    }



}
