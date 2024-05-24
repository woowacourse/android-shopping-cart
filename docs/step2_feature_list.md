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

- 최근 본 상품 로직
-[x] : DetailView - viewModel 이 생성될 때, 넘겨 받은 id 로 최근 상품으로 저장한다.
-[x] : DetailView - 마지막 본 상품으로 이동할 때, 최근 상품을 저장한다.
-[x] : DetailView 마지막 show 전략
  -[x] : 마지막으로 본 상품과 현재 상품이 같으면 마지막 상품 View를 보여주지 않음
  -[x] : 마지막 본 상품이 없을 때 마지막 상품 View를 보여주지 않음
-[x] : 마지막 상품이 안보이면 뒤로 가기하면 상품 목록으로 이동한다. (구현해야함)

# 공부 & 새롭게 알게된 사실

```kotlin
@Query("SELECT * FROM RecentProduct ORDER BY createdTime ASC LIMIT :size")
fun loadProducts(size: Int): List<RecentProductEntity>
```

만약, db에 3개가 있고 `loadProducts(4)` 해도 안터지고, 3개만 나온다. 🫢

## NestedScrollView && RecyclerView

ScrollView에 Horizontal부분(최근 상품)과 Linear 부분(상품들)을 추가하고,
Linear 부분 영역 RecyclerView의 height를 wrap_content로 설정하면 해결가능 하다!
하지만 ListView, RecyclerView 같은 리스트 형식의 뷰는
크기가 정해지지 않으면 가지고 있는 `Item만큼 높이를 차지하고
한번에 표시된다` !! (즉, recycle 이 안된다..)

대체 방법은?? ConcatAdapter를 사용하면 된다.

## ConcatAdapter

https://medium.com/hongbeomi-dev/concatadapter-deep-dive-6aa79750f81e

- ConcatAdapter: 여러개의 RecyclerView.Adapter를 하나로 합쳐서 사용하는 방법

```kotlin
public static final class Config {

    public final boolean isolateViewTypes;
    @NonNull
    public final StableIdMode stableIdMode;

    @NonNull
    public static final Config DEFAULT = new Config(true, NO_STABLE_IDS);

}
```

- isolateViewTypes = false

ConcatAdapter는 자신에게 할당된 모든 어댑터가 
동일한 뷰 타입을 사용하여 동일한 뷰홀더를 참조하도록 어뎁터 간에 뷰타입 풀을 공유한다는 옵션이다.

공유는 가능하다! but, 서로 다른 뷰홀더에 대해 동일한 뷰 타입을 리턴 하면 충돌한다.
즉, 어댑터 간에 뷰타입을 분리하여 동일한 뷰홀더를 사용하지 않도록 한다!!

> 이번 미션에서는 false 를 줄 필요가 없음

- stableIdMode

(기본값) NO_STABLE_IDS:  stable id 
리사이클러뷰 최적화할 때 getItemId 를 오버라이딩해주어 고유한 id를 부여하고
`productAdapter.setHasStableIds(true)` 로 주면 애니메이션 및 업데이트 성능 향상(체감은 안간다..)

아무튼, setHasStableIds(false) 를 의미

1) NoStableIdStorage : 무조건 RecyclerView.NO_ID를 리턴 (기본값)
2) SharedPoolStableIdStorage: Adapter의 getItemId 값을 그대로 리턴
3) IsolatedStableIdStorage : 파라미터로 받은 adapter의 getItemId 값이 이미 저장되어 있다면 그 id를 리턴하거나 고유한 id를 생성하여 리턴

- Multiple ViewType vs ConcatAdapter
A,B,A,B,A,B 와 같은 경우는 Multiple ViewType
AAAAAAABBBBBBBCCCCC 와 같은 경우는 ConcatAdapter를 사용하면 된다.


---