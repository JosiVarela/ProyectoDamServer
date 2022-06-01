package controller;

import net.sf.jasperreports.engine.*;
import services.Resources;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReportController {
    public static void getCollectionReport(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;

        try{
            JasperReport report = JasperCompileManager.compileReport(Resources.REPORTS_FOLDER + "GEN_Collections.jrxml");

            DBConnection.connect();
            JasperPrint viewer = JasperFillManager.fillReport(report, null, DBConnection.getConnection());

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(viewer);
            objectOutputStream.flush();

        } catch (JRException e) {
            e.printStackTrace();
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("JRE");
            } catch (IOException ex) {
            }
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
        } catch (IOException e) {
        }finally {
            try {
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }
        }
    }

    public static void getCollectionReportByName(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        DataInputStream dataInputStream;
        String colName;
        Map<String, Object> parameters = new HashMap<>();

        try{

            dataInputStream = new DataInputStream(socket.getInputStream());
            colName = dataInputStream.readUTF();

            JasperReport report = JasperCompileManager.compileReport(Resources.REPORTS_FOLDER + "PER_CollectionsName.jrxml");

            parameters.put("colName", colName);

            DBConnection.connect();
            JasperPrint viewer = JasperFillManager.fillReport(report, parameters, DBConnection.getConnection());

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(viewer);
            objectOutputStream.flush();

        } catch (JRException e) {
            e.printStackTrace();
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("JRE");
            } catch (IOException ex) {
            }
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
        } catch (IOException e) {
        }finally {
            try {
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }
        }
    }

    public static void getNumbersReportByName(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        DataInputStream dataInputStream;
        String numberName;
        String colName;
        Map<String, Object> parameters = new HashMap<>();

        try{

            dataInputStream = new DataInputStream(socket.getInputStream());
            numberName = dataInputStream.readUTF();

            dataInputStream = new DataInputStream(socket.getInputStream());
            colName = dataInputStream.readUTF();

            JasperReport report = JasperCompileManager.compileReport(Resources.REPORTS_FOLDER + "PER_NumbersName.jrxml");

            parameters.put("numberName", numberName);
            parameters.put("colName", colName);


            DBConnection.connect();
            JasperPrint viewer = JasperFillManager.fillReport(report, parameters, DBConnection.getConnection());

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(viewer);
            objectOutputStream.flush();

        } catch (JRException e) {
            e.printStackTrace();
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("JRE");
            } catch (IOException ex) {
            }
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
        } catch (IOException e) {
        }finally {
            try {
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }
        }
    }
}
