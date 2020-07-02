package br.com.jyoshiriro.pocloja.presenters

import br.com.jyoshiriro.pocloja.entities.Customer
import org.springframework.data.domain.PageImpl
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

class CustomerPresenterTest extends Specification {

    @Shared
    def nascimento = LocalDate.parse('1981-07-06')
    def cliente = new Customer(id:100, name: 'Zé Ramalho', cpf: '123', dateOfBirth: nascimento)

    def 'Instância criada com Entidade deve ter os valores corretos'() {
        when: 'Criar uma nova instância do presenter com um cliente'
        def presenter = new CustomerPresenter(cliente)

        then: 'Todos os atributos de cliente devem ser iguais aos que deveriam ser'
        presenter.nome == cliente.name
        presenter.cpf == cliente.cpf
        presenter.dataNascimento == cliente.dateOfBirth
    }

    def 'A idade deve ser calculada corretamente'() {
        when: 'Criar uma nova instância do presenter com esse cliente'
        def presenter = new CustomerPresenter(cliente)

        then: 'Todos os atributos de cliente devem ser iguais aos que deveriam ser'
        presenter.dataNascimento.until(LocalDate.now()).years == presenter.idade
    }

    def 'Método criarLista() deve criar lista com tamanho e valores certos'() {
        given: 'Lista original de clientes'
        def clientesOriginal = [cliente, new Customer(id:22, name: 'Ana', cpf: '999', dateOfBirth: LocalDate.now())]
        def entidades = new PageImpl<Customer>(clientesOriginal)

        when: 'Solicitando a lista de presenters'
        def presenters = CustomerPresenter.createPage(entidades)

        then: 'A lista de presenters deve ter o mesmo tamanho da de entidades'
        presenters.size == entidades.size

        and: 'Os valores de todos os itens devem estar corretos'
        presenters.eachWithIndex {presenter,i ->
            def entidade = entidades[i]
            assert presenter.id == entidade.id
            assert presenter.nome == entidade.name
            assert presenter.cpf == entidade.cpf
            assert presenter.dataNascimento == entidade.dateOfBirth
        }

    }
}
