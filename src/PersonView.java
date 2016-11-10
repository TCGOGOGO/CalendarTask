import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by tcgogogo on 16/11/8.
 */
public class PersonView extends JFrame{
    JPanel panel;

    JLabel personIdLabel;
    JTextField personIdText;
    JPanel idPanel;

    JRadioButton addRadioButton;
    JLabel nameLabel;
    JTextField nameText;
    JLabel birthLabel;
    JTextField birthText;
    JLabel phoneNumLabel;
    JTextField phoneNumText;
    JPanel addPanel;

    JRadioButton delRadioButton;
    JPanel delPanel;
    JRadioButton queryRadioButton;
    JPanel queryPanel;
    JRadioButton queryAllRadioButton;
    JPanel queryAllPanel;

    JRadioButton chgRadioButton;
    JLabel nameLabel2;
    JTextField nameText2;
    JLabel birthLabel2;
    JTextField birthText2;
    JLabel phoneNumLabel2;
    JTextField phoneNumText2;
    JPanel chgPanel;

    ButtonGroup buttonGroupOption;
    JButton okButton;

    PersonModel person;
    static final int SIZE = 7;

    public PersonView() {
        person = new PersonModel();

        addRadioButton = new JRadioButton("添加联系人");
        nameText = new JTextField(SIZE);
        nameLabel = new JLabel("姓名");
        birthText = new JTextField(SIZE);
        birthLabel = new JLabel("生日");
        phoneNumText = new JTextField(SIZE + 1);
        phoneNumLabel = new JLabel("电话号码");

        personIdLabel = new JLabel("联系人ID");
        personIdText = new JTextField(SIZE);

        delRadioButton = new JRadioButton("删除联系人");

        queryRadioButton = new JRadioButton("查询联系人");
        queryAllRadioButton = new JRadioButton("查询所有联系人");

        chgRadioButton = new JRadioButton("修改联系人信息");
        nameText2 = new JTextField(SIZE);
        nameLabel2 = new JLabel("姓名");
        birthText2 = new JTextField(SIZE);
        birthLabel2 = new JLabel("生日");
        phoneNumText2 = new JTextField(SIZE + 1);
        phoneNumLabel2 = new JLabel("电话号码");

        okButton = new JButton("提交");
        buttonGroupOption = new ButtonGroup();
        buttonGroupOption.add(addRadioButton);
        buttonGroupOption.add(delRadioButton);
        buttonGroupOption.add(queryRadioButton);
        buttonGroupOption.add(chgRadioButton);
        buttonGroupOption.add(queryAllRadioButton);

        addPanel = new JPanel(new FlowLayout());
        addPanel.add(addRadioButton);
        addPanel.add(nameLabel);
        addPanel.add(nameText);
        addPanel.add(birthLabel);
        addPanel.add(birthText);
        addPanel.add(phoneNumLabel);
        addPanel.add(phoneNumText);

        idPanel = new JPanel(new FlowLayout());
        idPanel.add(personIdLabel);
        idPanel.add(personIdText);

        JPanel tmpPanel = new JPanel(new GridLayout(4, 1));
        delPanel = new JPanel(new FlowLayout());
        delPanel.add(delRadioButton);

        queryPanel = new JPanel(new FlowLayout());
        queryPanel.add(queryRadioButton);

        queryAllPanel = new JPanel(new FlowLayout());
        queryAllPanel.add(queryAllRadioButton);

        tmpPanel.add(queryAllPanel);
        tmpPanel.add(idPanel);
        tmpPanel.add(delPanel);
        tmpPanel.add(queryPanel);

        chgPanel = new JPanel(new FlowLayout());
        chgPanel.add(chgRadioButton);
        chgPanel.add(nameLabel2);
        chgPanel.add(nameText2);
        chgPanel.add(birthLabel2);
        chgPanel.add(birthText2);
        chgPanel.add(phoneNumLabel2);
        chgPanel.add(phoneNumText2);

        panel = new JPanel(new FlowLayout());
        panel.add(addPanel);
        panel.add(tmpPanel);
        panel.add(chgPanel);
        panel.add(okButton);
        add(panel);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addRadioButton.isSelected()) {
                    try {
                        person.setName(nameText.getText());
                        person.setBirthday(birthText.getText());
                        person.setPhoneNumber(phoneNumText.getText());
                        PersonDao.writeToMysql(person);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else if (queryAllRadioButton.isSelected()) {
                    try {
                        PersonDao.queryAllFromMysql();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                else if (delRadioButton.isSelected()){
                    try {
                        PersonDao.deleteFromMysql(Integer.parseInt(personIdText.getText()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                else if (queryRadioButton.isSelected()) {
                    try {
                        PersonDao.queryFromMysql(Integer.parseInt(personIdText.getText()));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else if (chgRadioButton.isSelected()) {
                    try {
                        person.setId(Integer.parseInt(personIdText.getText()));
                        person.setName(nameText2.getText());
                        person.setBirthday(birthText2.getText());
                        person.setPhoneNumber(phoneNumText2.getText());
                        PersonDao.changeData(person);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "请输入联系人ID");
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
