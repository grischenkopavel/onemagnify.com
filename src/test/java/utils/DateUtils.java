package utils;/*
Created by Pavel Gryshchenko on 07.02.2023
*/

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public DateUtils() {
    }

    public String getCurrentYear() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
        String currentYear = simpleDateFormat.format(date);
        return currentYear;
    }
}
