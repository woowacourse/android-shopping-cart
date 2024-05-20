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
- [x]: ShoppingNavigator 적용

- [ ]: UiState 도입 + 로딩, 실패, 성공 sealed class 로 관리
- [ ]: Android Resource의 dimen 도입
- [ ]: SavedStateHandle 을 사용하여 ViewModel 에서 상태 저장하기
- [ ]: ShoppingRepository 페이지 기반으로 변경하기
- [ ]: 남은 ViewModel 테스트 작성하기
## ?? 궁금증
esspresso 테스트에서 개별 테스트는 모두 통과하는데
테스트를 모두 실행하면, 실패함... (강제로 그냥 바꿔버림)

이게 테스트 마다 Application이 다시 시작되지 않는 것 때문임..  
매 테스트 마다 다시 Application을 시작하도록 하는 좋은 방법이 있을까나 (일단 보류 나중에 다시 생각해보기)

# 공부 정리

## map vs switchMap vs MediatorLiveData

- MediatorLiveData 여러 LiveData 소스를 하나로 만듦(combine 이랑 비슷한데 element가 하나 라도 바뀌면 emit함)
- map (flow map이랑 비슷, {element -> element})
- switchMap (flow flatMap 이랑 비슷, {element -> LiveData})

switchMap은 다른 LiveData를 기반으로 LiveData 소스를 전환하는 데 사용됨
