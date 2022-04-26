package com.company;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // I'm going to create a function that takes in original_currency( currency I want to convert ),
    // amount X , destination_currency ( currency I want to convert to )
    // then, it will return X * rates ( rate between currencies ).

    // API I'm using in this project: https://www.currencyconverterapi.com/

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

        String currency_from;
        String currency_to;
        double amount;




        System.out.println("Hello, and welcome to my currency converter, made by Lucas Kikkawa Perpetua.\n");

        availableCurrencies(currencies);

        Scanner sc = new Scanner(System.in);

        System.out.println("Please, type in which currency you want to convert FROM: ");
        currency_from = sc.nextLine();

        System.out.println("Please, type in which currency you want to convert TO:");
        currency_to = sc.nextLine();

        System.out.println("Please, insert the amount you want to convert: ");
        amount = sc.nextDouble();

        converter(currency_from,currency_to,amount);
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

    private static void converter(String currency_from, String currency_to, double amount) throws IOException
    {
        double exchangeRate = 0;
        String currency_from_name = "";
        String currency_to_name = "";
        String currency_from_symbol = "";
        String currency_to_symbol = "";


        String API_ACCESS_KEY = "ef70f000b4a187533570";

        String GET_URL_CURRENCY = "https://free.currconv.com/api/v7/currencies" +
                "?apiKey=" + API_ACCESS_KEY;

        String GET_URL_CONVERT = "https://free.currconv.com/api/v7/convert" +
                "?apiKey=" + API_ACCESS_KEY +
                "&q=" + currency_from + "_" + currency_to +
                "&compact=ultra";

        // URL connection to get both currency_name and currency_symbol
        
        URL url_name_symbol = new URL(GET_URL_CURRENCY);

        HttpURLConnection name_symbol_httpURLConnection = (HttpURLConnection) url_name_symbol.openConnection();
        name_symbol_httpURLConnection.setRequestMethod("GET"); // requesting data
        int name_symbol_responseCode = name_symbol_httpURLConnection.getResponseCode();

        if (name_symbol_responseCode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(name_symbol_httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }in.close();

            JSONObject obj = new JSONObject(response.toString());
            currency_from_name= obj.getJSONObject("results").getJSONObject(currency_from).getString("currencyName");
            currency_from_symbol= obj.getJSONObject("results").getJSONObject(currency_from).getString("currencySymbol");
            currency_to_name= obj.getJSONObject("results").getJSONObject(currency_to).getString("currencyName");
            currency_to_symbol= obj.getJSONObject("results").getJSONObject(currency_to).getString("currencySymbol");
        }

        // URL connection to get exchangeRate


        URL url_convert = new URL(GET_URL_CONVERT);

        HttpURLConnection exchangeRate_httpURLConnection = (HttpURLConnection) url_convert.openConnection();
        exchangeRate_httpURLConnection.setRequestMethod("GET"); // requesting data
        int responseCode = exchangeRate_httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) // success stabilishing connection
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(exchangeRate_httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();


            // add requested data to buffer
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }in.close();

            JSONObject obj = new JSONObject(response.toString());
            exchangeRate = obj.getDouble(currency_from + "_" + currency_to);
            //System.out.println(exchangeRate);

            double converted_amount = amount * exchangeRate;

            String convert_msg = String.format("Converting %s%.2f (%s) to %s (%s)... \n",
                    currency_from_symbol,
                    amount,
                    currency_from_name,
                    currency_to,
                    currency_to_name);
            String converted_msg = String.format("%s%.2f is equal to %s%.2f.",currency_from_symbol,amount,currency_to_symbol,converted_amount);

            System.out.println(convert_msg);
            System.out.println(converted_msg);

        }

    }


}
