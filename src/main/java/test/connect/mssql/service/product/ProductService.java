package test.connect.mssql.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.connect.mssql.dto.product.FindProductResponse;
import test.connect.mssql.dto.product.ReceivingRequest;
import test.connect.mssql.entity.Product;
import test.connect.mssql.repository.product.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product receiving(ReceivingRequest receivingRequest){

        Product product = new Product(receivingRequest);
        productRepository.save(product);

        return product;
    }

    public List<FindProductResponse> findAll(){
        List<Product> findProducts = productRepository.findAll();

        List<FindProductResponse> products = new ArrayList<>();
        for (Product findProduct : findProducts) {
            products.add(new FindProductResponse(findProduct));
        }

        return products;
    }

}
