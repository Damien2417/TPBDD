package views;

import controllers.DAO;
import models.Acces;
import models.Creneaux;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreneauxView extends JPanel{
    Container container;
    Acces user;
    private JTable j;
    public JButton nextButton = new JButton("►");
    public JButton backButton = new JButton("◄");
    private String[] columnNames;
    TableModel dataModel;

    public JTable getTable() {
        return j;
    }

    public CreneauxView(Container container, int page, Acces user){
        this.container=container;
        this.user=user;

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
        j = new JTable();
        j.setModel(initializeArray(page));//invoking our custom model
        j.setDefaultRenderer(JLabel.class,  new Renderer());// for the rendering of cell
        j.setBounds(30, 40, 200, 300);
        j.setRowHeight(35);
        j.setEnabled(false);
        j.setFillsViewportHeight(true);
        j.getTableHeader().setBackground( new Color(136,112,76));


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
        j.getTableHeader().setResizingAllowed(false);

        container.add(nextButton);
        container.add(backButton);
        container.setLayout(new BorderLayout());
        container.add(j.getTableHeader(), BorderLayout.PAGE_START);
        container.add(j, BorderLayout.CENTER);
    }

    public TableModel initializeArray(int page){
        Object[][] arrayField =  new Object[20][6];

        columnNames = new String[] { "Horaires", "Lundi ", "Mardi ", "Mercredi ", "Jeudi ", "Vendredi " };

        String[] days = getWeekDays(page,"dd/MM/yyyy");
        String[] daysUS = getWeekDays(page,"yyyy-MM-dd");
        float hour = 9;
        DAO dao = new DAO();

        ArrayList<Creneaux> arrayList = dao.listerDAOCreneaux(daysUS[0],daysUS[daysUS.length-1]);
        java.sql.Date currentDate=null;
        for(int i=0;i<6 ;i++) {
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
                        textField.setText(currentHour.replace(".5", "h30") + " - " + destHour.replace(".0", "h00"));
                    }else{
                        textField.setText(currentHour.replace(".0","h00")+" - "+destHour.replace(".5","h30"));
                    }
                    hour+=0.5;
                }
                else {
                    boolean check=false;
                    String nom="";
                    for(Creneaux item: arrayList){
                        float currentHour = ((float) y/2)+9;
                        if(item.getDate().compareTo(currentDate)==0 && item.getHeure()==currentHour){
                            check=true;
                            nom = item.getNom();
                        }
                    }
                    if(check){
                        textField.setText(nom);
                    }else{
                        textField.setText("");
                    }
                }
                arrayField[y][i]=textField;
            }
        }
        dataModel = new DataModel(columnNames,arrayField);
        return dataModel;
    }
    public void addNextButtonListener(ActionListener al){
        nextButton.addActionListener(al);
    }

    public void addBackButtonListener(ActionListener al){
        backButton.addActionListener(al);
    }

    private class Renderer extends DefaultTableCellRenderer{
        Color libre = new Color(241,218,176);
        Color reserve = new Color(212,213,207);
        Color header = new Color(136,112,76);
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            if(value instanceof JLabel){
                JLabel label = (JLabel)value;
                //you can add the image here
                String text= label.getText();

                if(column==0){
                    label.setBackground(header);
                }
                else if(text!=""){
                    label.setBackground(reserve);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    if(!user.getStatut().equals("Medecin")){
                        if(user.getPrenom().equals(label.getText())){
                            label.setText("Mon rendez-vous");
                        }
                        else{
                            label.setText("");
                        }

                    }
                }else{
                    label.setBackground(libre);
                    label.setText("");
                }
                label.setOpaque(true);
                return label;
            }
            else {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        }
    }
    class DataModel extends javax.swing.table.DefaultTableModel{

        Object[][] row;

        Object[] col;

        public DataModel(Object[] col, Object[][] row){

            //Adding columns
            for(Object c: col)
                this.addColumn(c);

            //Adding rows
            for(Object[] r: row)
                addRow(r);

        }

        @Override

        public Class getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }

    }

    public void addTableListener(MouseListener al){
        j.addMouseListener(al);
    }
}
