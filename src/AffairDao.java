import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by tcgogogo on 16/11/8.
 */
public class AffairDao {
    private static Connection conn = null;
    private static Statement statement = null;
    private static String mysqlCode = "";
    private static ResultSet resultSet = null;
    private static int result = 0;

    //将事务写进mysql对应的表中
    public static void writeToMysql(AffairModel affairs) throws IOException {
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        try {
            mysqlCode = "insert into affairs(startDate, endDate, content, state) values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(mysqlCode);
            //设置插入字段的值
            ps.setString(1, affairs.getStartDate().toString());
            ps.setString(2, affairs.getEndData().toString());
            ps.setString(3, affairs.getContent().toString());
            ps.setString(4, String.valueOf(affairs.getState()));
            result = ps.executeUpdate();
            //result返回成功插入的记录条数,若大于0则说明插入成功,返回当前表中的最大id(因为id设置的是自增)即为插入记录的id
            if (result > 0) {
                statement = conn.createStatement();
                mysqlCode = "select max(id) from affairs";
                resultSet = statement.executeQuery(mysqlCode);
                int id = 0;
                while (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString(1));
                }
                //弹窗提示
                JOptionPane.showMessageDialog(null, "新建的事务ID为: " + id);
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
        mysqlCode = "delete from affairs where id = " + id;
        result = statement.executeUpdate(mysqlCode);
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "已删除ID为:" + id + "的事务");
        }
        else {
            JOptionPane.showMessageDialog(null, "删除异常");
        }
    }

    //若id为0则表示查询全部,否则查询对应id的记录
    public static void queryFromMysql(int id) throws IOException, SQLException{
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        if (id > 0) {
            mysqlCode = "select * from affairs where id = " + id;
        }
        else if(id == 0) {
            mysqlCode = "select * from affairs";
        }
        resultSet = statement.executeQuery(mysqlCode);
        //将查询结果单独用一个子界面显示出来
        JFrame f = new JFrame("查询结果");
        Container contentPane = f.getContentPane();
        Vector vector = new Vector();
        vector.add("ID");
        vector.add("开始时间");
        vector.add("结束时间");
        vector.add("事务内容");
        vector.add("事务完成情况");
        DefaultTableModel tablemodel = new DefaultTableModel(new Vector(), vector);
        Vector value = new Vector();
        try {
            while (resultSet.next()) {
                Vector vt = new Vector();
                vt.add(resultSet.getString(1));
                vt.add(resultSet.getString(2));
                vt.add(resultSet.getString(3));
                vt.add(resultSet.getString(4));
                //state字段值为1表示已完成,0表示未完成
                if (resultSet.getString(5).equals("1")) {
                    vt.add("已完成");
                }
                else {
                    vt.add("未完成");
                }
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

    public static void changeData (AffairModel affairs) throws IOException {
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        try {
            mysqlCode = "update affairs set startDate = \'" + affairs.getStartDate() + "\',endDate = \'" + affairs.getEndData() +
                    "\',content = \'" + affairs.getContent() + "\' where id = " + affairs.getId();
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

    public static void finishAffair(int id) throws IOException, SQLException {
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        statement = conn.createStatement();
        mysqlCode = "update affairs set state = 1 where id = " + id;
        result = statement.executeUpdate(mysqlCode);
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "ID为" + id + "的事务已完成");
        }
        else {
            JOptionPane.showMessageDialog(null, "操作异常");
        }
    }

    //获取到当前日期还未完成的所有事务
    public static ArrayList<String> getAffairs(String endDate) throws IOException, SQLException {
        java.util.List<String> affairs = new ArrayList<>();
        if(conn == null) {
            conn = MysqlConnect.getConnet();
        }
        statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        mysqlCode = "select * from affairs where state = \'0\'";
        resultSet = statement.executeQuery(mysqlCode);
        while(resultSet.next()) {
            StringBuffer sb = new StringBuffer("");
            sb.append(resultSet.getString(4) + "要在" + resultSet.getString(3) + "前完成");
            affairs.add(sb.toString());
        }
        return (ArrayList<String>) affairs;
    }

    public static void queryAllFromMysql() throws IOException, SQLException {
        queryFromMysql(0);
    }
}
