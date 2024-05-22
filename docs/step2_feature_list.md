# 기능 목록

- 장바구니 싱크
  - Product List: + 버튼을 누르면 장바구니에 상품이 추가됨과 동시에 수량 선택 버튼이 노출된다.
  - Detail : 상품 목록의 상품 수가 변화하면 장바구니에도 반영되어야 한다.
  - Cart: 장바구니의 상품 수가 변화하면 상품 목록에도 반영되어야 한다.
- 최근에 본 상품
  - 상품 상세 페이지에서 확인할 수 있다.

- Detail
  - Recent 상품 보여줘야함
  - Recent 상품 Detail 페이지 에서는 마지막 화면 이동 뷰가 안 보여야함
- 마지막으로 본 상품 페이지에서 뒤로 가기를 하면 상품 목록으로 이동한다.

## Data 

- Product List
  - 서버 만둘기

장바구니 ROOM DB 만들기
- Cart - product id, 수량만 저장
- 조회한 Product id, CreateDateTime
- why?? Product Api 는 있고, Cart, SearchedProduct Api 는 없다고 가정

## Domain