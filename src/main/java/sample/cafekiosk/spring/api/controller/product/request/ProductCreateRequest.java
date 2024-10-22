package sample.cafekiosk.spring.api.controller.product.request;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductCreateRequest {

    private ProductType type;
    private ProductSellingType sellingType;
    private String productName;
    private int price;

    @Builder
    private ProductCreateRequest(ProductType type, ProductSellingType sellingType, String productName, int price) {
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
