package cz.muni.fi.xgdovin.dao.dao;

import cz.muni.fi.xgdovin.dao.domain.DomainObject;

import java.util.List;
import java.util.Map;

public interface DomainObjectDao<E extends DomainObject> {

    /**
     * Finds entity by id.
     *
     * @param id the long id of desired entity
     * @return the entity with given id, or null if there is none
     */
    E findById(long id);

    /**
     * Creates new entity.
     *
     * @param e the entity to be put into DB
     */
    void create(E e);

    /**
     * Updates the entity.
     *
     * @param e the entity in DB to be updated
     */
    void update(E e);

    /**
     * Deletes the entity from DB.
     *
     * @param e the entity to be deleted from DB
     */
    void delete(E e);

    /**
     * Finds all entites in DB and returns them as a list.
     *
     * @return the list of all entities in DB, or empty list if there are none.
     */
    List<E> findAll();

    /**
     * Finds entity by value of properties.
     *
     * @param properties the map of properties, where keys represent fields in entity and value desired values
     * @return the entity which has all of its properties from map entries and values of those match the keys,
     * or null if there is none
     */
    E findByProperties(Map<String, Object> properties);

    /**
     * Finds entity by value of properties.
     *
     * @param properties the map of properties, where keys represent fields in entity and value desired values
     * @return the list of entities which have all of its properties from map entries and values of those match the keys,
     * or empty list if there are none.
     */
    List<E> findAllByProperties(Map<String, Object> properties);
}
