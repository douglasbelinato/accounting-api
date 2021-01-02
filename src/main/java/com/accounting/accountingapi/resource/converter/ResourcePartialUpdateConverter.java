package com.accounting.accountingapi.resource.converter;

import com.accounting.accountingapi.exception.EmptyBodyException;
import com.accounting.accountingapi.util.MessageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;

@Component
public class ResourcePartialUpdateConverter<R, P> {

    @Autowired
    private Validator validator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageUtil messageUtil;

    public R applyFieldsUpdatedToResource(Map<String, Object> fieldsUpdated, R resourceToBeUpdate,
                                          Class<R> resourceToBeUpdateType, Class<P> partialUpdateModelType) throws JsonProcessingException {
        R resourceToBeUpdateClone = deepCopy(resourceToBeUpdate, resourceToBeUpdateType);
        checkResourceNotEmpty(fieldsUpdated);
        checkFieldsValid(fieldsUpdated, partialUpdateModelType);
        convert(fieldsUpdated, resourceToBeUpdateClone);
        checkExistsFieldsConstraintViolations(resourceToBeUpdateClone);

        return resourceToBeUpdateClone;
    }

    private R deepCopy(R resourceToBeUpdate, Class<R> resourceToBeUpdateType) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(resourceToBeUpdate);
        return objectMapper.readValue(json, resourceToBeUpdateType);
    }

    private void checkResourceNotEmpty(Map<String, Object> fieldsUpdated) {
        if (fieldsUpdated.isEmpty()) {
            throw new EmptyBodyException();
        }
    }

    private void checkFieldsValid(Map<String, Object> fieldsUpdated, Class<P> partialUpdateModelType) {
        objectMapper.convertValue(fieldsUpdated, partialUpdateModelType);
    }

    private R convert(Map<String, Object> fieldsUpdated, R resourceToBeUpdate) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(fieldsUpdated);
        return objectMapper.readerForUpdating(resourceToBeUpdate).readValue(json);
    }

    private void checkExistsFieldsConstraintViolations(R resourceUpdated) {
        Set<ConstraintViolation<R>> violations = validator.validate(resourceUpdated);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}