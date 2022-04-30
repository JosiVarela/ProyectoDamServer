package daos;

import java.sql.Connection;
import java.sql.SQLException;

public interface INumberCopiesDAO {
    int getNumberCopiesQuantityByNumberId(Connection connection, String isbn, int numberId) throws SQLException;
}
