package views;

import controllers.DAO;
import models.Acces;
import models.Creneaux;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CreneauxView extends JPanel{
    Container container;
    public JButton nextButton = new JButton("►");
    public JButton backButton = new JButton("◄");
    private String[][] data;
    private String[] columnNames;
    TableModel dataModel;

    public CreneauxView(Container container, int page){
        this.container=container;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer(page);
    }

    private String[] getWeekDays(int decalage, String dateFormat){
        Calendar now = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

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
        nextButton.setBounds(1000, 720, 100, 30);
        backButton.setBounds(10, 720, 100, 30);
    }

    public void addComponentsToContainer(int page) {
        initializeArray(page);
        // Initializing the JTable
        JTable j = new JTable(dataModel);
        j.setBounds(30, 40, 200, 300);
        j.setRowHeight(35);
        j.setEnabled(false);
        j.setFillsViewportHeight(true);
        JTableHeader header= j.getTableHeader();
        TableColumnModel colMod = header.getColumnModel();
        for(int i=0;i<6 ;i++) {
            TableColumn tabCol = colMod.getColumn(i);
            tabCol.setHeaderValue(columnNames[i]);
        }
        header.repaint();
        
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)j.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment( SwingConstants.CENTER );
        j.getTableHeader().setReorderingAllowed(false);

        container.add(nextButton);
        container.add(backButton);
        container.setLayout(new BorderLayout());
        container.add(j.getTableHeader(), BorderLayout.PAGE_START);
        container.add(j, BorderLayout.CENTER);
    }

    public void initializeArray(int page){
        data = new String[20][6];
        ArrayList<ArrayList<JTextField>> arrayField = new ArrayList();
        dataModel = new AbstractTableModel() {
            public int getColumnCount() { return 6; }
            public int getRowCount() { return 20;}
            public JTextField getValueAt(int row, int col) { return arrayField.get(row).get(col); }
        };
        columnNames = new String[] { "Horaires", "Lundi ", "Mardi ", "Mercredi ", "Jeudi ", "Vendredi " };

        String[] days = getWeekDays(page,"dd/MM/yyyy");
        String[] daysUS = getWeekDays(page,"yyyy-MM-dd");
        float hour = 9;
        DAO dao = new DAO();

        ArrayList<Creneaux> arrayList = dao.listerDAOCreneaux(daysUS[0],daysUS[daysUS.length-1]);
        java.sql.Date currentDate=null;
        for(int i=0;i<6 ;i++) {
            ArrayList<JTextField> tempArrayField = new ArrayList();
            if(i>0){
                columnNames[i] = columnNames[i] + days[i-1];
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsed = format.parse(daysUS[i-1]);
                    currentDate = new java.sql.Date(parsed.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            for (int y = 0; y <= 19; y++) {
                JTextField textField=new JTextField();
                if(i==0) {
                    String currentHour = Float.toString(hour);
                    String destHour = Float.toString((float) (hour+0.5));
                    if(currentHour.contains(".5")) {
                        data[y][i] = currentHour.replace(".5", "h30") + " - " + destHour.replace(".0", "h00");
                        textField.setText(currentHour.replace(".5", "h30") + " - " + destHour.replace(".0", "h00"));
                    }else{
                        data[y][i] = currentHour.replace(".0","h00")+" - "+destHour.replace(".5","h30");
                        textField.setText(currentHour.replace(".0","h00")+" - "+destHour.replace(".5","h30"));
                    }
                    hour+=0.5;
                }
                else {
                    boolean check=false;
                    for(Creneaux item: arrayList){
                        float currentHour = ((float) y/2)+9;
                        if(item.getDate().compareTo(currentDate)==0 && item.getHeure()==currentHour){
                            check=true;
                        }
                    }
                    if(check){
                        data[y][i]="RESERVE";
                        textField.setText("RESERVE");
                    }else{
                        data[y][i]="Libre";
                        textField.setText("Libre");
                    }
                }
                tempArrayField.add(textField);
            }
            arrayField.add(tempArrayField);
        }
    }
    public void addNextButtonListener(ActionListener al){
        nextButton.addActionListener(al);
    }

    public void addBackButtonListener(ActionListener al){
        backButton.addActionListener(al);
    }

    public void JTableExamples() {

    }
}
