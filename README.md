# android-shopping-cart

## 도메인 모델

### Product

- id: String (생성 시 UUID)
- name: String
- price: Money
- imageUrl: String

### Money

- amount: Int

### Quantity

- quantity: Int

### CartItem

- product: Product
- quantity: Quantity

### Cart

- cartItems: List<CartItem>
- totalAmount: Money (파생값)

---

## 3단계/4단계

### 기능 목록

- [x] 수량 선택 컴포넌트 기능
    - [x] `+` 버튼 클릭 시 장바구니에 상품을 1개 추가하고 수량 조절 버튼(+, -)으로 변경한다
    - [x] 수량 조절 버튼을 통해 장바구니에 담을 상품 수를 변경할 수 있다
    - [x] 수량이 0이 되면 다시 초기 `+` 버튼 상태로 돌아간다
- [x] 화면 간 상태 동기화
    - [x] 상품 목록 화면에서 변경한 수량이 장바구니 화면에 반영된다
    - [x] 장바구니 화면에서 변경한 수량이 상품 목록 화면에 반영된다
    - [x] 상품 상세 화면에서 변경한 수량이 장바구니 및 목록 화면에 반영된다

### 프로그래밍 요구 사항

- [x] 수량 선택 컴포넌트 재사용: 상품 목록, 상세, 장바구니 화면 등에서 공통으로 사용할 수 있는 UI 컴포넌트로 분리한다
- [x] Room Database 및 Entity 분리
    - 장바구니 데이터를 저장할 `Entity`를 정의하고, 도메인 모델과 데이터 모델(Entity)을 분리한다
    - 계층 간 데이터 변환을 위한 Mapper를 구현한다
- [x] DAO와 Repository 패턴
    - DAO를 통해 데이터베이스 접근을 캡슐화하고, Suspend 함수를 사용하여 비동기 처리를 구현한다
- [x] ViewModel 도입 및 UDF 적용
    - 기존 `StateHolder`를 공식 `ViewModel`로 마이그레이션한다
    - 단방향 데이터 흐름(UDF) 구조를 적용하여 UI 상태와 이벤트를 명확히 분리한다
- [x] 상태 보존 (Process Death 대응)
    - 장바구니 수량 등 핵심 데이터는 Room Database를 통해 영구 보존한다
    - 시스템에 의한 프로세스 종료 시에도 현재 보고 있던 페이지 번호, 선택된 상품 ID 등 UI는 `SavedStateHandle`을 활용하여 복구한다

### 단위 테스트 목록

- Money
    - [x] 두 Money를 더하면 금액의 합인 Money를 반환한다
    - [x] 두 Money를 빼면 금액의 차인 Money를 반환한다
    - [x] Money에 수량(Quantity)을 곱하면 금액이 곱해진 Money를 반환한다
    - [x] 금액이 같은 두 Money는 동등하다
- Quantity
    - [x] 수량이 1개 미만이면 오류가 발생한다
    - [x] 수량을 더하면 더한 새 Quantity를 반환한다
    - [x] 수량을 빼면 빠진 새 Quantity를 반환한다
    - [x] 수량이 같으면 두 Quantity는 동등하다
- CartItem
    - [x] 현재 수량에 새로운 수량을 더한 CartItem을 반환한다
    - [x] 현재 수량에 새로운 수량을 뺀 CartItem을 반환한다
- Cart
    - [x] Cart에 상품을 존재한다면 true를 반환한다
    - [x] Cart에 상품을 존재하지 않는다면 false를 반환한다
    - [x] Cart에 입력받은 상품의 Quantity를 반환한다
    - [x] Cart에 입력받은 상품이 없다면 null을 반환한다
    - [x] 새로운 상품을 추가하면 Cart에 새로운 상품이 추가된 Cart를 반환한다
    - [x] 이미 존재하는 상품을 추가하면 수량이 합쳐진 Cart 를 반환한다
    - [x] 삭제 수량이 보유 수량과 같으면 해당 상품이 제거된 Cart 를 반환한다
    - [x] 삭제 수량이 보유 수량보다 적으면 수량이 줄어든 Cart 를 반환한다
    - [x] 삭제 수량이 보유 수량보다 많으면 예외를 발생시킨다
    - [x] Cart에 없는 상품을 삭제하려고하면 예외를 발생시킨다

---

## 1단계 - 상품 목록

- 핵심 도메인 구현: `Product`, `Money`, `Quantity`, `CartItem`, `Cart` 등 불변 객체 기반의 핵심 비즈니스 로직 구현 및 단위 테스트 완료
- UI 및 상태 관리: Jetpack Compose를 활용하여 상품 목록, 상세, 장바구니 화면 구현 및 `StateHolder`를 통한 UI 상태와 로직 분리
- 장바구니 기능: 상품 상세에서의 장바구니 담기, 장바구니 화면에서의 상품 삭제 기능 구현

---

## 2단계 - 데이터 로딩

- 상품 목록 페이징: `PAGE_SIZE = 20` 단위로 Mock 데이터를 로드하며, 더보기 버튼을 통한 추가 데이터 로딩 및 상태 유지(`rememberSaveable`) 구현
- 장바구니 페이지네이션: 장바구니에 담긴 상품을 `PAGE_SIZE = 5` 단위로 나누어 표시하며, 이전/다음 버튼을 통한 페이지 이동 및 삭제 시 자동 페이지 조정 구현
