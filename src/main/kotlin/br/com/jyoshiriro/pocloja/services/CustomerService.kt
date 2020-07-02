package br.com.jyoshiriro.pocloja.services

import br.com.jyoshiriro.pocloja.entities.Customer
import br.com.jyoshiriro.pocloja.presenters.CustomerPresenter
import br.com.jyoshiriro.pocloja.repositories.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService : CrudService<CustomerRepository, Customer, Long>() {

    fun findAllPresenters(paginacao: Pageable): Page<CustomerPresenter> {
        return CustomerPresenter.createPage(this.findAll(paginacao))
    }

    fun findByIdPresenter(id: Long): Optional<CustomerPresenter> {
        return CustomerPresenter.createOptional(this.findById(id))
    }

}