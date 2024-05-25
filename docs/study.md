
## map vs switchMap vs MediatorLiveData

- MediatorLiveData 여러 LiveData 소스를 하나로 만듦(combine 이랑 비슷한데 element가 하나 라도 바뀌면 emit함)
- map (flow map이랑 비슷, {element -> element})
- switchMap (flow flatMap 이랑 비슷, {element -> LiveData})

switchMap은 다른 LiveData를 기반으로 LiveData 소스를 전환하는 데 사용됨


## Room & dao

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

## ?? 궁금증
esspresso 테스트에서 개별 테스트는 모두 통과하는데
테스트를 모두 실행하면, 실패함... (강제로 그냥 바꿔버림)

이게 테스트 마다 Application이 다시 시작되지 않는 것 때문임..  
매 테스트 마다 다시 Application을 시작하도록 하는 좋은 방법이 있을까나 (일단 보류 나중에 다시 생각해보기)