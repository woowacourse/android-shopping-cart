# android-shopping-cart

## Step1
- [x]: git hook 설정

## Domain
- 상품 
  - id
  - 가격
  - 이름
  - 이미지 URL
- 장바구니
  - 상품들(Map, List)
  - [x]: 상품 추가
  - [x]: 상품 삭제
  - [x]: 상품이 없으면 삭제가 안됨
  - [x]: 특정 상품에 대한 가격 계산
  - [x]: 모든 상품에 대한 가격 계산

## UI

- 인터넷 권한 요청 허용
- Glide BindingAdapter 구현

### ProductList
- Appbar 커스터 마이징
  - [x]: 장바구니 icon 클릭시 카트 화면으로 이동
- [x] ProductList 화면 에서 item 클릭시 ProductDetail 화면으로 이동
- [x] GridLayoutManager 적용한 리사이클러뷰 구현 (span 2)
- [x] 리사이클러뷰에 ItemDecoratoion 적용
- [x] 더보기 버튼 추가
- [x] 더보기 버튼 클릭시 products 들 리사이클러뷰에 추가하기
- [ ] ui test

### ProductDetail
- [x] 

### ShoppingCart


## Data

- ShoppingRepository
  - [x]: 쇼핑상품들 불러오기

# 고민 & 공부할 내용

## Domain
삭제할 상품이 없을 때 상품을 삭제하면 에러?? Result? 아무 일도 없어, Boolean 반환?

## Data

현재 Cart 에서 상품들을 불러오는 과정이 매우 비효율적이다.

- 1) Cart 에서 다음 데이터를 불러옴
- 2-2) 불러온 데이터가 Empty 이면 false
- 2-2) 불러온 데이터가 있으면 true

추후 개선하자! 💪
```kotlin
fun canLoadMoreCartProducts(currentPage: Int): Boolean
```

### supportActionBar vs actionBar 뭐가 다름

actionBar: Activity 또는 FragmentActivity 에 있음..
supportActionBar: AppCompatActivity 에만 있네