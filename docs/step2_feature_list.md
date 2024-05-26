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

## 구현

장바구니 ROOM DB 만들기
why?? Product Api 는 있고, Cart, SearchedProduct Api 는 없다고 가정
Product Api 는 MockServer 를 활용해서 만들어서 실제 서버랑 비슷하게 만들어보자. 

- [x]: CartEntity - product id, 수량만 저장
- [x]: RecentProductEntity - 조회한 Product id, CreateDateTime
- RecentProductDao
  - [x]: 최근 본 상품을 저장하고, id를 반환한다.
  - [x]: 최근 본 상품을 3개 저장 하고, 최근 상품 본 상품을 불러올 때, 저장된 시간 순으로 정렬 된다.
- : CartDao
  - [x]: 카트 상품을 저장하고, id 를 반환한다
  - [x]: 1개의 상품 만큼 건너 뛰고, 2개의 상품을 조회 한다.
  - [x]: product 의 id 에 해당 하는 상품을 삭제한다.
  - [x]: 카트 상품을 저장한 후, 저장된 카트 상품을 불러온다.
  - [x]: 카트 상품들을 모두 삭제 한다. (추후 요구사항이 생길지도 모르자나?!)
- : Product List 서버 만둘기
  - [x]: MockServer 를 활용해서 상품 목록 내려주는 Service 만들기
- [x]: Service Test
- [x]: Injector 만들기
- [x]: EventBus 만들기
- [x]: ShoppingRepository 페이지 기반으로 변경하기
### 최근 본 상품 로직
-[x] : DetailView - viewModel 이 생성될 때, 넘겨 받은 id 로 최근 상품으로 저장한다.
-[x] : DetailView - 마지막 본 상품으로 이동할 때, 최근 상품을 저장한다.
-[x] : DetailView 마지막 show 전략
  -[x] : 마지막으로 본 상품과 현재 상품이 같으면 마지막 상품 View를 보여주지 않음
  -[x] : 마지막 본 상품이 없을 때 마지막 상품 View를 보여주지 않음
-[x] : 마지막 상품이 안보이면 뒤로 가기하면 상품 목록으로 이동한다.

---
- [x]: CartFragment UiState 도입
- [x]: ProductDetailViewModel UiState 도입
- [x]: ProductListViewModel UiState 도입
- [x]: CartFragment 상품 count 증가/감소 test 작성
- [x]: Mapper 를 활용하지 않는 곳 적용하기

## BackLog
-[ ]: 마지막 상품이 안보이면 시스템 backPressed 시 상품 목록으로 이동한다.
-[ ]: 각 Fragment 마다 AppBar 만들기 (CustomAppBarLayout 으로 만들어서 사용하기)
(Activity의 AppBar 에 접근해서 처리하는 것은 부모뷰의 역할을 뺏는 것임, AppBar 가 없는 Fragment 의 경우도 있을 지도?)
- [ ]: SavedStateHandle 을 사용하여 ViewModel 에서 상태 저장하기
- [ ]: ViewModel 테스트 작성하기
## 질문
```kotlin
@Test
    @DisplayName("상품의 수량이 1일 때, 증가 시키면 2가 된다")
    fun increase_count_test() {
        // given
        startScenarioWith(product())
        // when & then
        onView(withId(R.id.rv_shopping_cart)).performClickHolderAt<CartAdapter.CartViewHolder>(
            0,
            R.id.btn_increase_product,
        )
        Thread.sleep(100) // ... 반영되는데 시간이 걸림 
        onView(withId(R.id.rv_shopping_cart)).check(matchDescendantSoftly("2"))
    }
```