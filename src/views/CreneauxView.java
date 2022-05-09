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

        // Data to be displayed in the JTable
        String[][] data = {
                { "9h", "", "", "" , "" , "" },
                { "9h30", "", "", "", "", "" }
        };

        // Column Names
        String[] columnNames = { "Horaires", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi" };

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

    public void JTableExamples() {

    }
}
