package br.com.jyoshiriro.pocloja.controllers

import br.com.jyoshiriro.pocloja.entities.Customer
import br.com.jyoshiriro.pocloja.presenters.CustomerPresenter
import br.com.jyoshiriro.pocloja.repositories.CustomerRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Example
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@SpringBootTest(classes = CustomerController)
class CustomerControllerTest extends Specification {

    @Autowired
    CustomerController controller

    @SpringBean
    CustomerRepository repository = Mock()

    def 'POST /clientes bem sucedida deve retornar status 201 com header de location'() {
        given: 'Um novo cliente'
        def novoCliente = new Customer(name: 'Tanto faz')

        and: 'Configurando o repository para salvar dando um novo id ao cliente'
        repository.save(novoCliente) >> {
            novoCliente.id = 99
            return novoCliente
        }

        when: 'Solicitar a execução do postCliente() com o novoCliente'
        def resposta = controller.postCliente(novoCliente)

        then: 'O status de resposta deve ser 201'
        resposta.statusCode == HttpStatus.CREATED

        and: 'Deve haver um cabeçalho "location" que termina com o id do novo cliente'
        resposta.headers.get('location').first().endsWith("/${novoCliente.id}")
    }

    @Unroll
    def 'GET /clientes/#id deve retornar status #status com corpo #corpo'() {
        setup: 'Configurando o repository encontrar ou não um cliente'
        repository.findById(id) >> Optional.ofNullable(cliente)

        expect: 'O corpo deve ser o apropriado'
        def resposta = controller.getCliente(id.toLong())
        if (cliente) {
            resposta.body == cliente
        } else {
            resposta.body == null
        }

        and: 'O status deve ser o apropriado'
        resposta.statusCode == status

        where:
        id | cliente                                                             | status               | corpo
        1  | new Customer(id:1, name:'a',cpf: '9', dateOfBirth: LocalDate.now()) | HttpStatus.OK        | new CustomerPresenter(cliente)
        2  | null                                                                | HttpStatus.NOT_FOUND | null
    }

    @Unroll
    def 'GET /clientes com nome #nome e CPF #cpf deve retornar a lista com #encontrados correta e status #status'() {
        setup: 'Configurando o repository para se retornar uma lista cmo se existisse um banco de dados'
        repository.findAll(_ as Example, _ as Pageable) >> { example, pageable->
            def lista = []
            encontrados.times { lista.add(Mock(Customer)) }
            PageImpl<Customer> pageRepository = Mock()
            pageRepository.getContent() >> lista
            PageImpl<CustomerPresenter> pagePresenter = Mock()
            pagePresenter.getContent() >> lista.collect{GroovyMock(CustomerPresenter)}
            pageRepository.isEmpty() >> (!encontrados)
            pageRepository.map(_) >> pagePresenter

            return pageRepository
        }

        expect: 'O corpo deve ser o apropriado'
        def resposta = controller.getCliente(nome, cpf, Mock(Pageable))
        if (encontrados) {
            resposta.body.content.size() == encontrados
        } else {
            resposta.body == null
        }

        and: 'O status deve ser o apropriado'
        resposta.statusCode == status

        where:
        nome | cpf  | encontrados | status
        'a'  | null | 1           | HttpStatus.OK
        'b'  | null | 3           | HttpStatus.OK
        'c'  | null | 0           | HttpStatus.NO_CONTENT
        null | '11' | 1           | HttpStatus.OK
        null | '22' | 0           | HttpStatus.NO_CONTENT
        null | null | 5           | HttpStatus.OK
    }

    @Unroll
    def 'PUT /clientes/#id deve retornar status #status'() {
        setup: 'Configurando o repository encontrar ou não um cliente'
        repository.existsById(id) >> existe

        expect: 'O status deve ser o apropriado'
        def resposta = controller.putCliente(id.toLong(), cliente)
        resposta.statusCode == status

        where:
        id | cliente             | existe | status
        1  | new Customer(id:99) | true   | HttpStatus.OK
        2  | new Customer(id:88) | false  | HttpStatus.NOT_FOUND
    }

    @Unroll
    def 'PATCH /clientes/#id deve retornar status #status'() {
        setup: 'Configurando o repository encontrar ou não um cliente'
        repository.findById(id) >> Optional.ofNullable(existe ? cliente : null)

        expect: 'O status deve ser o apropriado'
        def resposta = controller.patchCliente(id.toLong(), cliente)
        resposta.statusCode == status

        and: 'Caso o cliente exista, o id deve ter sido atualizado'
        if (existe) {
            cliente.id == id
        }

        where:
        id | cliente                 | existe | status
        1  | new Customer(name: 'a') | true   | HttpStatus.OK
        2  | new Customer(name: 'b') | false  | HttpStatus.NOT_FOUND
    }


    @Unroll
    def 'DELETE /clientes/#id deve retornar status #status'() {
        setup: 'Configurando o repository encontrar ou não um cliente'
        repository.existsById(id) >> existe

        expect: 'O status deve ser o apropriado'
        def resposta = controller.deleteCliente(id.toLong())
        resposta.statusCode == status

        where:
        id | existe | status
        1  | true   | HttpStatus.OK
        2  | false  | HttpStatus.NOT_FOUND
    }
}
