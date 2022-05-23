package daos;

import model.entities.ComicCopy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface INumberCopiesDAO {
    int getNumberCopiesQuantityByNumberId(Connection connection, String isbn) throws SQLException;
    boolean existsCopie(Connection connection, int idCopie) throws SQLException;
    void insertCopie(Connection connection, ComicCopy comicCopy) throws SQLException;
    void updateCopie(Connection connection, ComicCopy comicCopy) throws SQLException;
    void deleteCopie(Connection connection, int idCopie) throws SQLException;
    List<ComicCopy> getComicCopiesList (Connection connection, String isbn) throws SQLException;
}
