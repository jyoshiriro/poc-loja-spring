package br.com.jyoshiriro.pocloja.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "item_pedido")
class OrderItem : AbstractSimpleIdIdentityEntity<Long>() {

    @ManyToOne
    open var order: Order? = null

    @ManyToOne
    open var customer: Customer? = null

    @ManyToOne
    open var product: Product? = null

    @NotEmpty
    @Min(0)
    @Column(name = "quantidade", nullable = false)
    open var amount: Int? = null
}