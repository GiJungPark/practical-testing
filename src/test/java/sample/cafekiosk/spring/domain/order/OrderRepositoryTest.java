package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.HOLD;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @DisplayName("  qw")
    @Test
    void findOrdersBy() {
        // givn
        LocalDateTime dateTime = LocalDateTime.of(2024, 10, 23, 20, 00);

        LocalDate date = dateTime.toLocalDate();
        Product americano = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product latte = createProduct("002", HANDMADE, SELLING, "카페라떼", 4500);
        List<Product> products = List.of(americano, latte);
        productRepository.saveAll(products);

        Order order = Order.create(products, dateTime);
        order.completePayment();
        orderRepository.save(order);

        // when
        List<Order> orders = orderRepository.findOrdersBy(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), OrderStatus.PAYMENT_COMPLETED);

        // then
        assertThat(orders).hasSize(1)
            .extracting("orderStatus", "registeredDataTime")
            .containsExactlyInAnyOrder(
                tuple(OrderStatus.PAYMENT_COMPLETED, dateTime)
            );
    }

    private Product createProduct(
        String productNumber, ProductType type, ProductSellingType sellingType, String productName, int price
    ) {
        return Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingType(sellingType)
            .productName(productName)
            .price(price)
            .build();
    }
}