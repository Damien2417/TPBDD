package views;

import models.Acces;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreneauxView extends JPanel{
    Container container;
    public JButton loginButton = new JButton("Login");
    public JButton resetButton = new JButton("Reset");
    private String[][] data;
    private String[] columnNames;

    public CreneauxView(Container container){
        this.container=container;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
    }

    private String[] getWeekDays(int decalage){
        Calendar now = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String[] days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2 + decalage; //add 2 if your week start on monday
        now.add(Calendar.DAY_OF_MONTH, delta );
        for (int i = 0; i < 7; i++)
        {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    public void setLayoutManager() {
        this.setLayout(null);
    }

    public void setLocationAndSize() {
        loginButton.setBounds(50, 300, 100, 30);
        resetButton.setBounds(200, 300, 100, 30);
    }

    public void addComponentsToContainer() {
        initializeArray();
        // Initializing the JTable
        JTable j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        j.setEnabled(false);
        j.setFillsViewportHeight(true);
        
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)j.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment( SwingConstants.CENTER );
        j.getTableHeader().setReorderingAllowed(false);
        container.setLayout(new BorderLayout());
        container.add(j.getTableHeader(), BorderLayout.PAGE_START);
        container.add(j, BorderLayout.CENTER);


        /*container.add(loginButton);
        container.add(resetButton);*/
    }

    public void initializeArray(){
        data = new String[20][6];
        columnNames = new String[] { "Horaires", "Lundi ", "Mardi ", "Mercredi ", "Jeudi ", "Vendredi " };

        String[] days = getWeekDays(0);
        float hour = 9;
        for(int i=0;i<6;i++) {
            if(i>0)
                columnNames[i] = columnNames[i] + days[i-1];
            for (int y = 0; y <= 19; y++) {
                if(i==0) {
                    String currentHour = Float.toString(hour);
                    String destHour = Float.toString((float) (hour+0.5));
                    if(currentHour.contains(".5"))
                        data[y][i] = currentHour.replace(".5","h30")+" - "+destHour.replace(".0","h00");
                    else
                        data[y][i] = currentHour.replace(".0","h00")+" - "+destHour.replace(".5","h30");;
                    hour+=0.5;
                }
                else
                    data[y][i] = days[i-1];

            }
        }
    }

    public void JTableExamples() {

    }
}
