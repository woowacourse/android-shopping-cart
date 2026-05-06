# android-shopping-cart

## 도메인

### PurchaseProduct

- [x] `Product`객체 1개를 갖는다
- [x] `Product`를 구입할 개수인 `count` 필드를 갖는다
- [x] 구매할 개수를 변경할 수 있다
- [x] 구매할 개수는 1 미만일 수 없다
- [x] 구매할 개수에 따른 금액을 계산할 수 있다

### PurchaseProducts

- [x] 여러개의 `PurchaseProduct`를 갖는다
- [x] `PurchaseProduct`를 추가할 수 있다
- [x] 특정 `PurchaseProduct`의 `count`를 변경할 수 있다
- [x] 특정 `PurchaseProduct`를 제거할 수 있다
- [x] 특정 `PurchaseProduct`의 총 가격을 알 수 있다
- [x] `PurchaseProduct`의 `count`의 총합을 알 수 있다
- [x] 동일한 `ID`를 갖는 `PurchaseProduct`가 추가되면 기존에 담겨있던 객체의 `Count`가 증가된다

### Cart

- [x] `PurchaseProducts`를 갖는다
- [x] `PurchaseProduct`를 추가할 수 있다
- [x] `ID`를 통해 특정 `PurchaseProduct`의 `count`를 변경할 수 있다
- [x] `ID`를 통해 특정 `PurchaseProduct`를 제거할 수 있다
- [x] `ID`를 통해 특정 `PurchaseProduct`의 총 가격을 알 수 있다
- [x] `Cart`에 담긴 `PurchaseProduct`의 count의 총합을 알 수 있다
- [x] 동일한 `ID`를 갖는 `PurchaseProduct`가 추가되면 기존에 담겨있던 객체의 `Count`가 증가된다

## UI

### CartCountLabel

- [ ] `Cart`에 담긴 `PurchaseProduct`의 `count` 총합을 표시한다
- [ ] `Cart`에 담긴 `PurchaseProduct`의 `count` 총합 변경이 반영된다

### CirclePlusBtn

- [ ] 버튼을 클릭하면 `Cart`에 `PurchaseProduct`가 추가된다 
- [ ] 버튼을 클릭하면 `QuantitySelector` 컴포저블을 표시한다

### QuantitySelector

- [ ] `PurchaseProduct`의 `count`를 표시한다.
- [ ] `+`버튼을 누르면 해당하는 `PurchaseProduct`의 `count`가 `1` 증가한다
- [ ] `-`버튼을 누르면 해당하는 `PurchaseProduct`의 `count`가 `1` 감소한다
- [ ] 해당하는 `PurchaseProduct`의 `count`가 `1`인 상태에서 `-`를 누르면 해당 `PurchaseProduct`를 카트에서 제거한다
