package sample.cafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductSellingType {

    SELLING("판매중"),
    STOP_SELLING("판매중지"),
    HOLD("판매보류");

    private final String description;

    public static List<ProductSellingType> forDisplay() {
        return List.of(SELLING, HOLD);
    }
}
