package daos;

import controller.DBConnection;
import data.MockData;
import model.entities.Collection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectionDAO implements ICollectionDAO {
    @Override
    public List<Collection> getCollectionList(Connection connection) throws SQLException {
        String query = "select id_col, title from comic_collection";
        List<Collection> collectionList = new ArrayList<>();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            collectionList.add(new Collection(resultSet.getInt(1), resultSet.getString(2),
                     null, null));
        }

        statement.close();
        resultSet.close();

        return collectionList;
    }

    @Override
    public List<Collection> getCollectionListByName(Connection connection, String name) throws SQLException {
        String query = "select id_col, title from comic_collection where title like(?)";
        List<Collection> collectionList = new ArrayList<>();
        name = "%" + name + "%";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            collectionList.add(new Collection(resultSet.getInt(1), resultSet.getString(2),
                    null, null));
        }

        statement.close();
        resultSet.close();

        return collectionList;
    }

    @Override
    public Collection getCollectionInfoById(Connection connection, int id) throws SQLException {
        String query = "select * from comic_collection where id_col = ?";
        Collection collection = null;

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            collection = new Collection(id, resultSet.getString(2), resultSet.getDate(3),
                    resultSet.getString(4));
        }

        statement.close();
        resultSet.close();

        return collection;
    }

    @Override
    public int getCollectionNumbersQuantity(Connection connection, int id) throws SQLException {
        String query = "select count(*) from comic_number where collection_id = ?";
        int collectionQuantity = 0;

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            collectionQuantity = resultSet.getInt(1);
        }

        return collectionQuantity;
    }
}
