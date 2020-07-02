package br.com.jyoshiriro.pocloja.entities

import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractSimpleIdIdentityEntity<I:Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id:I? = null

    open fun fromParams(params:Map<String, Any>) {
        throw NotImplementedError("fromParams(params:Map<String, Any>) required but not implemented")
    }

}