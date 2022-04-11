package model;

import daos.CollectionDAO;
import model.entities.Collection;

import java.util.List;

public class CollectionManagemet {
    public static List<Collection> getCollectionList(){
        return new CollectionDAO().getCollectionList();
    }
}
