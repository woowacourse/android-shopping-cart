# android-shopping-cart

## Domain

### ProductRepository

- [X] 상품 목록을 가져올 수 있다.

### CartRepository

- [X] 장바구니의 상품 목록을 가져올 수 있다.
- [X] 장바구니에 상품을 추가할 수 있다.
- [X] 장바구니에 상품을 삭제할 수 있다.

### RecentViewedRepository

- [X] 사용자가 상품의 상세 정보를 조회하면 목록에 추가한다.
- [X] 만약 10개가 넘어갔을 경우 가장 오래 된 상품을 삭제한다.
- [X] 최근 본 상품 목록을 가져올 수 있다.

### Product

- name : 이름
- imageUrl : 이미지 URL
- price : 가격

### Price

- price : 가격

## View

- [X] 앱이 종료돼도 최근 본 상품 목록과 장바구니 데이터는 유지돼야 한다.

### ProductListActivity

- [X] 상품을 클릭하면 상품 상세로 이동한다.
- [X] 최근 본 상품이 있는 경우 상품 목록 상단에서 10개까지 확인할 수 있다.
- [X] 툴바 안의 카트 버튼을 누르면 장바구니로 이동한다.

### ProductDetailActivity

- [X] 사용자는 상품을 장바구니에 추가할 수 있다.

### CartActivity

- [X] 장바구니에서 원하는 상품을 삭제할 수 있다.
- [X] 툴바 안의 백버튼을 누르면 뒤로 이동한다.

___

## Step3  기능 목록
### ProductListActivity(메인 페이지)
- [ ] '+' 버튼 추가
- [ ] '+' 버튼을 누르면 -1+ 버튼 나옴
  - [ ] 그 버튼을 통해 개수 변경 가능
- [ ] 툴바
    - [ ] 카트 옆에 갯수 표시
    - 
### ProductDetailActivity(상세 화면)
- [X] 마지막으로 본 상품 추가
- [X] 장바구니 담기를 눌렀을 때 다이얼로그 띄우기
  - [ ] 담았을 때 상품 목록 화면으로 이동하도록 하기

### CartActivity(장바구니)
- [ ] 전체 선택 체크 박스
- [X] 가격 보여주기
- [X] 주문하기 / 개수 
