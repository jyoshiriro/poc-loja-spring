package br.com.jyoshiriro.pocloja.presenters

import br.com.jyoshiriro.pocloja.entities.Customer
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.time.Period
import java.util.*

data class CustomerPresenter (val id:Long?, val nome:String?, val cpf:String?, val dataNascimento:LocalDate?) {

    val idade: Int
        get() = Period.between(dataNascimento, LocalDate.now()).getYears()

    constructor(entidade: Customer) : this(entidade.id as Long?, entidade.name, entidade.cpf, entidade.dateOfBirth)

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