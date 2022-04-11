package daos;

import data.MockData;
import model.entities.Collection;

import java.util.List;

public class CollectionDAO implements ICollectionDAO {
    @Override
    public List<Collection> getCollectionList() {
        return MockData.getCollections();
    }
}
