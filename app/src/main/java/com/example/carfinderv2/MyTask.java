package com.example.carfinderv2;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MyTask extends AsyncTask<Object, Void, String> {

    private static final String URI = "https://www.otomoto.pl/osobowe/seg-compact/od-2013/?search%5Bfilter_float_price%3Afrom%5D=35000&search%5Bfilter_float_price%3Ato%5D=45000&search%5Bfilter_float_mileage%3Ato%5D=100000&search%5Bfilter_float_engine_capacity%3Afrom%5D=1500&search%5Bfilter_enum_fuel_type%5D%5B0%5D=petrol&search%5Bfilter_float_engine_power%3Afrom%5D=120&search%5Bfilter_enum_damaged%5D=0&search%5Bfilter_enum_country_origin%5D%5B0%5D=pl&search%5Bfilter_enum_original_owner%5D=1&search%5Bfilter_enum_no_accident%5D=1&search%5Border%5D=created_at_first%3Adesc&search%5Bbrand_program_id%5D%5B0%5D=&search%5Bcountry%5D=";

    CarFinder activity;
    Context context;
    private CheckerProcessor checkerProcessor;

    public MyTask(Context context)
    {
        this.context = context;
        checkerProcessor = new CheckerProcessor(context);
    }

    @Override
    protected String doInBackground(Object... params) {
        activity = (CarFinder)params[0];
        try {
            StringBuilder sb = new StringBuilder();
            URL url = new URL(URI);

            BufferedReader in;
            in = new BufferedReader(
                    new InputStreamReader(
                            url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                sb.append(inputLine);

            in.close();

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String str) {
        //Do something with result string
//        Document doc = Jsoup.parse(str, "", Parser.xmlParser());
//        Element resulsts = doc.getElementById("offers");

        Pattern pattern = Pattern.compile("\"result_count\":\\d{1,3},");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
        {
            String test = matcher.group(0);
            String requiredString = test.substring(test.indexOf(":") + 1, test.indexOf(","));
            Integer carAmount = Integer.valueOf(requiredString);
            carAmount.byteValue();

            checkerProcessor.checkForNewCar(carAmount);
        }

        System.out.println(str);
//            WebView webView = activity.findViewById(R.id.we);
//            webView.loadData(str, "text/html; charset=UTF-8", null);
    }

}