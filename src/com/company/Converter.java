package com.company;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Converter
{
    public static void convert(String currency_from, String currency_to, double amount) throws IOException
    {
        double exchangeRate;
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
            String converted_msg = String.format("%s%.2f %s is equal to %s%.2f %s.",currency_from_symbol,amount,currency_from_name,currency_to_symbol,converted_amount,currency_to_name);

            System.out.println(convert_msg);
            System.out.println(converted_msg);

        }

    }

    public static String validateCurrency(Scanner sc, Map<String, String> currencies){
        boolean correctCurrency = false;
        String currency = "";

        while(!correctCurrency)
        {
            System.out.println("Please, type in which currency you want to convert FROM:");
            currency = sc.nextLine().toUpperCase();
            String check = currencies.get(currency);
            if (check == null)
            {
                System.out.println("Invalid input. Please type a valid currency.");
            } else
            {
                correctCurrency = true;
            }
        }
        return currency;
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

    public static double validateAmount(Scanner sc){
        boolean correctAmount = false;
        String amountInput;
        double amount = 0;
        BigDecimal checkamount = BigDecimal.valueOf(0);
        int decimal_digits_amount = 0;


        while(!correctAmount)
        {
            // Must have <= 2 decimal_digits
            // 100.00000 <- Double.parseDouble(amountInput) converts to 100.0
            System.out.println("Please, insert the amount you want to convert using a comma (,) for decimal ( max 2 decimal digits ) : ");
            amountInput = sc.nextLine();
            amountInput = amountInput.replaceAll(",",".");
            if (amountInput.matches("[0-9]+"))
            {
                amount = Double.parseDouble(amountInput);
                checkamount = BigDecimal.valueOf(amount);
                decimal_digits_amount = checkamount.scale(); // how many decimal digits after "."/

                ;
                if (amount <= 0 || decimal_digits_amount > 2)
                {
                    System.out.println("Invalid input. Please type a valid amount");
                } else
                {
                    correctAmount = true;
                }
            } else
            {
                System.out.println("Invalid input. Only numbers are allowed.");
            }

        }

        return amount;
    }

    public static boolean exitCheck(Scanner sc){
        boolean exit = false;
        boolean correctInput = false;
        String exitInput = "";
        List<String> validOptions = Arrays.asList("Y","N") ;
        
        // check input
        while(!correctInput)
        {
            System.out.println("Do you want to convert again? Type Y for Yes, N for no:");
            exitInput = sc.nextLine().toUpperCase();

            if (!validOptions.contains(exitInput))
            {
                System.out.println("Invalid input. Please type Y or N.");
            } else
            {
                correctInput = true;
            }
            
        }

        if (exitInput.equals(validOptions.get(1))) // No, wants to quit
        {
                exit = true;
        }
        // no need to check for N input

        return exit;
    }
}


