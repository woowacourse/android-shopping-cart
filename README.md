# android-shopping-cart

### 기능 요구 사항

#### 1. 상품 목록
+ 상품을 클릭하면 상품 상세로 이동한다.
+ 상품 목록에서 더보기 버튼을 눌러 추가 로드할 수 있다. (20개 기준)

#### 2. 장바구니
+ 상품 상세에서 장바구니에 상품을 담을 수 있다.
+ 장바구니에서 원하는 상품을 삭제할 수 있다.

#### 3. 상품 상세 화면
+ 상품의 이미지, 이름, 가격을 출력한다.
+ 사용자는 상품 목록의 상품을 장바구니에 추가할 수 있다.

### 1차 코드 리뷰 반영

- [x] test용 applicationContext으로 수정
- [x] 데이터베이스 객체로 상품 정보 추출
- [x] Activity에서 Handler 추출
- [ ] viewmodel에 value를 직접 조회하지 않고 observe를 통해 조회
- [x] notifyDataSetChanged -> notifyItemChanged로 변경
- [x] pageNo -> pageNumber로 변경
- [ ] Paging 객체 데이터 및 저장소 의존성 분리(밖에서 할 로직을 따로 떼어 분리하기만 느낌) 역할을 명확히하고 네이밍 수정
- [ ] onScrollListener를 재사용 가능하게 커스텀
- [ ] ui 상태 처리는 databinding을 활용해서 처리