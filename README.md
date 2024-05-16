# android-shopping-cart

## Step1
- [x]: git hook 설정

## Domain
- 상품 
  - id
  - 가격
  - 이름
  - 이미지 URL
- 장바구니
  - 상품들(Map, List)
  - [x]: 상품 추가
  - [x]: 상품 삭제
  - [x]: 상품이 없으면 삭제가 안됨
  - [x]: 특정 상품에 대한 가격 계산
  - [x]: 모든 상품에 대한 가격 계산

## UI

- 인터넷 권한 요청 허용
- Glide BindingAdapter 구현

### ProductList
- Appbar 커스터 마이징
  - [ ]: 장바구니 icon 클릭시 카트 화면으로 이동
- [x] ProductList 화면 에서 item 클릭시 ProductDetail 화면으로 이동
- [x] GridLayoutManager 적용한 리사이클러뷰 구현 (span 2)
- [x] 리사이클러뷰에 ItemDecoratoion 적용
- [ ] 더보기 버튼 추가
- [ ] 더보기 버튼 클릭시 products 들 리사이클러뷰에 추가하기
- [ ] ui test

### ProductDetail
- [x] 

### ShoppingCart


## Data

- ShoppingRepository
  - [x]: 쇼핑상품들 불러오기

# 고민 & 공부할 내용

## Domain
삭제할 상품이 없을 때 상품을 삭제하면 에러?? Result? 아무 일도 없어, Boolean 반환?

## UI

### Fragment vs Activity

상품 목록이나 상품 상세를 띄울 때 Activity 를 사용할 것인가? Fragment 를 사용할 것인가? 를 고민을 하였는데
Fragment로 대체할 수 있다면 Fragment를 사용하는 것이 좋다고 생각한다
Fragment 가 Activity에 비해 가볍다고 생각했기 때문이다. (지세한건 모름...)

### 추후 SingleLiveData 적용하기

1) 일단 장바구니에 있는 상품을 View에서 삭제하고 서버 통신
2) 서버통신 결과값에 따라 장바구니에 있는 상품 삭제

둘 중 뭐가 좋을까?
2번의 경우 네트워크 시간 때문에 VIew에 바로 적용 안됨
따라서, 우리는 사용자 에게 더 좋은 경험을 시켜주기 위해 1 번 방법 선택 

### BindingAdapter 의 패키지 위치
BindingAdapter 마다 다르겠지만

아래의 BindingAdapter 는 이미지를 사용하는 곳에서 공통적으로 사용하는 녀석이다.
물론, 화면마다 이미지를 처리하는 방식이 달라질 수 있다고는 생각하기에 각 화면마다 바인딩 어뎁터를 부여할 수도 있다.
그러나, 지금은 공통적으로 사용함! 요녀석은 어디로 둬야하지..?

```kotlin
@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?) {
    Glide.with(context)
        .load(imgUrl)
        .placeholder(R.drawable.img_odooong)
        .error(R.drawable.ic_error_24)
        .into(this)
}
```

### supportActionBar vs actionBar 뭐가 다름

actionBar: Activity 또는 FragmentActivity 에 있음..
supportActionBar: AppCompatActivity 에만 있네