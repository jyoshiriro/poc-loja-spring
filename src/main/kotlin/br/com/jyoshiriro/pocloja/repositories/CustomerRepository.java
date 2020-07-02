package br.com.jyoshiriro.pocloja.repositories;

import br.com.jyoshiriro.pocloja.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findByDateOfBirthAndNameContains(String cpf, String nome, Pageable pageable); // não precisava, apenas para constar

    @Query("select c from Customer c where c.cpf = ?1 and c.dateOfBirth = ?2")
    Page<Customer> findByDateOfBirthAndNameContains2(String name, LocalDate dateOfBirth, Pageable pageable); // não precisava, apenas para constar

    @Query("select c from Customer c where c.name like '%'+:name+'%' and c.dateOfBirth = :birth")
    Page<Customer> findByDateOfBirthAndNameContains3(@Param("name") String name, @Param("birth") LocalDate dateOfBirth, Pageable pageable); // não precisava, apenas para constar

}
