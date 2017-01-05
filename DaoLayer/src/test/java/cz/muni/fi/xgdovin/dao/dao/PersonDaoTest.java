package cz.muni.fi.xgdovin.dao.dao;

import cz.muni.fi.xgdovin.dao.domain.Person;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.*;

import static org.junit.Assert.fail;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonDaoTest {

    @Autowired
    private PersonDao personDao;

    private Person person1;
    private Person person2;

    @Before
    public void setup() {
        person1 = new Person();
        person1.setFirstName("Ferko");
        person1.setSurname("Mrkvicka");
        person1.setDescription("Najvacsi mykolog na svete");
        person1.setUsername("feri333");
        person1.setPassword("notAHash,ButWhatever");
        personDao.create(person1);

    }

    @Test
    public void createTest() {
        personDao.create(person1);
        Person result = personDao.findById(person1.getId());
        Assert.assertTrue(result.equals(person1));
    }

    @Test(expected = PersistenceException.class)
    public void createNullFirstNameTest() {
        Person invalidPerson = new Person();
        invalidPerson.setSurname("Mrkvicka");
        invalidPerson.setDescription("Najvacsi mykolog na svete");
        invalidPerson.setUsername("feri333");
        invalidPerson.setPassword("notAHash,ButWhatever");
        personDao.create(invalidPerson);
        fail();
    }

    @Test(expected = PersistenceException.class)
    public void createNullSurnameTest() {
        Person invalidPerson = new Person();
        invalidPerson.setFirstName("Ferko");
        invalidPerson.setDescription("Najvacsi mykolog na svete");
        invalidPerson.setUsername("feri333");
        invalidPerson.setPassword("notAHash,ButWhatever");
        personDao.create(invalidPerson);
        fail();
    }

    @Test(expected = PersistenceException.class)
    public void createNullUserNameTest() {
        Person invalidPerson = new Person();
        invalidPerson.setFirstName("Ferko");
        invalidPerson.setSurname("Mrkvicka");
        invalidPerson.setDescription("Najvacsi mykolog na svete");
        invalidPerson.setPassword("notAHash,ButWhatever");
        personDao.create(invalidPerson);
        fail();
    }

    @Test(expected = PersistenceException.class)
    public void createNullPasswordTest() {
        Person invalidPerson = new Person();
        invalidPerson.setFirstName("Ferko");
        invalidPerson.setSurname("Mrkvicka");
        invalidPerson.setDescription("Najvacsi mykolog na svete");
        invalidPerson.setUsername("feri333");
        personDao.create(invalidPerson);
        fail();
    }

    @Test
    public void updateTest() {
        String firstName = "Ferko";
        String surname = "Mrkvicka";
        String newDescription = "I believe it should be spelled \"mYkolog\" :)";

        Map<String, Object> properties = new HashMap<>();
        properties.put("firstName", firstName);
        properties.put("surname", surname);

        Person fromDB = personDao.findByProperties(properties);     //get some person from DB, check it truly exist there
        Assert.assertNotNull(fromDB);

        fromDB.setDescription(newDescription);
        personDao.update(fromDB);

        Person updatedFromDB = personDao.findById(fromDB.getId());   //reload same (hopefully updated) person by ID

        Assert.assertEquals(updatedFromDB.getId(), fromDB.getId());
        Assert.assertEquals(updatedFromDB.getFirstName(), firstName);
        Assert.assertEquals(updatedFromDB.getSurname(), surname);
        Assert.assertEquals(updatedFromDB.getDescription(), newDescription);
    }

    @Test
    public void findById() {
        Person result = personDao.findById(person1.getId());
        Assert.assertTrue(result.equals(person1));
    }

    @Test
    public void findByFullNameProperty() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("firstName", person1.getFirstName());
        properties.put("surname", person1.getSurname());

        long id = personDao.findByProperties(properties).getId();
        Person result = personDao.findById(id);
        Assert.assertTrue(result.equals(person1));
    }

    @Test
    public void findByIdProperty() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", person1.getId());

        long id = personDao.findByProperties(properties).getId();
        Person result = personDao.findById(id);
        Assert.assertTrue(result.equals(person1));
    }

    @Test
    public void deleteTest() {
        Person result = personDao.findById(person1.getId());
        personDao.delete(result);
        Assert.assertNull(personDao.findById(person1.getId()));
    }

    @Test
    public void findAllTest() {
        person2 = new Person();
        person2.setFirstName("Jan");
        person2.setSurname("Slepy");
        person2.setDescription("Najhorsi mykolog na svete");
        person2.setUsername("janciBystrozraky");
        person2.setPassword("notAHash,ButWhatever...StillDifferentThanFeri'sHash");
        personDao.create(person2);

        List<Person> listResult = personDao.findAll();

        Assert.assertTrue(listResult.contains(person1));
        Assert.assertTrue(listResult.contains(person2));
        Assert.assertEquals(listResult.size(), 2);
    }
}
