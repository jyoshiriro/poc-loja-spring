package br.com.jyoshiriro.pocloja.controllers;

import br.com.jyoshiriro.pocloja.entities.Product;
import br.com.jyoshiriro.pocloja.services.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController extends AbstractCrudController<ProductService, Product, Long> {


}
