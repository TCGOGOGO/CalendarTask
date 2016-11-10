import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by tcgogogo on 16/11/8.
 */
public class RunnerView extends JFrame implements ActionListener{

    Button calendarButton;
    Button personButton;
    Button affairButton;
    JPanel panel;

    public RunnerView(String s) {
        calendarButton = new Button("日历查询");
        personButton = new Button("我的联系人");
        affairButton = new Button("我的事务");
        panel = new JPanel(new FlowLayout(100, 50, 50));
        panel.add(calendarButton);
        panel.add(personButton);
        panel.add(affairButton);
        this.setTitle(s);
        add(panel);

        calendarButton.addActionListener(this);
        personButton.addActionListener(this);
        affairButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calendarButton) {
            CalendarView calendarView = new CalendarView();
            calendarView.setTitle("日历");
            calendarView.setBounds(300, 300, 500, 400);
            calendarView.setVisible(true);
            calendarView.validate();
        }
        else if (e.getSource() == personButton) {
            PersonView personView = new PersonView();
            personView.setTitle("我的联系人");
            personView.setBounds(300, 300, 600, 300);
            personView.setVisible(true);
            personView.validate();
        }
        else if (e.getSource() == affairButton) {
            AffairView affairView = new AffairView();
            affairView.setTitle("我的事务");
            affairView.setBounds(300, 300, 600, 450);
            affairView.setVisible(true);
            affairView.validate();
            affairView.setResizable(false);
        }
    }
}
