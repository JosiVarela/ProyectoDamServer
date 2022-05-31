package daos;

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
            collection = new Collection(id, resultSet.getString(2), resultSet.getDate(3).toLocalDate(),
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

    @Override
    public boolean existsCollectionWithNameNotId(Connection connection, int id, String name) throws SQLException {
        String query = "select id_col from comic_collection where id_col != ? and title = ?";
        boolean result = false;

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);
        statement.setString(2, name);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            result = true;
        }

        statement.close();
        resultSet.close();

        return result;
    }

    @Override
    public boolean existsCollectionWithSameName(Connection connection, String name) throws SQLException {
        String query = "select id_col from comic_collection where title = ?";
        boolean result = false;

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            result = true;
        }

        resultSet.close();
        statement.close();

        return result;
    }

    @Override
    public boolean existsCollectionWithId(Connection connection, int id) throws SQLException {
        String query = "select id_col from comic_collection where id_col = ?";
        boolean result = false;

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            result = true;
        }

        resultSet.close();
        statement.close();

        return result;
    }

    @Override
    public void updateCollection(Connection connection, Collection collection) throws SQLException {
        String query = "update comic_collection set title = ?, argument = ? where id_col = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, collection.getTitle());
        statement.setString(2, collection.getArgument());
        statement.setInt(3, collection.getId());

        statement.executeUpdate();
    }

    @Override
    public void insertCollection(Connection connection, Collection collection) throws SQLException {
        String query = "insert into comic_collection(title, creation_date, argument) values (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, collection.getTitle());
        statement.setDate(2, Date.valueOf(collection.getCreationDate()));
        statement.setString(3, collection.getArgument());

        statement.executeUpdate();
    }

    @Override
    public void deleteCollection(Connection connection, int id) throws SQLException {
        String query = "delete from comic_collection where id_col = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);

        statement.executeUpdate();
    }

    @Override
    public String getCollectionName(Connection connection, int colId) throws SQLException {
        String result = null;
        String query = "select title from comic_collection where id_col = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, colId);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            result = resultSet.getString(1);
        }

        resultSet.close();
        statement.close();

        return result;
    }
}
