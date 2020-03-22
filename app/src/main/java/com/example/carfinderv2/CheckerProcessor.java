package com.example.carfinderv2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

class CheckerProcessor {

    private static final String URI = "https://www.otomoto.pl/osobowe/seg-compact/od-2013/?search%5Bfilter_float_price%3Afrom%5D=35000&search%5Bfilter_float_price%3Ato%5D=45000&search%5Bfilter_float_mileage%3Ato%5D=100000&search%5Bfilter_float_engine_capacity%3Afrom%5D=1500&search%5Bfilter_enum_fuel_type%5D%5B0%5D=petrol&search%5Bfilter_float_engine_power%3Afrom%5D=120&search%5Bfilter_enum_damaged%5D=0&search%5Bfilter_enum_country_origin%5D%5B0%5D=pl&search%5Bfilter_enum_original_owner%5D=1&search%5Bfilter_enum_no_accident%5D=1&search%5Border%5D=created_at_first%3Adesc&search%5Bbrand_program_id%5D%5B0%5D=&search%5Bcountry%5D=";

    private static int ACTUAL_CAR_AMOUNT = 0;
    private Context context;

    CheckerProcessor(Context context) {
        this.context = context;
    }

    void checkForNewCar(Integer amountOfCars) {
        System.out.println("CAR_AMOUNT:::::::::::::::::::" + ACTUAL_CAR_AMOUNT);
        createAnnotation();
        if (ACTUAL_CAR_AMOUNT >= amountOfCars) {
            ACTUAL_CAR_AMOUNT = amountOfCars;
        } else {
            createAnnotation();
            vibrateThePhone();
            ACTUAL_CAR_AMOUNT = amountOfCars;
        }
    }

    private void createAnnotation() {
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse(URI));
        PendingIntent pi = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(context)
                .setTicker("New Car!")
                .setColor(Color.BLUE)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("New Car!")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager2 = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager2.notify(0, notification);
    }

    private void vibrateThePhone() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(800);
    }
}
