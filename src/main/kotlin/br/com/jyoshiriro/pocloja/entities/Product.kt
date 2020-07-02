package br.com.jyoshiriro.pocloja.entities

import org.hibernate.validator.constraints.Length
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "produto")
class Product : AbstractSimpleIdIdentityEntity<Long>() {

    @Column(name = "nome", length = 100, nullable = false)
    open var name: @NotEmpty @Length(min = 3, max = 100) String? = null

    @NotEmpty @Min(0)
    @Column(name = "preco", nullable = false)
    open var price: BigDecimal? = null
}