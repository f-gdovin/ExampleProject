package cz.muni.fi.xgdovin.dao.dao;

import cz.muni.fi.xgdovin.dao.domain.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.*;

@ContextConfiguration(locations = "classpath:dao-context.xml")
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;

    @Before
    public void setup(){
        user1 = new User();
        user1.setFirstName("Ferko");
        user1.setSurname("Mrkvicka");
        user1.setDescription("Najvacsi mykolog na svete");
        user1.setUsername("feri333");
        user1.setPassword("notAHash,ButWhatever");
        userDao.create(user1);

    }

    @Test
    public void createTest(){
        Map<String, Object> properties = new HashMap<>();
        properties.put("firstName", user1.getFirstName());
        properties.put("surname", user1.getSurname());
        
        User result = userDao.findByProperties(properties);
        Assert.assertTrue(result.equals(user1));
    }

    @Test(expected = PersistenceException.class)
    public void createNullFirstNameTest(){
        User invalidUser = new User();
        invalidUser.setSurname("Mrkvicka");
        invalidUser.setDescription("Najvacsi mykolog na svete");
        invalidUser.setUsername("feri333");
        invalidUser.setPassword("notAHash,ButWhatever");
        userDao.create(invalidUser);
    }

    @Test(expected = PersistenceException.class)
    public void createNullSurnameTest(){
        User invalidUser = new User();
        invalidUser.setFirstName("Ferko");
        invalidUser.setDescription("Najvacsi mykolog na svete");
        invalidUser.setUsername("feri333");
        invalidUser.setPassword("notAHash,ButWhatever");
        userDao.create(invalidUser);
    }

    @Test(expected = PersistenceException.class)
    public void createNullUserNameTest(){
        User invalidUser = new User();
        invalidUser.setFirstName("Ferko");
        invalidUser.setSurname("Mrkvicka");
        invalidUser.setDescription("Najvacsi mykolog na svete");
        invalidUser.setPassword("notAHash,ButWhatever");
        userDao.create(invalidUser);
    }

    @Test(expected = PersistenceException.class)
    public void createNullPasswordTest(){
        User invalidUser = new User();
        invalidUser.setFirstName("Ferko");
        invalidUser.setSurname("Mrkvicka");
        invalidUser.setDescription("Najvacsi mykolog na svete");
        invalidUser.setUsername("feri333");
        userDao.create(invalidUser);
    }

    @Test
    public void updateTest(){
        String firstName = "Ferko";
        String surname = "Mrkvicka";
        String newDescription = "I believe it should be spelled \"mYkolog\" :)";

        Map<String, Object> properties = new HashMap<>();
        properties.put("firstName", firstName);
        properties.put("surname", surname);

        User fromDB = userDao.findByProperties(properties);     //get some hunter from DB, check it truly exist there
        Assert.assertNotNull(fromDB);

        fromDB.setDescription(newDescription);
        userDao.update(fromDB);

        User updatedFromDB = userDao.findByUUid(fromDB.getUuid());     //reload same (hopefully updated) hunter by ID

        Assert.assertEquals(updatedFromDB.getUuid(), fromDB.getUuid());
        Assert.assertEquals(updatedFromDB.getFirstName(), firstName);
        Assert.assertEquals(updatedFromDB.getSurname(), surname);
        Assert.assertEquals(updatedFromDB.getDescription(), newDescription);
    }

    @Test
    public void findByUuid(){
        Map<String, Object> properties = new HashMap<>();
        properties.put("firstName", user1.getFirstName());
        properties.put("surname", user1.getSurname());
        
        UUID uuid = userDao.findByProperties(properties).getUuid();
        User result = userDao.findByUUid(uuid);
        Assert.assertTrue(result.equals(user1));
    }

    @Test
    public void deleteTest(){
        Map<String, Object> properties = new HashMap<>();
        properties.put("firstName", user1.getFirstName());
        properties.put("surname", user1.getSurname());
        
        UUID uuid = userDao.findByProperties(properties).getUuid();
        userDao.delete(user1);
        Assert.assertNull(userDao.findByUUid(uuid));
    }

    @Test
    public void findAllTest(){
        user2 = new User();
        user2.setFirstName("Jan");
        user2.setSurname("Slepy");
        user2.setDescription("Najhorsi mykolog na svete");
        user2.setUsername("janciBystrozraky");
        user2.setPassword("notAHash,ButWhatever...StillDifferentThanFeri'sHash");
        userDao.create(user2);

        List<User> listResult = userDao.findAll();

        Assert.assertTrue(listResult.contains(user1));
        Assert.assertTrue(listResult.contains(user2));
        Assert.assertEquals(listResult.size(), 2);
    }
}
