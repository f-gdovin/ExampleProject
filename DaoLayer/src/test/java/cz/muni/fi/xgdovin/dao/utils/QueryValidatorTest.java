package cz.muni.fi.xgdovin.dao.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dao-context.xml")
public class QueryValidatorTest {

    @Autowired
    private QueryValidator queryValidator;

    @Test
    public void whenNoProperty_thenValidationPasses() {
        Map<String, Object> properties = new HashMap<>();

        queryValidator.validateQueryParameters(TestClass.class, properties);
    }

    @Test
    public void whenValidProperty_thenValidationPasses() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", 5L);

        queryValidator.validateQueryParameters(TestClass.class, properties);
    }

    @Test
    public void whenMoreValidProperties_thenValidationPasses() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", 5L);
        properties.put("someBool", false);

        queryValidator.validateQueryParameters(TestClass.class, properties);
    }

    @Test
    public void whenMoreValidPropertiesUnboxingNeeded_thenValidationPasses() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", 5L);
        properties.put("someBool", Boolean.FALSE);

        queryValidator.validateQueryParameters(TestClass.class, properties);
    }

    @Test
    public void whenMoreValidPropertiesImplicitCastNeeded_thenValidationPasses() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", 5L);
        properties.put("someDouble", 1);

        queryValidator.validateQueryParameters(TestClass.class, properties);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidPropertyName_thenExceptionIsThrown() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id2", 5L);

        queryValidator.validateQueryParameters(TestClass.class, properties);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidPropertyType_thenExceptionIsThrown() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", "string instead of long");

        queryValidator.validateQueryParameters(TestClass.class, properties);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidPropertyTypeExplicitCastNeeded_thenExceptionIsThrown() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", 5L);
        properties.put("someInt", 1.5);

        queryValidator.validateQueryParameters(TestClass.class, properties);
        fail();
    }
}