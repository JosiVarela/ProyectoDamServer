package daos;

import model.entities.ComicCopy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean existsCopie(Connection connection, int idCopy) throws SQLException {
        String query = "select id from number_copies where id = ?";
        boolean result = false;

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, idCopy);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            result = true;
        }

        resultSet.close();
        statement.close();

        return result;
    }

    @Override
    public void insertCopie(Connection connection, ComicCopy comicCopy) throws SQLException {
        String query = "insert into number_copies (purchase_date, state, isbn, observations) values (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setDate(1, Date.valueOf(comicCopy.getPurchaseDate()));
        statement.setInt(2, comicCopy.getState());
        statement.setString(3, comicCopy.getIsbn());
        statement.setString(4, comicCopy.getObservations());

        statement.executeUpdate();
    }

    @Override
    public void updateCopy(Connection connection, ComicCopy comicCopy) throws SQLException {
        String query = "update number_copies set purchase_date = ?, state = ?, observations = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setDate(1, Date.valueOf(comicCopy.getPurchaseDate()));
        statement.setInt(2, comicCopy.getState());
        statement.setString(4, comicCopy.getObservations());

        statement.executeUpdate();
    }

    @Override
    public void deleteCopie(Connection connection, int idCopy) throws SQLException {
        String query = "delete from number_copies where id = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, idCopy);

        statement.executeUpdate();
    }

    @Override
    public List<ComicCopy> getComicCopiesList(Connection connection, String isbn) throws SQLException {
        String query = "select * from number_copies where isbn = ?";
        List<ComicCopy> comicCopyList = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, isbn);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            comicCopyList.add(new ComicCopy(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(),
                    resultSet.getInt(3), resultSet.getString(5), resultSet.getString(4)));
        }

        resultSet.close();
        statement.close();

        return comicCopyList;
    }

    @Override
    public ComicCopy getComicCopy(Connection connection, int id) throws SQLException {
        String query = "select * from number_copies where id = ?";
        ComicCopy comicCopy = null;

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            comicCopy = new ComicCopy(id, resultSet.getDate(2).toLocalDate(), resultSet.getInt(3),
                    resultSet.getString(5), resultSet.getString(4));
        }

        resultSet.close();
        statement.close();aaaa

        return  comicCopy;
    }
}
