package daos;

import model.entities.ComicNumber;
import model.NumberCopiesManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComicNumberDAO implements IComicNumberDAO{
    @Override
    public List<ComicNumber> getComicNumberListByColId(Connection connection, int colId) throws SQLException {
        List<ComicNumber> numberList = new ArrayList<>();
        String isbn;
        int numberId;
        String query = "select * from comic_number where collection_id = ?";


        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, colId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            isbn = resultSet.getString(1);
            numberId = resultSet.getInt(2);

            numberList.add(new ComicNumber(isbn, numberId, resultSet.getString(5),
                    resultSet.getString(3),
                    NumberCopiesManagement.getNumberCopiesQuantity(connection, isbn, numberId),
                    resultSet.getInt(4)));
        }

        statement.close();
        resultSet.close();

        return numberList;
    }
}
