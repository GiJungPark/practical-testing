package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.HOLD;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.STOP_SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn() {
        // given
        Product americano = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingType(SELLING)
                .productName("아메리카노")
                .price(4000)
                .build();

        Product latte = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingType(HOLD)
                .productName("카페라떼")
                .price(4500)
                .build();

        Product shavedIce = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingType(STOP_SELLING)
                .productName("팥빙수")
                .price(7000)
                .build();

        productRepository.saveAll(List.of(americano, latte, shavedIce));

        // then
        List<Product> products = productRepository.findAllBySellingTypeIn(List.of(SELLING, HOLD));

        // when
        assertThat(products).hasSize(2)
                .extracting("productNumber", "productName", "sellingType")
                .containsExactlyInAnyOrder(
                  tuple("001", "아메리카노", SELLING),
                  tuple("002", "카페라떼", HOLD)
                );
    }
    
    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        // given
        Product americano = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingType(SELLING)
                .productName("아메리카노")
                .price(4000)
                .build();

        Product latte = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingType(HOLD)
                .productName("카페라떼")
                .price(4500)
                .build();

        Product shavedIce = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingType(STOP_SELLING)
                .productName("팥빙수")
                .price(7000)
                .build();
        productRepository.saveAll(List.of(americano, latte, shavedIce));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "productName", "sellingType")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }
}