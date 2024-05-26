
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

## ?? 궁금증
esspresso 테스트에서 개별 테스트는 모두 통과하는데
테스트를 모두 실행하면, 실패함... (강제로 그냥 바꿔버림)

이게 테스트 마다 Application이 다시 시작되지 않는 것 때문임..  
매 테스트 마다 다시 Application을 시작하도록 하는 좋은 방법이 있을까나 (일단 보류 나중에 다시 생각해보기)