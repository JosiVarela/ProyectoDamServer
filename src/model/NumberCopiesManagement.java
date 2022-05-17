package model;

import daos.NumberCopiesDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class NumberCopiesManagement {
    public static int getNumberCopiesQuantity(Connection connection, String isbn) throws SQLException {
        return new NumberCopiesDAO().getNumberCopiesQuantityByNumberId(connection, isbn);
    }
}
