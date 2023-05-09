# android-shopping-cart

### 기능 요구 사항

* 사용자는 상품 목록의 상품을 장바구니에 추가할 수 있다.
* 상품을 클릭하면 상품 상세로 이동한다.
* 상품 상세에서 장바구니에 상품을 담을 수 있다.
* 장바구니에서 원하는 상품을 삭제할 수 있다.
* 최근 본 상품이 있는 경우 상품 목록 상단에서 10개까지 확인할 수 있다.
* 앱이 종료돼도 최근 본 상품 목록과 장바구니 데이터는 유지돼야 한다.

### 클래스
* ProductUiModel
  * imgUrl
  * name
  * price

* Product

* ShoppingCart
  * List<Product>
  * remove()
  * add()

* RecentProduct
  * List<Product>
  * remove()
  * add()