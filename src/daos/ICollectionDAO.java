package daos;

import model.entities.Collection;

import java.util.List;

public interface ICollectionDAO {
    List<Collection> getCollectionList();
}
