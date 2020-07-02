package br.com.jyoshiriro.pocloja.presenters

import br.com.jyoshiriro.pocloja.entities.Customer
import io.swagger.annotations.ApiModel
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.time.Period
import java.util.*

@ApiModel("Customer")
data class CustomerPresenter (val id:Long?, val name:String?, val cpf:String?, val dateOfBirth:LocalDate?) {

    val idade: Int
        get() = Period.between(dateOfBirth, LocalDate.now()).getYears()

    constructor(entidade: Customer) : this(entidade.id, entidade.name, entidade.cpf, entidade.dateOfBirth)

    companion object {
        @JvmStatic
        fun createPage(listaEntidade: Page<Customer>): Page<CustomerPresenter> = listaEntidade.map{ CustomerPresenter(it) }

        @JvmStatic
        fun createOptional(optional:Optional<Customer>): Optional<CustomerPresenter> {
            return if (optional.isEmpty) {
                Optional.ofNullable(null)
            } else {
                Optional.of(CustomerPresenter(optional.get()))
            }
        }
    }

}