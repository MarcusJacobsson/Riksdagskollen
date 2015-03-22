package se.ottomatech.marcusjacobsson.sverigesriksdag.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Marcus Jacobsson on 2015-03-22.
 */
public class RiksdagskollenTimeUtil {
    public static String getTimeFromDateString(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.getDefault());
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String result;

        result = format.format(newDate);


        return result;
    }
}
