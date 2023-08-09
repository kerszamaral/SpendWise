package SpendWise.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class Dates {
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static Date convLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date convString(String date) {
        try {
            return dateFormatter.parse(date);
        } catch (Exception e) {
            return Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }
    }
}
