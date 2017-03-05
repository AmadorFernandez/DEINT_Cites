package com.amador.cites.utils;

import android.util.Patterns;

import com.amador.cites.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amador on 5/03/17.
 */

public class Validate {

    public static boolean validateEmail(String email){

        boolean result = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return result;
    }

    public static boolean validatePhone(String phone){

        boolean result = Patterns.PHONE.matcher(phone).matches();
        return result;
    }

    public static boolean validateSimpleText(String simpleText){

        boolean result = !simpleText.isEmpty();
        return result;
    }

    public static boolean validateFormatDate(String date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        boolean result = true;
        Date dateFormating;

        try {
            dateFormating = dateFormat.parse(date);
        } catch (ParseException e) {
            result = false;
        }

        return result;
    }

    public static boolean validateFormatTime(String time){

        boolean result = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date timeFormating;

        try {
            timeFormating = dateFormat.parse(time);
        } catch (ParseException e) {
            result = false;
        }


        return result;
    }

    public static boolean validateTimesCite(String dateStart, String dateEnd){

        boolean result = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date dateStartFormating;
        Date dateEndFormating;

        try {

            dateEndFormating = dateFormat.parse(dateEnd);
            dateStartFormating = dateFormat.parse(dateStart);

            result = dateStartFormating.before(dateEndFormating);

        } catch (ParseException e) {

            result = false;
        }



        return result;
    }
}
