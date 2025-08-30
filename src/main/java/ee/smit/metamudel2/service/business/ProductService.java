package ee.smit.metamudel2.service.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ee.smit.metamudel2.model.business.Product;
import ee.smit.metamudel2.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    public List<Product> getProductsByBrand(String brand) {
        List<Product> products = productRepository.getProductsByBrand(brand);
        if (products.size() == 0) {
            return null;
        }
        return products;
    }

}
