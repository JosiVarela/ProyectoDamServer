package model;

import daos.ComicNumberDAO;
import model.entities.ComicNumber;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NumberManagement {
    public static List<ComicNumber> getNumberListByColId(Connection connection, int colId) throws SQLException {
        return new ComicNumberDAO().getComicNumberListByColId(connection, colId);
    }

    public static List<ComicNumber> getComicNumbers(Connection connection) throws SQLException {
        return new ComicNumberDAO().getComicNumbers(connection);
    }

    public static boolean existsNumber(Connection connection, String isbn) throws SQLException {
        return new ComicNumberDAO().existsNumber(connection,isbn);
    }

    public static ComicNumber getComicNumber(Connection connection, String isbn) throws SQLException, IOException {
        return new ComicNumberDAO().getComicNumber(connection, isbn);
    }

    public static void insertComicNumber(Connection connection, ComicNumber comicNumber) throws SQLException {
        new ComicNumberDAO().insertNumber(connection, comicNumber);
    }

    public static void updateComicNumber(Connection connection, ComicNumber comicNumber) throws SQLException {
        new ComicNumberDAO().updateNumber(connection, comicNumber);
    }

    public static void deleteComicNumber(Connection connection, String isbn) throws SQLException {
        new ComicNumberDAO().deleteNumber(connection, isbn);
    }
}
