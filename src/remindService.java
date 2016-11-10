import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tcgogogo on 16/11/8.
 */
public class remindService {
    Calendar calendar = Calendar.getInstance();
    public remindService() throws IOException, SQLException {
        StringBuffer sb = new StringBuffer("");
        sb.append(DateStd.dateStd(calendar.get(Calendar.MONTH) + 1));
        sb.append("-");
        sb.append(DateStd.dateStd(calendar.get(Calendar.DATE)));

        //获取当天过生日的人的姓名列表
        ArrayList<String> nameList =  PersonDao.getBirthday(sb.toString());
        StringBuffer sb2 = new StringBuffer("");
        sb2.append(calendar.get(Calendar.YEAR));
        sb2.append("-");
        sb2.append(sb);

        //获取当天还未完成的事务列表
        ArrayList<String> affairList = AffairDao.getAffairs(sb2.toString());

        StringBuffer message = new StringBuffer("");
        int nameNum = nameList.size();
        if (nameNum != 0) {
            message.append("今天过生日的有:\n");
            for (int i = 0; i < nameNum; i ++) {
                message.append(nameList.get(i) + "\n");
            }
        }
        int affairNum = affairList.size();
        if (affairNum != 0) {
            message.append("事务提醒:\n");
            for (int i = 0; i < affairNum; i ++) {
                message.append(affairList.get(i) + ".\n");
            }
        }
        //若当天有人过生日或有事务未完成则弹窗提示
        if (nameNum + affairNum > 0) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}
