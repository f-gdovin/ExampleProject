package cz.muni.fi.xgdovin.dao.dao;

import cz.muni.fi.xgdovin.dao.domain.DomainObject;
import cz.muni.fi.xgdovin.dao.utils.QueryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Primary
@Repository
@Transactional
public class DomainObjectDaoImpl<E extends DomainObject> implements DomainObjectDao<E> {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private QueryValidator queryValidator;

    @SuppressWarnings("unchecked")
    public E findById(long id) {
        return (E) em.find(getRealClass(), id);
    }

    public void create(E e) {
        em.persist(e);
    }

    public void update(E e) {
        em.merge(e);
    }

    public void delete(E e) {
        em.remove(e);
    }

    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        return (List<E>) em.createQuery("select e from " + getRealClass().getSimpleName() + " e",
                getRealClass()).getResultList();
    }

    @SuppressWarnings("unchecked")
    public E findByProperties(Map<String, Object> properties) {
        queryValidator.validateQueryParameters(getRealClass(), properties);
        return (E) createParametrizedQueryFromMap(properties).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<E> findAllByProperties(Map<String, Object> properties) {
        queryValidator.validateQueryParameters(getRealClass(), properties);
        return (List<E>) createParametrizedQueryFromMap(properties).getResultList();
    }

    private Query createParametrizedQueryFromMap(Map<String, Object> properties) {
        Query query = em.createQuery("select e from " + getRealClass().getSimpleName()
                        + " e where " + createWheresFromMap(properties),
                getRealClass());
        for (String key : properties.keySet()) {
            query.setParameter(key, properties.get(key));
        }
        return query;
    }

    private String createWheresFromMap(Map<String, Object> properties) {
        StringBuilder sb = new StringBuilder("");
        for (String key : properties.keySet()) {
            if (!sb.toString().isEmpty()) {
                sb.append(" and ");
            }
            sb.append(key).append("= :").append(key);
        }
        return sb.toString();
    }

    private Class<? extends DomainObject> getRealClass() {
        Type sooper = getClass().getGenericSuperclass();
        Type t = ((ParameterizedType)sooper).getActualTypeArguments()[0];

        return (Class<? extends DomainObject>) t;
    }
}
