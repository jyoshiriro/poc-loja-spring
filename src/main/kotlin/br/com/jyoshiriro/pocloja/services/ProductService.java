package br.com.jyoshiriro.pocloja.services;

import br.com.jyoshiriro.pocloja.entities.Product;
import br.com.jyoshiriro.pocloja.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends CrudService<ProductRepository, Product, Long> {
}
