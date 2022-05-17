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

    public static boolean existsNumber(Connection connection, String isbn) throws SQLException {
        return new ComicNumberDAO().existsNumber(connection,isbn);
    }

    public static ComicNumber getComicNumber(Connection connection, String isbn) throws SQLException, IOException {
        return new ComicNumberDAO().getComicNumber(connection, isbn);
    }
}
