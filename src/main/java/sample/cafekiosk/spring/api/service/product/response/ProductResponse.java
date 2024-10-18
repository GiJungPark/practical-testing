package sample.cafekiosk.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductResponse {

    private Long id;
    private String productNo;
    private ProductType type;
    private ProductSellingType sellingType;
    private String productName;
    private int price;

    @Builder
    private ProductResponse(Long id, String productNo, ProductType type, ProductSellingType sellingType, String productName, int price) {
        this.id = id;
        this.productNo = productNo;
        this.type = type;
        this.sellingType = sellingType;
        this.productName = productName;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productNo(product.getProductNo())
                .type(product.getType())
                .sellingType(product.getSellingType())
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();
    }
}
