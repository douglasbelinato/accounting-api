package com.accounting.accountingapi.interfaces.rest;

import com.accounting.accountingapi.interfaces.rest.event.ResourceCreatedEvent;
import com.accounting.accountingapi.infrastructure.exception.dto.ErrorResponseDTO;
import com.accounting.accountingapi.interfaces.rest.mapper.PersonMapper;
import com.accounting.accountingapi.interfaces.rest.converter.ResourcePartialUpdateConverter;
import com.accounting.accountingapi.interfaces.rest.dto.PersonDTO;
import com.accounting.accountingapi.interfaces.rest.dto.PersonPartialUpdateDTO;
import com.accounting.accountingapi.application.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
@Tag(name = "Person", description = "Operations for person")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ResourcePartialUpdateConverter<PersonDTO, PersonPartialUpdateDTO> personPartialUpdateConverter;


    @Operation(summary = "List people", description = "List all people saved in our database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search was successful",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        return ResponseEntity.ok().body(personMapper.fromPersonModelListToPersonDtoList(personService.findAll()));
    }


    @Operation(summary = "Find by id", description = "Find a person by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search was successful",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Person not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@Parameter(description = "Person id")
                                                  @PathVariable("id") Long id) {
        var person = personService.findById(id);
        return ResponseEntity.ok().body(personMapper.fromPersonModelToPersonDto(person));
    }


    @Operation(summary = "Save person", description = "Save a new person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New person successfully saved",
                    content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error - Please verify response description for more details",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<PersonDTO> save(@Valid @RequestBody PersonDTO personDTO, HttpServletResponse response) {
        var personSaved = personMapper.fromPersonModelToPersonDto(personService.save(personMapper.fromPersonDtoToPersonModel(personDTO)));

        applicationEventPublisher.publishEvent(new ResourceCreatedEvent(this, response, personSaved.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(personSaved);
    }


    @Operation(summary = "Delete person", description = "Delete a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Person successfully deleted",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Error - Please verify response description for more details",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
         personService.delete(id);
    }


    @Operation(summary = "Update person partially", description = "Update specific fields of a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Person successfully updated",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Error - Please verify response description for more details",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long id, @RequestBody Map<String, Object> jsonMapResourceUpdated) throws JsonProcessingException {
        var personDTO = personMapper.fromPersonModelToPersonDto(personService.findById(id));
        PersonDTO personUpdatedDTO = personPartialUpdateConverter.applyFieldsUpdatedToResource(jsonMapResourceUpdated, personDTO, PersonDTO.class, PersonPartialUpdateDTO.class);

        personService.update(personMapper.fromPersonDtoToPersonModel(personUpdatedDTO));
    }


}
