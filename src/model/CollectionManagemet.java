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
}
