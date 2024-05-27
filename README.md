# android-shopping-cart

### Room: 앱에서 저장하는 local 소스는, *서버의 local*이라고 생각하고 프로그램을 작성하고 있습니다.
### 서버에서 모든 정보를 저장한다는 생각입니다!

### 메인 화면

- [x] 2열 그리드로 상품을 20개씩 보여준다.
- [x] 상품 이미지, 이름, 가격을 보여준다.
- [x] 더보기 버튼을 누르면 불러올 수 있다.
- [x] 상품을 클릭하면 상품 상세로 이동한다.
- [x] 상품 목록에서 장바구니에 담을 상품의 수를 선택할 수 있다
- [x] 상품 목록의 상품 수가 변화하면 장바구니에도 반영되어야 한다
- [x] 최근 본 상품이 있는 경우 상단에서 10개까지 확인할 수 있다.


### 상품 상세 화면

- [x] 상품 상세 페이지에서 이미지, 이름, 가격을 보여준다.
- [x] 상품 상세에서 장바구니에 상품을 담을 수 있다.
- [x] 상품 상세에서 장바구니에 담을 상품의 수를 선택할 수 있다
- [x] 상품 상세에서 상품 수가 변화하면 상품 목록에도 반영되어야 한다

### 장바구니 화면

- [x] 장바구니에서 원하는 상품을 삭제할 수 있다.
- [x] 상품 이미지, 이름, 가격을 보여준다.
- [x] 장바구니 목록은 페이지네이션이 되어야 한다. (5개 기준)
- [x] 장바구니의 상품 수가 변화하면 상품 목록에도 반영되어야 한다
- [x] 마지막으로 본 상품 1개를 상세 페이지에서 확인할 수 있다.
- [x] 마지막으로 본 상품을 선택했을 때는 마지막으로 본 상품이 보이지 않는다.
- [x] 마지막으로 본 상품 페이지에서 뒤로 가기를 하면 상품 목록으로 이동한다.


### 기타

- [x] 앱이 재시작 되어도 최근 본 상품 목록과 장바구니 데이터는 유지되어야 한다
  - [x] 로컬 데이터 유지를 위해 Room을 사용한다.


---

### 알게된 점
- toList()
quantity를 업데이트하고 diffUtil을 사용했는데, 아이템이 업데이트된게 반영되지 않아 보였다. 
DiffUtil을 하나씩 따라가보니 oldData도 이미 변경된 데이터임을 로그를 통해 알았다. 
그래서 더 들어가보니 toList를 사용하면 list는 복제되어도 안에 객체 참조는 그대로 됨..
그래서 map과 copy로 해결했다

- BindingAdapter Nullable
데이터가 바인딩 되지 않은 시점에 xml이 로드되면 null이 들어갈 수 있어서 nullable 처리해야 오류 발생을 피할 수 있다.

- 제네릭은 컴파일 타임에 결정되지만, reified는 런타임에 할 수 있도록 해준다