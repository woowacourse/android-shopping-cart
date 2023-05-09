## 사용자 시나리오

- 상품 목록을 불러온다
- 상품을 클릭하면 상품 상세 페이지로 이동한다
    - 최근 본 상품에 추가된다
- 장바구니 담기 버튼을 누르면 카트에 추가된다
- 카트 아이콘을 누르면 장바구니 목록을 확인할 수 있다
- 장바구니 목록에서 상품을 삭제할 수 있다
- 더보기 버튼을 눌러 상품을 추가 로드할 수 있다

## Domain

### Product

- 상품 ID
- 상품 이름
- 이미지 url
- 가격

### ProductCache

- 불러온 상품 목록들을 캐싱하고 유지한다

### Price

- 금액은 0 이상이다

### CartRepository

- 전체 상품 목록을 가져온다
- Product를 받아서 Cart DB에 저장한다
- Product를 Cart DB에서 삭제한다

### RecentProductRepository

- 전체 상품 목록을 가져온다
- Product를 받아서 RecentProduct DB에 저장한다
