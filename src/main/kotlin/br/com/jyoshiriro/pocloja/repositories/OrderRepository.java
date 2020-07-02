package br.com.jyoshiriro.pocloja.repositories;

import br.com.jyoshiriro.pocloja.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
