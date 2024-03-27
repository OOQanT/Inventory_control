package test.connect.mssql.controller.product;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.connect.mssql.dto.ApiResponse;
import test.connect.mssql.dto.product.FindProductResponse;
import test.connect.mssql.dto.product.ReceivingRequest;
import test.connect.mssql.entity.Product;
import test.connect.mssql.service.product.ProductService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/receiving")
    public ResponseEntity<ApiResponse> product_receiving(@Validated @RequestBody ReceivingRequest receivingRequest,
                                                         BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(ApiResponse.createFail(bindingResult));
        }

        Product receiving = productService.receiving(receivingRequest);


        return ResponseEntity.status(HttpServletResponse.SC_CREATED)
                .body(ApiResponse.createSuccess(receiving));

    }

    @GetMapping("/allProduct")
    public ResponseEntity<ApiResponse> all_product(){
        List<FindProductResponse> allProducts = productService.findAll();

        return ResponseEntity.status(HttpServletResponse.SC_OK)
                .body(ApiResponse.createSuccess(allProducts));
    }
}
