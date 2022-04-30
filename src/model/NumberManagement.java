package model;

import daos.ComicNumberDAO;
import model.entities.ComicNumber;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NumberManagement {
    public static List<ComicNumber> getNumberListByColId(Connection connection, int colId) throws SQLException {
        return new ComicNumberDAO().getComicNumberListByColId(connection, colId);
    }
}
