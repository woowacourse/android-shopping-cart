# android-shopping-cart
## Domain
### ProductRepository
- [ ] 상품 목록을 가져올 수 있다.
### CartRepository
- [ ] 장바구니의 상품 목록을 가져올 수 있다.
- [ ] 장바구니에 상품을 추가할 수 있다.
- [ ] 장바구니에 상품을 삭제할 수 있다.
### RecentViewedRepository
- [ ] 사용자가 상품의 상세 정보를 조회하면 목록에 추가한다.
- [ ] 만약 10개가 넘어갔을 경우 가장 오래 된 상품을 삭제한다.
- [ ] 최근 본 상품 목록을 가져올 수 있다.
### Product
- name : 이름
- imageUrl : 이미지 URL
- Price : 가격
## View
- [ ] 앱이 종료돼도 최근 본 상품 목록과 장바구니 데이터는 유지돼야 한다.
### ProductListActivity
- [ ] 상품을 클릭하면 상품 상세로 이동한다.
- [ ] 최근 본 상품이 있는 경우 상품 목록 상단에서 10개까지 확인할 수 있다.
- [ ] 툴바 안의 카트 버튼을 누르면 장바구니로 이동한다.
### ProductDetailActivity
- [ ] 사용자는 상품을 장바구니에 추가할 수 있다.
### CartActivity
- [ ] 장바구니에서 원하는 상품을 삭제할 수 있다.
- [ ] 툴바 안의 백버튼을 누르면 뒤로 이동한다.

