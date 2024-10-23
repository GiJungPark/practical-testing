package sample.cafekiosk.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.BAKERY;
import static sample.cafekiosk.spring.domain.product.ProductType.BOTTLE;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 10, 23, 0, 0);

        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("003", BOTTLE, 3000);
        Product product3 = createProduct("002", BAKERY, 5000);
        List<Product> products = productRepository.saveAll(List.of(product1, product2, product3));

        Order order1 = Order.create(products, LocalDateTime.of(2024, 10, 22, 23, 59, 59));
        Order order2 = Order.create(products, now);
        Order order3 = Order.create(products, LocalDateTime.of(2024, 10, 23, 23, 59, 59));
        Order order4 = Order.create(products, LocalDateTime.of(2024, 10, 24, 0, 0));
        order1.completePayment();
        order2.completePayment();
        order3.completePayment();
        order4.completePayment();
        orderRepository.saveAll(List.of(order1, order2, order3, order4));

        // stubbing
        when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
            .thenReturn(true);

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2024, 10, 23), "test@gmail.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> mailSendHistories = mailSendHistoryRepository.findAll();
        assertThat(mailSendHistories).hasSize(1)
            .extracting("content")
            .contains("총 매출 합계는 18000원입니다.");
    }

    private Product createProduct(
        String productNumber, ProductType type, int price
    ) {
        return Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingType(SELLING)
            .productName("메뉴 이름")
            .price(price)
            .build();
    }
}