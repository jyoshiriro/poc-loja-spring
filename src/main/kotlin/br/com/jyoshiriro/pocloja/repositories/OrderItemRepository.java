package br.com.jyoshiriro.pocloja.repositories;

import br.com.jyoshiriro.pocloja.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
