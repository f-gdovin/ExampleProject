package cz.muni.fi.xgdovin.dao.dao;

import cz.muni.fi.xgdovin.dao.domain.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PersonDaoImpl extends DomainObjectDaoImpl<Person> implements PersonDao {
}
