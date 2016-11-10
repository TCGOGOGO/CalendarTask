import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;

/**
 * Created by tcgogogo on 16/11/8.
 */
public class PersonDao {
    private static Connection conn = null;
    private static Statement statement = null;
    private static String mysqlCode = "";
    private static ResultSet resultSet = null;
    private static int result = 0;


    public static void writeToMysql(PersonModel person) throws IOException {
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        try {
            mysqlCode = "insert into person(name, birthday, phoneNum) values(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(mysqlCode);
            ps.setString(1, person.getName().toString());
            ps.setString(2, person.getBirthday().toString());
            ps.setString(3, person.getPhoneNumber().toString());
            result = ps.executeUpdate();
            if (result > 0) {
                statement = conn.createStatement();
                mysqlCode = "select max(id) from person";
                resultSet = statement.executeQuery(mysqlCode);
                int id = 0;
                while (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString(1));
                }
                JOptionPane.showMessageDialog(null, "新建的联系人ID为: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromMysql(int id) throws IOException, SQLException {
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        statement = conn.createStatement();
        mysqlCode = "delete from person where id = " + id;
        result = statement.executeUpdate(mysqlCode);
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "已删除ID为:" + id + "的联系人");
        }
        else {
            JOptionPane.showMessageDialog(null, "删除异常");
        }
    }

    public static void queryFromMysql(int id) throws IOException, SQLException{
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        if (id > 0) {
            mysqlCode = "select * from person where id = " + id;
        }
        else if (id == 0){
            mysqlCode = "select * from person";
        }
        resultSet = statement.executeQuery(mysqlCode);
        JFrame f = new JFrame("查询结果");
        Container contentPane = f.getContentPane();
        Vector vector = new Vector();
        vector.add("ID");
        vector.add("姓名");
        vector.add("生日");
        vector.add("手机号码");
        DefaultTableModel tablemodel = new DefaultTableModel(new Vector(), vector);
        Vector value = new Vector();
        try {
            while (resultSet.next()) {
                Vector vt = new Vector();
                vt.add(resultSet.getString(1));
                vt.add(resultSet.getString(2));
                vt.add(resultSet.getString(3));
                vt.add(resultSet.getString(4));
                value.add(vt);
            }
            tablemodel.setDataVector(value, vector);
            JTable table = new JTable(tablemodel);
            contentPane.add(new JScrollPane(table));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        f.pack();
        f.setVisible(true);
    }

    public static void changeData (PersonModel person) throws IOException {
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        try {
            mysqlCode = "update person set name = \'" + person.getName() + "\',birthday = \'" + person.getBirthday() +
                    "\',phoneNum = \'" + person.getPhoneNumber() + "\' where id = " + person.getId();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeUpdate(mysqlCode);
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "更新成功");
            }
            else {
                JOptionPane.showMessageDialog(null, "更新失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getBirthday(String birth) throws IOException, SQLException {
        List<String> persons = new ArrayList<>();
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        mysqlCode = "select * from person where birthday like \'%" + birth + "\'";
        resultSet = statement.executeQuery(mysqlCode);
        while(resultSet.next()) {
            persons.add(resultSet.getString(2));
        }
        return (ArrayList<String>) persons;
    }

    public static void queryAllFromMysql() throws IOException, SQLException {
        queryFromMysql(0);
    }
}
