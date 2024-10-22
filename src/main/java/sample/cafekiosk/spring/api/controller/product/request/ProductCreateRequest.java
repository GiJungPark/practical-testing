package sample.cafekiosk.spring.api.controller.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@NoArgsConstructor
@Getter
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;

    @NotNull(message = "상품 판매 상태는 필수입니다.")
    private ProductSellingType sellingType;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String productName;

    @Positive(message = "상품 가격은 양수여야 합니다.")
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
