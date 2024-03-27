package test.connect.mssql.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import test.connect.mssql.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
