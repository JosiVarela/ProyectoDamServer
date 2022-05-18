package daos;

import model.entities.ComicNumber;
import model.NumberCopiesManagement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        String query = "select * from comic_number where collection_id = ?";


        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, colId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            isbn = resultSet.getString(1);

            numberList.add(new ComicNumber(isbn, resultSet.getInt(2), resultSet.getString(5),
                    resultSet.getString(3),
                    NumberCopiesManagement.getNumberCopiesQuantity(connection, isbn),
                    resultSet.getInt(4)));
        }

        statement.close();
        resultSet.close();

        return numberList;
    }

    @Override
    public boolean existsNumber(Connection connection, String isbn) throws SQLException {
        String query = "select isbn from comic_number where isbn = ?";
        boolean result = false;

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, isbn);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            result = true;
        }

        resultSet.close();
        statement.close();

        return result;
    }

    @Override
    public ComicNumber getComicNumber(Connection connection, String isbn) throws SQLException, IOException {
        ComicNumber comicNumber = null;
        InputStream aux;
        byte[] image;
        String query = "select * from comic_number where isbn = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, isbn);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            aux = resultSet.getBinaryStream(6);
            comicNumber = new ComicNumber(resultSet.getString(1), resultSet.getInt(2),
                    resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
                    resultSet.getString(7));

            if(aux != null){
                image = aux.readAllBytes();
                comicNumber.setImage(image);
            }
        }

        resultSet.close();
        statement.close();

        return comicNumber;
    }

    @Override
    public void insertNumber(Connection connection, ComicNumber comicNumber) throws SQLException {
        String query = "insert into comic_number values(?,?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, comicNumber.getIsbn());
        statement.setInt(2, comicNumber.getComicNumber());
        statement.setString(3, comicNumber.getCover());
        statement.setInt(4, comicNumber.getColId());
        statement.setString(5, comicNumber.getName());
        statement.setString(7, comicNumber.getArgument());

        if(comicNumber.getImage() != null){
            statement.setBinaryStream(6, new ByteArrayInputStream(comicNumber.getImage()));
        }else{
            statement.setBinaryStream(6, null);
        }

        statement.executeUpdate();
    }
}
