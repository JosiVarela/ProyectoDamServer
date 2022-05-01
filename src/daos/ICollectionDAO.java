package daos;

import model.entities.Collection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ICollectionDAO {
    List<Collection> getCollectionList(Connection connection) throws SQLException;
    List<Collection> getCollectionListByName(Connection connection, String name) throws SQLException;
    Collection getCollectionInfoById(Connection connection, int id) throws SQLException;
    int getCollectionNumbersQuantity(Connection connection, int id) throws SQLException;
    boolean existsCollectionWithName(Connection connection, int id, String name) throws SQLException;
    void updateCollection(Connection connection, Collection collection) throws SQLException;
}
