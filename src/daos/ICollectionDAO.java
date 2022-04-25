package daos;

import model.entities.Collection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ICollectionDAO {
    List<Collection> getCollectionList(Connection connection) throws SQLException;
    Collection getCollectionInfoById(Connection connection, int id) throws SQLException;
    int getCollectionNumbersQuantity(Connection connection, int id) throws SQLException;
}
