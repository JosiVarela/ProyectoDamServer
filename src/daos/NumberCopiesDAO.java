package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberCopiesDAO implements INumberCopiesDAO{

    @Override
    public int getNumberCopiesQuantityByNumberId(Connection connection, String isbn) throws SQLException {
        int quantity = 0;
        String query = "select count(*) from number_copies where isbn = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, isbn);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            quantity = resultSet.getInt(1);
        }

        statement.close();
        resultSet.close();

        return quantity;
    }
}
