# android-shopping-cart

## 기능 요구 사항

### 상품 목록 페이지

- 상품 목록을 볼 수 있다.
- 장바구니 버튼을 클릭하면 장바구니 페이지로 이동한다
    - 장바구니 버튼은 우측 상단에 위치한다.
- 상품을 클릭하면 상품 상세정보 페이지로 이동한다.
- GridLayoutManager를 사용한다.
- UI 색감은 피그마에서 지정한 색깔로 맞춘다.
- 앱바 네이밍 : Shopping

### 상품 상세 페이지
- 이미지, 상품 이름, 가격 등이 출력된다.
- 상품 상세에서 장바구니에 상품을 담을 수 있다.
- 장바구니 담기 버튼을 클릭하면 장바구니 페이지로 이동한다.
- 장바구니 담기 버튼을 클릭하면 장바구니에 상품정보가 저장된다.
- X 버튼을 누르면 상품목록 페이지로 이동한다.
    - 버튼의 위치는 우측 상단에 위치한다.
- 앱바 네이밍 : 없음

### 장바구니
- 장바구니에서 'X'버튼을 누르면 상품이 삭제된다.
- 장바구니에서 원하는 상품을 삭제할 수 있다.
- 한 페이지에 상품정보는 최대 5개까지 출력된다.
    - 5개를 초과할 경우, 페이지네이션이 활성화된다.
    - 페이지네이션이 활성화 될 경우, 초기에는 우측 방향 버튼이 활성화된다.
    - 마지막 페이지로 이동하면 우측 방향 버튼이 비활성화 된다.
- 뒤로가기 버튼을 누르면 뒤로 간다.
- 앱바 네이밍 : Cart


## Domain Specification

### Product
- id: Long
- name: String
- price: Long
- imgUrl: Url

### CartItem
- productId: Long
- quantity: Int

### ShoppingCart
- items: List\<CartItem>
- totalPrice: Long

### RecentViewedProducts
- itemIds: List\<Long> ; Long represents Product id
