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

장바구니 ROOM DB 만들기
why?? Product Api 는 있고, Cart, SearchedProduct Api 는 없다고 가정

- [x]: CartEntity - product id, 수량만 저장
- [x]: RecentProductEntity - 조회한 Product id, CreateDateTime
- RecentProductDao
  - [x]: 최근 본 상품을 저장하고, id를 반환한다.
  - [x]: 최근 본 상품을 3개 저장 하고, 최근 상품 본 상품을 불러올 때, 저장된 시간 순으로 정렬 된다.
- [x]: CartDao
- [ ]: Product List 서버 만둘기
- [ ]: Dao Test
- [ ]: Service Test

## Domain



# 공부 & 새롭게 알게된 사실

```kotlin
@Query("SELECT * FROM RecentProduct ORDER BY createdTime ASC LIMIT :size")
fun loadProducts(size: Int): List<RecentProductEntity>
```

만약, db에 3개가 있고 `loadProducts(4)` 해도 안터지고, 3개만 나온다. 🫢