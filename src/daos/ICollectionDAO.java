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
    boolean existsCollectionWithNameNotId(Connection connection, int id, String name) throws SQLException;
    boolean existsCollectionWithSameName(Connection connection, String name) throws SQLException;
    boolean existsCollectionWithId(Connection connection, int id) throws SQLException;
    void updateCollection(Connection connection, Collection collection) throws SQLException;
    void insertCollection(Connection connection, Collection collection) throws SQLException;
    void deleteCollection(Connection connection, int id) throws SQLException;
    String getCollectionName(Connection connection, int colId) throws SQLException;
}
