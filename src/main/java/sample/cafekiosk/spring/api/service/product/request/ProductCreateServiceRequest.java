package sample.cafekiosk.spring.api.service.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

public class ProductCreateServiceRequest {

    private ProductType type;
    private ProductSellingType sellingType;
    private String productName;
    private int price;

    @Builder
    private ProductCreateServiceRequest(ProductType type, ProductSellingType sellingType, String productName, int price) {
        this.type = type;
        this.sellingType = sellingType;
        this.productName = productName;
        this.price = price;
    }

    public Product toEntity(String latestProductNumber) {
        return Product.builder()
            .productNumber(latestProductNumber)
            .type(type)
            .sellingType(sellingType)
            .productName(productName)
            .price(price)
            .build();
    }
}
