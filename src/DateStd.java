/**
 * Created by tcgogogo on 16/11/8.
 */
public class DateStd {
    public static String dateStd(int num) {
        String ans = "";
        if (num < 10) {
            ans += "0";
        }
        ans += String.valueOf(num);
        return ans;
    }
}
