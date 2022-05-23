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

    public static void insertCopy(Connection connection, ComicCopy comicCopy) throws SQLException {
        new NumberCopiesDAO().insertCopie(connection, comicCopy);
    }

    public static boolean existsCopy(Connection connection, int id) throws SQLException {
        return new NumberCopiesDAO().existsCopie(connection, id);
    }

    public static void updateCopy(Connection connection, ComicCopy comicCopy) throws SQLException {
        new NumberCopiesDAO().updateCopy(connection, comicCopy);
    }

    public static void deleteCopy(Connection connection, int id) throws SQLException {
        new NumberCopiesDAO().deleteCopie(connection, id);
    }
}
