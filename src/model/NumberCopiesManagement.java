package model;

import daos.NumberCopiesDAO;
import model.entities.ComicCopy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NumberCopiesManagement {
    public static int getNumberCopiesQuantity(Connection connection, String isbn) throws SQLException {
        return new NumberCopiesDAO().getNumberCopiesQuantityByNumberId(connection, isbn);
    }

    public static List<ComicCopy> getComicCopiesList(Connection connection, String isbn) throws SQLException {
        return new NumberCopiesDAO().getComicCopiesList(connection, isbn);
    }
}
