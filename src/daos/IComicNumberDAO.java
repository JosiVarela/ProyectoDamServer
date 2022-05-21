package daos;

import model.entities.ComicNumber;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IComicNumberDAO {
    List<ComicNumber> getComicNumberListByColId(Connection connection, int colId) throws SQLException;
    boolean existsNumber(Connection connection, String isbn) throws SQLException;
    ComicNumber getComicNumber(Connection connection, String isbn) throws SQLException, IOException;
    void insertNumber(Connection connection, ComicNumber comicNumber) throws SQLException;
    void updateNumber(Connection connection, ComicNumber comicNumber) throws SQLException;
    void deleteNumber(Connection connection, String isbn) throws SQLException;
}
