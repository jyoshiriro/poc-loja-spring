package br.com.jyoshiriro.pocloja.entities

import br.com.caelum.stella.bean.validation.CPF
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "cliente")
open class Customer : AbstractSimpleIdIdentityEntity<Long> {

    @NotEmpty
    @Length(min = 5, max = 50)
    @Column(name = "nome", length = 50, nullable = false)
    open var name: String? = null

    @CPF
    @NotEmpty
    @Column(length = 11, nullable = false)
    open var cpf: String? = null

    @NotNull
    @Column(name="data_nascimento", nullable = false)
    open var dateOfBirth: LocalDate? = null

    constructor() {}

    constructor(name: String?, dateOfBirth: LocalDate?) {
        this.name = name
        this.dateOfBirth = dateOfBirth
    }

    override fun fromParams(params: Map<String, Any>) {
        this.name = params["name"] as String
        this.cpf = params["cpf"] as String
        this.dateOfBirth = LocalDate.parse(params["dateOfBirth"] as String)
    }

}