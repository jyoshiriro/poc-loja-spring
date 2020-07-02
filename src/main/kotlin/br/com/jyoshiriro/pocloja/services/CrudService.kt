package br.com.jyoshiriro.pocloja.services

import br.com.jyoshiriro.pocloja.entities.AbstractSimpleIdIdentityEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import java.util.*

abstract class CrudService<R: JpaRepository<E, I>, E:AbstractSimpleIdIdentityEntity<I>, I:Serializable> {

    @Autowired
    private lateinit var repository:R

    open fun create(entity: E) {
        this.repository.save(entity)
    }

    open fun update(id:I, entity: E) {
        entity.id = id
        this.repository.save(entity)
    }

    open fun findById(id:I):Optional<E> {
        return this.repository.findById(id)
    }

    open fun existsById(id:I):Boolean {
        return this.repository.existsById(id)
    }

    open fun <S:E> findAll(example: Example<S>, pageable: Pageable): Page<S> {
        return this.repository.findAll(example, pageable)
    }

    open fun findAll(paginacao: Pageable): Page<E> {
        return this.repository.findAll(paginacao)
    }

    open fun deleteById(id: I) {
        this.repository.deleteById(id)
    }

}