package controller;

import net.sf.jasperreports.engine.*;
import services.Resources;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

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
}
