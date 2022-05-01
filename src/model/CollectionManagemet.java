package model;

import daos.CollectionDAO;
import model.entities.Collection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CollectionManagemet {
    public static List<Collection> getCollectionList(Connection connection) throws SQLException {
        return new CollectionDAO().getCollectionList(connection);
    }

    public static List<Collection> getCollectionListByName(Connection connection, String name) throws SQLException {
        return new CollectionDAO().getCollectionListByName(connection, name);
    }

    public static Collection getCollectionInfoById(Connection connection, int id) throws SQLException {
        return new CollectionDAO().getCollectionInfoById(connection, id);
    }

    public static int getCollectionNumbersQuantity(Connection connection, int id) throws SQLException {
        return new CollectionDAO().getCollectionNumbersQuantity(connection, id);
    }

    public static boolean existsCollectionWithName(Connection connection, int id, String name) throws SQLException {
        return new CollectionDAO().existsCollectionWithName(connection, id, name);
    }

    public static void updateCollection(Connection connection, Collection collection) throws SQLException {
        new CollectionDAO().updateCollection(connection, collection);
    }
}
