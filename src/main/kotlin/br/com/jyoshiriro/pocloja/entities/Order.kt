package br.com.jyoshiriro.pocloja.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "pedido")
class Order : AbstractSimpleIdIdentityEntity<Long>() {

    @NotEmpty
    @Length(min = 1, max = 12)
    @Column(name = "numero", length = 12, nullable = false)
    open var number: String? = null

    @CreationTimestamp
    @Column(name = "data_pedido", nullable = false)
    open var orderDate: LocalDateTime? = null
}