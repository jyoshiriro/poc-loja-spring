package br.com.jyoshiriro.pocloja.controllers;

import br.com.jyoshiriro.pocloja.entities.Customer;
import br.com.jyoshiriro.pocloja.presenters.CustomerPresenter;
import br.com.jyoshiriro.pocloja.services.CustomerService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/customers")
public class CustomerController extends AbstractCrudController<CustomerService, Customer, Long> {

    @GetMapping("/")
    @ApiOperation("Retrieves customers filtered by name and/or date of birth")
    public ResponseEntity<Page<CustomerPresenter>> get(
            final Pageable pageable,
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth
            ) {

        Page<Customer> customers = this.service.findAll(Example.of(new Customer(name, dateOfBirth)), pageable);

        return this.getPageOrNoContent(CustomerPresenter.createPage(customers));
    }

    @Override
    @ApiResponses(@ApiResponse(code = 200, message = MSG_OK, response = CustomerPresenterPage.class))
    public ResponseEntity<Page> getAll(final Pageable pageable) {
        return super.getAll(pageable);
    }

    @Override
    protected ResponseEntity getAllAction(final Pageable pageable) {
        return this.getPageOrNoContent(this.service.findAllPresenters(pageable));
    }

    @Override
    @ApiResponses(@ApiResponse(code = 200, message = MSG_OK, response = CustomerPresenter.class))
    public ResponseEntity<Customer> getOne(Long id) {
        return super.getOne(id);
    }

    @Override
    protected ResponseEntity getOneAction(Long id) {
        return ResponseEntity.of(this.service.findByIdPresenter(id));
    }
}

@ApiModel("Page<Customer>")
interface CustomerPresenterPage extends Page<CustomerPresenter> { }