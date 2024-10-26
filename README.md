# CafeKiosk Application

## 요구사항

### 상품
- 상품은 `id`, `상품 번호`, `상품 타입`, `상품 판매 상태`, `상품 이름`, `상품 가격` 정보를 가진다.
- 키오스크 주문을 위한 상품 후보 리스트를 조회할 수 있다.
    - 상품 판매 상태가 판매중, 판매 보류인 상품만 조회할 수 있다.
- 관리자 페이지에서 신규 상품을 등록할 수 있다.
    - 상품 이름, 상품 타입, 상품 판매 상태, 상품 가격을 입력받는다.

### 주문
- 주문은 `주문 상태`, `주문 등록 시간`을 가진다.
- 주문의 총 금액을 계산할 수 있어야 한다.
- 상품 번호 리스트를 받아 주문을 생성할 수 있다.
- 주문 생성 시 재고 확인 및 개수 차감 후 생성해야 한다.  
  _(재고가 존재하는 상품 타입에 한하여)_

### 재고
- 재고는 `상품 번호`를 가진다.
- 재고가 존재하는 상품 타입은 병 음료, 베이커리이다.

---

## 테스트 작성

### Controller 계층 
`src/test/java/sample/cafekiosk/spring/controller`
- `@MockMvcTest`를 사용하여 단위 테스트 작성
  - `@MockMvcTest`를 사용한 이유:
    - Controller 계층은 클라이언트의 요청과 응답을 처리하는 책임을 가진다.
    - Service 빈을 불러와 통합 테스트를 작성하기보다, 요청의 검증과 응답의 반환값을 테스트하는 데 집중했다.

### Service 계층
`src/test/java/sample/cafekiosk/spring/service`
- `@SpringBootTest`를 사용하여 통합 테스트 작성
  - `@SpringBootTest`를 사용한 이유:
    - Service 계층은 Repository의 기능과 비즈니스 로직이 협력하여 작동한다.
    - Service 빈과 Repository 빈을 불러와야 하기 때문에 통합 테스트로 작성했다.

### Domain 계층 
`src/test/java/sample/cafekiosk/spring/domain`
- 순수 단위 테스트 작성

### Repository 계층
`src/test/java/sample/cafekiosk/spring/repository`
- `@DataJpaTest`를 사용하여 통합 테스트 작성
  - `@DataJpaTest`를 사용한 이유:
    - 모든 빈을 불러올 필요가 없기 때문에 `@SpringBootTest`보다 가볍다.
    - 자체적으로 `Transactional` 롤백을 지원하여 테스트 후 리포지토리를 별도로 정리하거나 `@Transactional`을 적용할 필요가 없다.

---

## CafeKiosk Application RestDocs
`src/test/java/sample/cafekiosk/docs`
### Asciidoc 
- RestDocs를 사용하여 신규 상품을 등록하는 API 명세서를 작성함.

---

# CafeKiosk Unit Test
`src/test/java/sample/cafekiosk/unit`
## 요구사항 
- 주문 목록에 음료 추가 / 삭제 기능
- 주문 목록 전체 지우기
- 주문 목록 총 금액 계산하기
- 주문 생성하기
- 한 종류의 음료 여러 잔을 한 번에 담는 기능
- 가게 운영 시간 (10:00 ~ 22:00) 외에는 주문을 생성할 수 없다.
