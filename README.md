# 기능 구현 사항

## 3/4 단계 기능 요구 사항
### Room
- [x] Room, KSP 의존성 추가
- [x] CartItemEntity 추가
- [x] CartItemDao 추가
- [x] ShoppingDatabase 추가
- [x] Entity와 Domain 간 Mapper 추가
- [x] CartRepository가 Room을 사용하도록 변경

### Cart
- [x] CartViewModel 추가
- [x] CartActivity에서 CartViewModel 연결
- [x] CartStateHolder 제거 및 CartScreen을 UiState 기반으로 수정
- [x] 장바구니 수량 증가 기능 연결
- [x] 장바구니 수량 감소 기능 연결
- [x] 장바구니 삭제 기능 연결

### Shopping
- [x] ShoppingViewModel 추가
- [x] 상품 목록 및 선택 버튼 추가
- [x] 상품 목록 수량 선택 버튼 추가
- [x] 상품 목록 수량과 장바구니 상태 동기화 로직 작성

### Detail
- [x] DetailViewModel 추가
- [x] 상품 상세 수량 선택 UI 추가
- [x] 상품 상세 장바구니 추가 기능 연결

### Recent Items
- [x] RecentItemEntity 추가
- [x] RecentItemDao 추가
- [x] 최근 본 상품 저장
- [x] 최근 본 상품 10개 조회
- [x] 상품 상세에서 마지막으로 본 상품 표시
- [x] 마지막으로 본 상품 선택 시 숨김 처리
- [x] 마지막으로 본 상품 페이지 뒤로가기 처리

### Network
- [x] 상품 목록 HTTP Client 구현
- [x] MockWebServer 테스트 환경 구축
- [x] 네트워크 상태 감지 및 UI 반영 로직 작성