package cz.muni.fi.xgdovin.dao.utils;

import cz.muni.fi.xgdovin.dao.domain.DomainObject;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QueryValidator {

    public void validateQueryParameters(Class<? extends DomainObject> clazz, Map<String, Object> properties) {
        List<String> classFields = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        for (String property : properties.keySet()) {
            if (!classFields.contains(property)) {
                throw new IllegalArgumentException(
                        "Impossible to find property \"" + property
                        + "\" in object of class \"" + clazz.getName()
                        + "\". Possible fields are \"" + classFields +  "\""
                );
            } else {
                try {
                    Field field = clazz.getDeclaredField(property);
                    Object propertyValue = properties.get(property);
                    //it is sufficient that this side is assignable in order to allow implicit casting and autoboxing
                    if (!ClassUtils.isAssignable(propertyValue.getClass(), field.getType(), true)) {
                        throw new IllegalArgumentException(
                                "Type mismatch: property \"" + property
                                        + "\" in object of class \"" + clazz.getName()
                                        + "\" is of \"" + field.getType() + "\" type. "
                                        + " You supplied value \"" + propertyValue +  "\""
                                        + ", which is of \"" + propertyValue.getClass() +  "\" type."
                        );
                    }
                }
                //will never happen
                catch (NoSuchFieldException e) {
                    throw new IllegalArgumentException(
                            "Impossible to find property \"" + property
                                    + "\" in object of class \"" + clazz.getName()
                                    + "\". Possible fields are \"" + classFields +  "\""
                    );
                }
            }
        }
    }
}
