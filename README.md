# android-shopping-cart

## 도메인

### PurchaseProduct

- [x] Product객체 1개를 갖는다
- [x] Product를 구입할 개수인 count 필드를 갖는다
- [x] 구매할 개수를 변경할 수 있다
- [x] 구매할 개수는 1 미만일 수 없다
- [x] 구매할 개수에 따른 금액을 계산할 수 있다

### Cart

- [ ] 여러개의 PurchaseProduct를 갖는다
- [ ] PurchaseProduct를 추가할 수 있다
- [ ] PuchaseProduct의 count를 변경할 수 있다
- [ ] PurchaseProduct를 제거할 수 있다
- [ ] Cart에 담긴 PurchaseProduct의 개수를 알 수 있다

## UI

### CartCountLabel

- [ ] Cart에 담긴 PurchaseProduct의 count 총합을 표시한다
- [ ] Cart에 담긴 PurchaseProduct의 count 총합 변경이 반영된다

### CirclePlusBtn

- [ ] 버튼을 클릭하면 Cart에 PurchaseProduct가 추가된다 
- [ ] 버튼을 클릭하면 QuantitySelector 컴포저블을 표시한다

### QuantitySelector

- [ ] PurchaseProduct의 count를 표시한다.
- [ ] `+`버튼을 누르면 해당하는 PurchaseProduct의 count가 1 증가한다
- [ ] `-`버튼을 누르면 해당하는 PurchaseProduct의 count가 1 감소한다
- [ ] 해당하는 PurchaseProduct의 count가 1인 상태에서 `-`를 누르면 해당 PurchaseProduct를 카트에서 제거한다
