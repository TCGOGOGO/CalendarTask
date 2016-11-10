import java.util.Calendar;

/**
 * Created by tcgogogo on 16/11/7.
 */

public class CalendarModel {

    private int year = 2000;
    private int month = 1;

    public CalendarModel() {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String[] getCalendar() {
        String[] c = new String[50];
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int day = 31;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        }
        if (month == 2) {
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                day = 29;
            } else {
                day = 28;
            }
        }
        //firstDay表示该月的第一天是星期几,0表示周日
        for (int i = firstDay, cnt = 1; i < firstDay + day; i ++, cnt ++) {
            c[i] = String.valueOf(cnt);
        }
        return c;
    }
}