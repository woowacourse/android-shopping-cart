# 3단계/4단계

## 단위 테스트 목록

### 도메인

> Cart/CartContent 의 plus/minus, addQuantity/changeQuantity/decreaseQuantity 와 RecentProducts 정책은
> CartRepository / RecentProductRepository 의 연산으로 이전되어 ViewModel 테스트로 검증한다.

- Money
    - [x] 두 Money를 더하면 금액의 합인 Money를 반환한다
    - [x] 두 Money를 빼면 금액의 차인 Money를 반환한다
    - [x] Money에 수량(Int)을 곱하면 금액이 곱해진 Money를 반환한다
- CartContent
    - [x] 상품의 수량이 1개 미만이면 오류가 발생한다
    - [x] 입력받은 상품의 id가 CartItem 의 상품 id 와 같으면 true, 다르면 false 를 반환한다
- Cart
    - [x] quantityOf 는 해당 상품의 수량을 반환하고, 없으면 0 을 반환한다
    - [x] totalQuantityOf 는 모든 상품 수량의 합을 반환한다

### ViewModel

- ProductListViewModel
    - [x] 초기 진입 시 상품 목록을 불러와 state.productUiModels 에 노출한다
    - [x] 최근 본 상품이 state.recentProducts 에 노출된다
    - [x] 수량 증가 이벤트의 결과가 state.productUiModels 의 수량에 반영된다
    - [x] 이미 존재하는 상품을 추가하면 수량이 누적된다 *(Cart 도메인 이전)*
    - [x] 보유 수량과 같은 수량을 감소시키면 해당 상품이 제거된다 *(Cart 도메인 이전)*
    - [x] 보유 수량보다 적게 감소시키면 수량이 줄어든다 *(Cart 도메인 이전)*
    - [x] 장바구니에서 수량이 바뀐 뒤 cartRefresh 호출 시 state 에 반영된다
    - [x] insertRecentProduct 는 RecentProductRepository 에 기록을 위임한다 *(RecentProducts 도메인 이전)*
- ProductDetailViewModel
    - [x] 초기 진입 시 상품 상세를 불러와 state.productState 에 Success 로 노출한다
    - [x] recentProductId 가 현재 상품과 다르면 state.recentProductState 에 노출된다
    - [x] recentProductId 가 현재 상품과 같으면 recentProductState 는 None 으로 유지된다
    - [x] increase / decrease 이벤트의 결과가 state.quantity 에 반영된다
    - [x] addToCart 는 기존 수량과 현재 quantity 의 합을 CartRepository 에 반영한다
- CartViewModel
    - [x] 초기 진입 시 장바구니 항목을 불러와 state.paginatedCartContents 에 노출한다
    - [x] 이미 존재하는 상품을 증가시키면 수량이 누적된다 *(Cart 도메인 이전)*
    - [x] 보유 수량과 같은 수량을 감소시키면 해당 상품이 제거된다 *(Cart 도메인 이전)*
    - [x] 보유 수량보다 적게 감소시키면 수량이 줄어든다 *(Cart 도메인 이전)*
    - [x] paginatedCartContents 가 비면 isEndPage() 가 true 가 된다
    - [x] 외부에서 장바구니에 변경이 생기면 재조회 시 state 에 반영된다

## 기능 목록

### 전체

- [ ] 앱이 재시작돼도 최근 본 상품 목록과 장바구니 데이터는 유지돼야 한다.
- [ ] 네트워크 상태 변경을 감지하여 UI에 반영한다.

### 상품 목록

- [ ] 버튼을 누르면 장바구니에 상품이 추가됨과 동시에 수량 선택 버튼이 노출된다.
- [ ] 수량 선택 버튼을 통해 0이 되면 수량 선택 버튼을 숨긴다.
- [ ] 상품 목록에서 장바구니에 담을 상품의 수를 선택 할 수 있다. (B마트 UX 참고)
- [ ] 상품 목록의 상품 수가 변화하면 장바구니에도 반영되어야 한다.
- [ ] 최근 본 상품이 있는 경우 상품 목록 상단에서 10개까지 확인할 수 있다.

### 상품 상세

- [ ] 상품 상세에서 장바구니에 담을 상품의 수를 선택할 수 있다.
- [ ] 마지막으로 본 상품 1개를 상품 상세 페이지에서 확인할 수 있다.
- [ ] 마지막으로 본 상품을 선택했을 때는 마지막으로 본 상품이 보이지 않는다.
- [ ] 마지막으로 본 상품 페이지에서 뒤로 가기를 하면 상품 목록으로 이동한다.

### 장바구니

- [ ] 장바구니에서 상품 수를 변경 할 수 있어야 한다
- [ ] 장바구니의 상품 수가 변화하면 상품 목록에도 반영되어야 한다.

## 프로그래밍 요구사항

### 3단계

- [ ] 반복되는 컴포넌트(상품 수량 선택)를 재활용 가능한 구조로 분리한다.
- [ ] 단방향 데이터 흐름(UDF)을 적용한다.
- [ ] 시스템에 의한 프로세스 종료 시에도 현재 수량 상태가 유지되도록 처리한다.

### 4단계

- [ ] 로컬 데이터 유지를 위해 Room 을 사용한다.
- [ ] 상품 목록의 HTTP Client 를 OkHttp 로 구현한다.
- [ ] MockWebServer 를 사용하여 HTTP Client 의 테스트 환경을 구성한다.
- [ ] 네트워크 상태 변경 시스템 이벤트를 감지하여 UI 에 반영한다.