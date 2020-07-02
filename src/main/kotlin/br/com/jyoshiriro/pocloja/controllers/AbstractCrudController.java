package br.com.jyoshiriro.pocloja.controllers;

import br.com.jyoshiriro.pocloja.entities.AbstractSimpleIdIdentityEntity;
import br.com.jyoshiriro.pocloja.presenters.ErrosPresenter;
import br.com.jyoshiriro.pocloja.services.CrudService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@CrossOrigin
@Validated
public abstract class AbstractCrudController
        <S extends CrudService, E extends AbstractSimpleIdIdentityEntity, I extends Serializable> {

    @Autowired
    protected S service;

    protected final static String URI_ID = "/{id}";
    protected final static String MSG_OK = "OK";
    protected final static String MSG_CREATE_NEW = "Creates registry";
    protected final static String MSG_UPDATE = "Updates registry";
    protected final static String MSG_RETIREVE_ONE = "Retrieves registry by id";
    protected static final String MSG_RETIREVE_ALL = "Retrieves all registries";
    protected final static String MSG_DELETE_ONE = "Deletes registry by id";
    protected final static String MSG_JSON_NEW = "New registry JSON";
    protected final static String MSG_INVALID_FIELDS = "Invalid field(s)";
    protected final static String MSG_CREATED = "Succesfully created!";
    protected final static String HEADER_LOCATION = "Location";
    protected final static String HEADER_LOCATION_VALOR = "Created resource URL";

    @PostMapping
    @ApiOperation(MSG_CREATE_NEW)
    @ApiResponses({
        @ApiResponse(code = 400, response = ErrosPresenter.class, message = MSG_INVALID_FIELDS),
        @ApiResponse(code = 201, message = MSG_CREATED,
                responseHeaders = @ResponseHeader(name = HEADER_LOCATION, description = HEADER_LOCATION_VALOR))
    })
    public ResponseEntity create(@RequestBody @ApiParam(MSG_JSON_NEW) final E newInstance) {
        return createAction(newInstance);
    }

    protected ResponseEntity createAction(final E newInstance) {
        this.service.create(newInstance);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(URI_ID)
                .buildAndExpand(newInstance.getId())
                .toUri();

        return created(location).build();
    }

    @GetMapping("/{id}")
    @ApiOperation(MSG_RETIREVE_ONE)
    public ResponseEntity getOne(@PathVariable final I id) {
        return this.getOneAction(id);
    }

    protected ResponseEntity getOneAction(final I id) {
        return of(this.service.findById(id));
    }

    @GetMapping
    @ApiOperation(MSG_RETIREVE_ALL)
    public ResponseEntity<Page> getAll(final Pageable pageable) {
        return getAllAction(pageable);
    }

    protected ResponseEntity<Page> getAllAction(final Pageable pageable) {
        Page page = this.service.findAll(pageable);

        return this.getPageOrNoContent(page);
    }


    @PutMapping("/{id}")
    @ApiOperation(MSG_UPDATE)
    @ApiResponses(@ApiResponse(code = 400, response = ErrosPresenter.class, message = MSG_INVALID_FIELDS))
    public ResponseEntity update(@PathVariable final I id, @RequestBody final E updatedEntity) {
        return this.updateAction(id, updatedEntity);
    }

    protected ResponseEntity updateAction(final I id, final E updatedEntity) {
        if (this.service.existsById(id)) {
            this.service.update(id, updatedEntity);
            return ok().build();
        } else {
            return notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(MSG_DELETE_ONE)
    public ResponseEntity delete(@PathVariable final I id) {
        return this.deleteTrigger(id);
    }

    public ResponseEntity deleteTrigger(final I id) {
        if (this.service.existsById(id)) {
            this.service.deleteById(id);
            return ok().build();
        } else {
            return notFound().build();
        }
    }

    protected ResponseEntity getPageOrNoContent(final Page page) {
        return page.isEmpty() ? noContent().build() : of(Optional.of(page));
    }
}
