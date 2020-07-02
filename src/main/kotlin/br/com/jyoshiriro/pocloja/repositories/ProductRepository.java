package br.com.jyoshiriro.pocloja.repositories;

import br.com.jyoshiriro.pocloja.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
