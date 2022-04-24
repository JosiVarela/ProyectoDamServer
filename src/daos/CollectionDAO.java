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
        String query = "select * from comic_collection";
        List<Collection> collectionList = new ArrayList<>();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            collectionList.add(new Collection(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getDate(3), resultSet.getString(4)));
        }

        statement.close();
        resultSet.close();

        return collectionList;
    }
}
