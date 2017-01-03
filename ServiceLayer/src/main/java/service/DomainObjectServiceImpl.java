package service;

import cz.muni.fi.xgdovin.dao.dao.DomainObjectDao;
import cz.muni.fi.xgdovin.dao.domain.DomainObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Transactional
public class DomainObjectServiceImpl<E extends DomainObject> implements DomainObjectService<E> {

    @Autowired
    private DomainObjectDao<E> domainObjectDao;

    public E findByUUid(UUID uuid) {
        return domainObjectDao.findByUUid(uuid);
    }

    public void create(E e) {
        domainObjectDao.create(e);
    }

    public void update(E e) {
        domainObjectDao.update(e);
    }

    public void delete(E e) {
        domainObjectDao.delete(e);
    }

    public List<E> findAll() {
        return domainObjectDao.findAll();
    }

    public E findByProperties(Map<String, Object> properties) {
        return domainObjectDao.findByProperties(properties);
    }

    public List<E> findAllByProperties(Map<String, Object> properties) {
        return domainObjectDao.findAllByProperties(properties);
    }
}
