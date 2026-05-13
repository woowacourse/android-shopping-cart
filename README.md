# Android Shopping Cart

# 🚀 3단계 - 상태 관리 | 4단계 - HTTP Client
## 핵심 기능

- 상품 목록 조회 및 페이지 로드
- 상품 수량 `+/-` 조작
- 장바구니 담기/삭제
- 상품 목록, 상세, 장바구니 간 수량 동기화(SSOT)
- 최근 본 상품(최신순, 최대 10개) 관리
- 네트워크 상태 변경 감지 및 UI 배너 표시
- 앱 재시작 이후 장바구니/최근 방문 데이터 유지

## 미션 요구사항 반영

- 로컬 데이터 유지를 위해 Room 사용
- HTTP Client(OkHttp) 구현
- MockWebServer 기반 테스트 서버 구성
- 네트워크 상태 변경 시스템 이벤트 감지 후 UI 반영

## 패키지 구조

```text
app/src/main/java/woowacourse/shopping
├─ ShoppingApplication.kt
├─ activity
│  ├─ ProductListActivity.kt
│  ├─ DetailProductActivity.kt
│  └─ ShoppingCartActivity.kt
├─ ui
│  ├─ ProductListScreen.kt
│  ├─ DetailProductScreen.kt
│  ├─ ShoppingCartScreen.kt
│  ├─ component
│  └─ pagination
├─ viewmodel
│  ├─ ProductListViewModel.kt
│  ├─ DetailProductViewModel.kt
│  └─ ShoppingCartItemViewModel.kt
├─ repository
│  ├─ ShoppingItemRepository.kt
│  ├─ ShoppingCartRepository.kt
│  ├─ RoomShoppingItemRepository.kt
│  └─ RoomShoppingCartRepository.kt
├─ backend
│  ├─ MockProductSeedData.kt
│  ├─ MockShoppingBackendServer.kt
│  ├─ ProductBackendDataSource.kt
│  ├─ OkHttpProductBackendDataSource.kt
│  └─ ShoppingItemsRemoteSyncer.kt
├─ network
│  ├─ NetworkStatusMonitor.kt
│  └─ AndroidNetworkStatusMonitor.kt
├─ storage
│  ├─ room
│  │  ├─ ShoppingDatabase.kt
│  │  ├─ shoppingItem
│  │  │  ├─ ShoppingItemEntity.kt
│  │  │  └─ ShoppingItemDao.kt
│  │  └─ shoppingcart
│  │     ├─ ShoppingCartEntity.kt
│  │     ├─ ShoppingCartItemRow.kt
│  │     └─ ShoppingCartDao.kt
│  └─ datastore
│     ├─ VisitStore.kt
│     └─ DataStoreVisitStore.kt
└─ model
   ├─ Product.kt
   ├─ ProductTitle.kt
   ├─ Price.kt
   ├─ ShoppingItem.kt
   └─ ShoppingCartItem.kt
```

## 화면별 동작 규칙

### 상품 목록

- 상품은 20개 단위 페이지로 표시
- 수량이 0이면 장바구니 추가 버튼 노출
- 수량이 1 이상이면 `+/-` 컨트롤 노출
- 상단 장바구니 아이콘 배지는 전체 수량 합 표시

### 상품 상세

- 기본 선택 수량: 1
- `+/-`는 상세의 선택 수량만 변경
- `장바구니에 담기` 클릭 시에만 실제 수량 반영
- 최근 본 상품 영역은 "현재 상품과 다른 마지막 방문 상품 1개"만 노출

### 장바구니

- 페이지당 5개 노출
- `+/-` 조작 시 상품 목록/상세와 동일 수량으로 즉시 동기화
- 수량이 0이 되면 장바구니에서 제거
- 항목 삭제 시 해당 상품 수량도 0으로 초기화

## 체크리스트

### 3단계

- [x] 상품 목록에서 `+` 버튼 클릭 시 장바구니 수량 증가
- [x] 목록/상세/장바구니 간 수량 동기화
- [x] 수량 0이면 장바구니 항목 제거
- [x] 수량 음수 방지
- [x] 재시작 이후 수량 유지
- [x] 공통 수량 컴포넌트 동작
- [x] 장바구니 `x` 삭제 동작

### 4단계

- [x] 최근 본 상품 최신순 정렬, 최대 10개 유지
- [x] 상세 화면에서 마지막 방문 상품 1개 표시
- [x] 마지막 방문 상품 선택 시 해당 영역에서 제외
- [x] 마지막 방문 상품 상세에서 뒤로 가기 시 상품 목록 이동
- [x] 재시작 후 최근 방문/장바구니 데이터 유지
