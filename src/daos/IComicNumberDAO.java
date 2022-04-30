package daos;

import model.entities.ComicNumber;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IComicNumberDAO {
    List<ComicNumber> getComicNumberListByColId(Connection connection, int colId) throws SQLException;
}
