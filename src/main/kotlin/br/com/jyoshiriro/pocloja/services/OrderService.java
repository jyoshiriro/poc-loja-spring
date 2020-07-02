package br.com.jyoshiriro.pocloja.services;

import br.com.jyoshiriro.pocloja.entities.Order;
import br.com.jyoshiriro.pocloja.repositories.OrderRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends CrudService<OrderRepository, Order, Long> {

    @Override
    public void create(@NotNull Order entity) {
        // TODO: gerar o número do pedido
        super.create(entity);
    }
}
