## Step1 feature list

- [x]: SINGLE LIVEDATA로 개선
- [x]: common 으로 Image 처리 BindingAdapter 빼기
- [x]: by viewModels() 로 리팩
- [x]: Cart 로 네이밍 통일
- [x]: Plus -> LoadMore 로 네이밍 변경
- [x]: ProductDetailViewModel early return 으로 개선
- [x]: MediatorLiveData, map, switchMap을 활용하여 LiveData 개선
- [x]: 장바구니 Cart paging 리팩토링 --- Product, page 바뀌면 prev, next page 로드
- [x]: CartAdapter notifyDataSetChanged 개선 - ItemUpdateHelper util 함수 구현
- [x]: CartAdapter onClickItem도 DataBinding을 사용
- [x]: onViewCreated() 내부에 있는 함수들을 분리
- [x]: ProductListViewModel 의 반복되는 코드 함수화하기
- [x]: Adapter Listener 데이터 바인딩 적용
- [x]: repository 싱글톤으로 바꾸기
- [x]: Android Resource의 style 도입
- [ ]: UiState 도입
- [ ]: Android Resource의 dimen 도입
- [ ]: SavedStateHandle 을 사용하여 ViewModel 에서 상태 저장하기
- [ ]: ShoppingNavigator
- ViewModel 테스트 작성하기

## ?? 궁금증

```kotlin
class ShoppingApplication : Application() {

    val shoppingRepository: ShoppingRepository by lazy {
        ShoppingRepositoryInjector.shoppingRepository()
    }

    val cartRepository: CartRepository by lazy {
        CartRepositoryInjector.cartRepository()
    }
}
```

esspresso 테스트에서 개별 테스트는 모두 통과하는데
테스트를 모두 실행하면, 실패하는 경험을 했습니다.

이게 테스트 마다 Application이 다시 시작되지 않는 것 때문인데요..  
매 테스트 마다 다시 Application을 시작하도록 하는 좋은 방법이 있을까요..?

# 공부 정리

## map vs switchMap vs MediatorLiveData

- MediatorLiveData 여러 LiveData 소스를 하나로 만듦(combine 이랑 비슷한데 element가 하나 라도 바뀌면 emit함)
- map (Collection map이랑 비슷, {element -> element})
- switchMap (collection flatMap 이랑 비슷, {element -> LiveData})

switchMap은 다른 LiveData를 기반으로 LiveData 소스를 전환하는 데 사용됩니다.

# 답변 정리

## leak Canary 를 추가한 이유가 궁금합니다.

엇.. `debugImplementation` 로 수정하겠습니다.. 😅

---  

개발자가 실수로 더 이상 필요하지 않은 객체에 대한 참조를 유지할 경우 메모리 누수가 발생할 수 있습니다.(binding 객체, Fragment, Context 등등)  
메모리 누수가 발생할 경우, OOM이 발생하여 비정상 종료가 발생할 수 있습니다.

이때, `LeakCanary` 를 활용하면 개발자에게 메모리 누수가 발생하고 있다는 것을 알려주어 OOM을 방지할 수 있도록 도와주는 라이브러리입니다.
따라서, 메모리 릭이 발생했을 때, 이를 빠르게 인지하고 고칠 수 있도록해당 라이브러리를 추가했습니다! 다만, LeakCanary 에 너무 의존하지 않고, 개발자 본인이 메모리
누수에 대해 항상 인지하고 개발을 해야할 것입니다. 😁

- LeakCanary 가 메모리 릭을 알아챌 수 있는 이유!

LeakCanary 는 안드로이드 생명주기를 알고 있습니다.
따라서, 액티비티 혹은 프래그먼트가 삭제됐음에도 GC 에 수집되지 못하는 객체가 있다면   
메모리릭 가능성이 있다고 가정하고, 이를 Log 에 기록해둡니다.
그리고, 기록해둔 객체들의 메모리 합이 일정 임계치가 넘어가면 java Heap을 덤프시켜 개발자에게 노티를 해줍니다!

---  

아래와 같이 메모리릭이 발생하였을 때 개발자에게 노티를 줍니다!

https://github.com/woowacourse/android-shopping-cart/assets/87055456/a5738868-6985-4771-83c9-60ec93f451cf

https://m.blog.naver.com/cncn6666/221759802371


----

## Repository가 추가된 이유가 있을까요??🤔

데이터의 파이프 라인(Local, Remote)이 바뀌거나 데이터를 저장하는 방식이 변경될지라도 Presentation Layer 에서는 수정사항이 없도록 Repository를
사용하였습니다.

예를 들어, Remote 에서 데이터를 불러오는 방식이 Local 에서 데이터를 불러오는 방식으로 바뀐다고 해도 레포지토리의 인터페이스는 바뀌지 않을 것이기에,
presentaion은 영향을 받지 않을 것입니다.

따라서, 데이터 파이프 라인들을 캡슐화한 Repository 를 ViewModel 이 참조하도록 구현하였습니다.

## LiveData get() 키워드가 들어간 이유를 알 수 있을까요??🤔

get() 을 사용 하면 변수를 하나 더 줄일 수 있기 때문

## binding에서 nullable 하도록 설정이 되어 있다보니, binding을 사용할 때 nullable한지 체크하는 부분이 들어가고 있다는 생각이 듭니다🥲

만약, 프래그먼트 생명주기와 binding 에 대해 잘 모르는 동료 개발자가 onAttached(), onCreate() 에서 binding 객체를 사용하여
앱의 비정상 종료를 야기할 수 있다고 생각하여 `nullalble` 하게 만들었습니다!