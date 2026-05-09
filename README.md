# android-shopping-cart

## 도메인

### PurchaseProduct

- [x] `Product`객체 1개를 갖는다
- [x] `Product`를 구입할 개수인 `count` 필드를 갖는다
- [x] 구매할 개수를 변경할 수 있다
- [x] 구매할 개수는 1 미만일 수 없다
- [x] 구매할 개수에 따른 금액을 계산할 수 있다

### PurchaseProducts

- [x] 여러개의 `PurchaseProduct`를 갖는다
- [x] `PurchaseProduct`를 추가할 수 있다
- [x] 특정 `PurchaseProduct`의 `count`를 변경할 수 있다
- [x] 특정 `PurchaseProduct`를 제거할 수 있다
- [x] 특정 `PurchaseProduct`의 총 가격을 알 수 있다
- [x] `PurchaseProduct`의 `count`의 총합을 알 수 있다
- [x] 동일한 `ID`를 갖는 `PurchaseProduct`가 추가되면 기존에 담겨있던 객체의 `Count`가 증가된다
- [x] 특정한 ID를 갖는 `PurchaseProduct`가 이미 담겨있는지 알 수 있다
- [x] 특정한 ID를 갖는 `PurchaseProduct`의 `count`를 알 수 있다

### Cart

- [x] `PurchaseProducts`를 갖는다
- [x] `PurchaseProduct`를 추가할 수 있다
- [x] `ID`를 통해 특정 `PurchaseProduct`의 `count`를 변경할 수 있다
- [x] `ID`를 통해 특정 `PurchaseProduct`를 제거할 수 있다
- [x] `ID`를 통해 특정 `PurchaseProduct`의 총 가격을 알 수 있다
- [x] `Cart`에 담긴 `PurchaseProduct`의 count의 총합을 알 수 있다
- [x] 동일한 `ID`를 갖는 `PurchaseProduct`가 추가되면 기존에 담겨있던 객체의 `Count`가 증가된다
- [x] 특정한 ID를 갖는 `PurchaseProduct`가 이미 담겨있는지 알 수 있다
- [x] 특정한 ID를 갖는 `PurchaseProduct`의 `count`를 알 수 있다

## UI

### CartCountLabel

- [x] `Cart`에 담긴 `PurchaseProduct`의 `count` 총합을 표시한다
- [x] `Cart`에 담긴 `PurchaseProduct`의 `count` 총합 변경이 반영된다

### CirclePlusBtn

- [x] 버튼을 클릭하면 `Cart`에 `PurchaseProduct`가 추가된다 
- [x] 버튼을 클릭하면 `QuantitySelector` 컴포저블을 표시한다

### QuantitySelector

- [x] `PurchaseProduct`의 `count`를 표시한다.
- [x] `+`버튼을 누르면 해당하는 `PurchaseProduct`의 `count`가 `1` 증가한다
- [x] `-`버튼을 누르면 해당하는 `PurchaseProduct`의 `count`가 `1` 감소한다
- [x] 해당하는 `PurchaseProduct`의 `count`가 `1`인 상태에서 `-`를 누르면 해당 `PurchaseProduct`를 카트에서 제거한다

## viewModel

### ShoppingViewModel

- [x] ROOM db에 상품을 저장할 수 있다
- [x] 장바구니에 이미 담긴 상품이면 count를 update한다
- [x] 장바구니에 담겨있는 상품을 제거할 수 있다
- [x] 전체 상품 목록을 불러온다
- [x] 불러올 상품 목록에 loadMore를 적용한다
- [x] ViewModel 인스턴스 주입을 위한 팩토리 클래스 정의

### ProductDetailViewModel

- [x] 장바구니에 담을 수량을 조절할 수 있다
- [x] 장바구니에 담기 버튼을 누르면 ROOM DB에 해당 상품과 수량 정보를 저장한다
- [x] ViewModel 인스턴스 주입을 위한 팩토리 클래스 정의

### CartViewModel

- [x] ROOM db에서 화면에 표시할 상품에 pagination을 적용해 불러온다
- [x] 특정 상품의 count를 변경하면 ROOM db에 반영된다
- [x] 특정 상품을 삭제하면 ROOM db에서 삭제된
- [x] ViewModel 인스턴스 주입을 위한 팩토리 클래스 정의

## ROOM DB

### DataBase

- [x] PurchaseProductDao 인스턴스 선언
- [x] DataBase 인스턴스 필드 선언
- [x] DataBase 인스턴스 반환 함수 정의

### PurchaseProductEntity

- [x] 도메인 객체에 대한 테이블 정의
  - [x] 상품 ID Column
  - [x] 상품 이름 Column
  - [x] 상품 가격 Column
  - [x] 상품 이미지 Uri Column
  - [x] 상품 수량 Column
- [x] Entity를 도메인 객체로 변환할 수 있다

### PurchaseProductsDao

- [x] DB에 저장된 전체 장바구니 목록을 불러올 수 있다
- [x] 상품 ID로 특정 장바구니 목록을 불러올 수 있다
- [x] DB에 상품을 추가할 수 있다
- [x] 특정 상품의 수량을 변경할 수 있다
- [x] 특정 상품을 DB에서 제거할 수 있다
- [x] 상품 수량의 총합을 알 수 있다
- [x] 특정 상품의 수량을 알 수 있다
- [x] 특정 상품의 수량에 따른 총 금액을 알 수 있다
- [x] 전체 장바구니 목록에 pagination을 적용해 일부만 불러올 수 있다
- [x] 상품의 개수를 할 수 있다
- [x] id를 통해 특정 상품이 담겨있는지 확인할 수 있다

### PurchaseProductsRepository

- [x] 전체 장바구니 목록을 불러올 수 있다
- [x] 장바구니에 상품을 추가할 수 있다
- [x] 장바구니에 담긴 상품의 수량을 변경할 수 있다
- [x] DB에 저장된 목록의 개수를 읽어올 수 있다
- [x] DB의 전체 목록에 pagination을 적용해 일부 목록만 불러올 수 있다
- [x] 특정 상품을 ID로 조회힐 수 있다
- [x] 특정 상품의 수량에 따른 총 금액을 조회할 수 있다
- [x] 장바구니에 담긴 상품 수량의 충합을 알 수 있다
- [x] 특정 상품의 수량을 조회할 수 있다
- [x] 장바구니에 담긴 상품을 제거할 수 있다
- [x] 도메인 객체를 Entity로 변환할 수 있다

## Application

### ShoppingApplication

- [x] ROOM DB 인스턴스 초기화
- [x] Repository 인스턴스 초기화

--- 

## UI

### RecentlyViewedProducts

- [ ] 최근 본 상품을 최대 10개 까지 표시한다
- [x] LazyRow를 통해 스크롤할 수 있다
- [ ] 표시된 상품을 클릭하면 해당 상품의 ProductDetailScreen으로 이동한다

### RecentlyViewedProductItem

- [x] 상품의 이미지를 표시한다
- [x] 상품의이름을 표시한다

### LastViewedProduct

- [x] 가장 마지막에 본 상품의 이름을 표시한다
- [ ] 클릭 시 해당 상품의 ProductDetailScreen으로 이동한다

### ProductDetailActivity

- [ ] 가장 마지막으로 본 상품의 상품 정보 페이지에서 뒤로가기를 누르면 MainActivity로 이동한다

## ROOM db

### RecentlyViewedProductEntity

- [x] 도메인 객체에 대한 Column 정의
    - [x] 도메인 객체에 대한 테이블 정의
    - [x] 상품 ID Column
    - [x] 상품 이름 Column
    - [x] 상품 가격 Column
    - [x] 상품 이미지 Uri Column
- [x] Entity를 Domain 객체로 변환할 수 있다

### RecentlyViewedProductDao

- [ ] db에 저장된 전체 목록을 조화할 수 있다
- [ ] 상품은 최대 10개가지 저장된다
- [ ] 10개가 저장된 상태로 새로운 Entity가 들어오면 가장 오래 저장되어 있던 데이터를 제거한다
- [ ] 가장 마지막에 저장된 상품을 조회할 수 있다

## WebClient

- [ ] url을 통해 상품 목록을 WebServer에 요청해 상품 목록을 조회할 수 있다
