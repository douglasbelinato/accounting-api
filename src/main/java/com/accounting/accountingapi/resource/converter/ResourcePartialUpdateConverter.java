package com.accounting.accountingapi.resource.validation;

import com.accounting.accountingapi.exception.EmptyBodyException;
import com.accounting.accountingapi.resource.dto.PersonPartialUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.SmartValidator;

import javax.validation.Validator;
import java.util.Map;

@Component
public class ResourcePartialUpdateValidator<T> {

    @Autowired
    private Validator validator;

    private SmartValidator smartValidator;

    public void fulfillResourceWithValues(Map<String, Object> jsonMapResourceUpdated, T resourceToBeUpdate, Class<T> type) throws Throwable {
        if (jsonMapResourceUpdated.isEmpty()) {
            throw new EmptyBodyException();
        }

        try {
            final T resourceUpdated = new ObjectMapper().convertValue(jsonMapResourceUpdated, type);

            jsonMapResourceUpdated.forEach((propertyName, propertyValue) -> {
                var field = ReflectionUtils.findField(type, propertyName);
                field.setAccessible(true);

                var newValue = ReflectionUtils.getField(field, resourceUpdated);

                ReflectionUtils.setField(field, resourceToBeUpdate, newValue);
            });

        } catch (IllegalArgumentException e) {
            // ????
        }
    }

    private void checkResourceNotEmpty(Map<String, Object> jsonMapResourceUpdated) {
        if (jsonMapResourceUpdated.isEmpty()) {
            throw new EmptyBodyException();
        }
    }

    private void checkFieldsValid(Map<String, Object> jsonMapResourceUpdated) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.convertValue(jsonMapResourceUpdated, PersonPartialUpdateDTO.class);

        String json = new ObjectMapper().writeValueAsString(jsonMapResourceUpdated);

        var personDTO = personMapper.fromPersonModelToPersonDto(personService.findById(id));
    }

    private void checkExistsFieldsConstraintViolations() {

    }
}