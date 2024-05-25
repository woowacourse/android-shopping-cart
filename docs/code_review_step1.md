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