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

        // Initializing the JTable
        //JTable j = new JTable(dataModel);
        JTable j = new JTable();
        j.setModel(initializeArray(page));//invoking our custom model
        j.setDefaultRenderer(JLabel.class,  new Renderer());// for the rendering of cell

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
        j.setDefaultRenderer(JLabel.class,new Renderer());
        j.getTableHeader().setReorderingAllowed(false);

        container.add(nextButton);
        container.add(backButton);
        container.setLayout(new BorderLayout());
        container.add(j.getTableHeader(), BorderLayout.PAGE_START);
        container.add(j, BorderLayout.CENTER);
    }

    public TableModel initializeArray(int page){
        data = new String[20][6];
        Object[][] arrayField =  new Object[20][6];
        /*dataModel = new AbstractTableModel() {
            public int getColumnCount() { return 6; }
            public int getRowCount() { return 20;}
            public JLabel getValueAt(int row, int col) { return arrayField.get(col).get(row); }
        };*/

        columnNames = new String[] { "Horaires", "Lundi ", "Mardi ", "Mercredi ", "Jeudi ", "Vendredi " };

        String[] days = getWeekDays(page,"dd/MM/yyyy");
        String[] daysUS = getWeekDays(page,"yyyy-MM-dd");
        float hour = 9;
        DAO dao = new DAO();

        ArrayList<Creneaux> arrayList = dao.listerDAOCreneaux(daysUS[0],daysUS[daysUS.length-1]);
        java.sql.Date currentDate=null;
        for(int i=0;i<6 ;i++) {
            Object[] tempArrayField = new Object[20];
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
                JLabel textField=new JLabel();
                textField.setBounds(10,10,10,10);
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
                tempArrayField[y]=textField;
            }
            arrayField[i]=tempArrayField;
        }
        dataModel = new MyModel(columnNames,arrayField);
        return dataModel;
    }
    public void addNextButtonListener(ActionListener al){
        nextButton.addActionListener(al);
    }

    public void addBackButtonListener(ActionListener al){
        backButton.addActionListener(al);
    }

    private class Renderer extends DefaultTableCellRenderer{


        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            if(value instanceof JLabel){
                JLabel label = (JLabel)value;
                //you can add the image here
                label.setBackground(Color.GREEN);
                label.setOpaque(true);
                return label;
            }
            else {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        }
    }
    class MyModel extends javax.swing.table.DefaultTableModel{

        Object[][] row = {{new JLabel("Row 1 Col 1"), "Row 1 Col 2", "Row 1 Col3"},
                {new JLabel("Row 2 Col 1"), "Row 2 Col 2", "Row 2 Col3"},
                {new JLabel("Row 3 Col 1"), "Row 3 Col 2", "Row 3 Col3"},
                {new JLabel("Row 4 Col 1"), "Row 4 Col 2", "Row 4 Col3"}};

        Object[] col = {"Column 1", "Column 2", "Column 3"};

        public MyModel (Object[] col, Object[][] row){

            //Adding columns
            for(Object c: col)
                this.addColumn(c);

            //Adding rows
            for(Object[] r: row)
                addRow(r);

        }

        @Override

        public Class getColumnClass(int columnIndex) {
            if(columnIndex == 0)return getValueAt(0, columnIndex).getClass();

            else return super.getColumnClass(columnIndex);

        }

    }
}
