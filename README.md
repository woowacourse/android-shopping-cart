# 기능 구현 사항

## 1차 리팩토링
- From 베르
  - [x] 패키지 구조 변경
  - [x] MainActivity 존재 이유 관련 고민
    - MainActivity 제거 및 ShoppingActivity를 시작점으로 수정
  - [x] intent 시 raw 문자열 사용에 따른 문제 수정
- From 토끼
  - [x] 의존성 버전관리 누락 이슈 수정
  - [x] Money 곱셈 오버플로우 수정 
  - [x] equals 메서드의 타입 안전성 문제 수정
  - [x] 의도한 도메인 예외와 실제 오류 구분 처리 로직 작성
  - [x] getProductById 제품 미존재 시 오류 처리 로직 작성
  - [x] 페이지네이션 파라미터 경계값 검증
  - [x] 다음 버튼 접근성 라벨 수정
  - [x] getCartItem 로직 수정 (경계값)

## 2차 리팩토링
- [x] 화면 회전 등의 이벤트 발생 대응
- [x] 화면 구성과 네비게이션 간 책임 분리
- [x] MockRepository 명명 수정
- [x] ShoppingScreen 내 불필요한 리컴포지션 로직 수정
- [x] 불필요한 Coroutine launch 메서드 수정
- [x] 리컴포지션 시 불필요한 중복 동작 로직 수정
- [x] DetailStateHolder 리컴포지션 로직 수정
- [x] 이미지 불러오기 오류 관련 UX 개선
- [ ] kotlin default parameter 활용
- [ ] DetailActivity 내 companion object 위치 변경 및 캡슐화

## 상품명(ProductName)
- 상품의 이름(String)을 나타낸다.
- [x] 이름이 빈 값일 경우 예외를 반환한다.
- [x] 이름이 공백일 경우 예외를 반환한다

## 가격(Money)
- [x] 음수일 경우 예외를 반환한다.

## 상품 (Product)
- 상품명 (ProductName), 가격(Money), 이미지 링크(String), id(UUID)를 가진다.
- [x] 동일한 id를 가진 상품은 동일 상품이다.

## 장바구니 상품(CartItem)
- 상품(Product)과 상품 개수(Int)를 가진다.
- [x] 해당 상품의 개수에 따른 가격을 계산한다.
- [x] 상품 개수가 0 이하일 시 예외를 반환한다.

## 장바구니(Cart)
- 장바구니에 등록된 상품 (List<CartItem>)을 관리한다.
- 장바구니에 등록된 상품의 전체 가격을 계산한다.
- [x] 상품을 등록할 수 있다.
- [x] 등록한 상품을 삭제할 수 있다.
- [x] 등록된 상품의 총 가격을 계산한다.

## UI
- [x] 상품 목록 페이지 구현
- [x] 상품 상세 페이지 구현
- [x] 장바구니 페이지 구현
- [x] 상품 목록 페이지 더보기 기능 구현
- [x] 장바구니 페이지 페이지네이션 구현

