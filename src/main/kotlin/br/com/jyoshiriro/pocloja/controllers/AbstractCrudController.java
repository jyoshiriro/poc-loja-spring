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

    private final static String URI_ID = "/{id}";
    private final static String MSG_CREATE_NEW = "Criar novo";
    private final static String MSG_INVALID_FIELDS = "Um ou mais campos inválidos";
    private final static String MSG_CREATED = "Criado com sucesso";
    private final static String HEADER_LOCATION = "Location";
    private final static String HEADER_LOCATION_VALOR = "URL do recurso recém criado";

    @PostMapping
    @ApiOperation(MSG_CREATE_NEW)
    @ApiResponses({
        @ApiResponse(code = 400, response = ErrosPresenter.class, message = MSG_INVALID_FIELDS),
        @ApiResponse(code = 201, message = MSG_CREATED,
                responseHeaders = @ResponseHeader(name = HEADER_LOCATION, description = HEADER_LOCATION_VALOR))
    })
    public ResponseEntity create(@RequestBody @ApiParam("JSON de novo cliente") final E newInstance) {
        return createTrigger(newInstance);
    }

    protected ResponseEntity createTrigger(final E newInstance) {
        this.service.create(newInstance);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(URI_ID)
                .buildAndExpand(newInstance.getId())
                .toUri();

        return created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable final I id) {
        return this.getOneTrigger(id);
    }

    protected ResponseEntity getOneTrigger(final I id) {
        return of(this.service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page> getAll(final Pageable pageable) {
        return getAllTrigger(pageable);
    }

    protected ResponseEntity<Page> getAllTrigger(final Pageable pageable) {
        Page page = this.service.findAll(pageable);

        return page.isEmpty() ? noContent().build() : of(Optional.of(page));
    }


    @PutMapping("/{id}")
    @ApiResponses(@ApiResponse(code = 400, response = ErrosPresenter.class, message = MSG_INVALID_FIELDS))
    public ResponseEntity update(@PathVariable final I id, @RequestBody final E updatedEntity) {
        return this.updateTrigger(id, updatedEntity);
    }

    protected ResponseEntity updateTrigger(final I id, final E updatedEntity) {
        if (this.service.existsById(id)) {
            this.service.update(id, updatedEntity);
            return ok().build();
        } else {
            return notFound().build();
        }
    }

    @DeleteMapping("/{id}")
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
