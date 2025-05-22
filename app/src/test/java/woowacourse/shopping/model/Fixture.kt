package woowacourse.shopping.model

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CartProducts
import woowacourse.shopping.domain.model.Product

val DUMMY_PRODUCT_1 =
    Product(
        id = 1,
        name = "[병천아우내] 모듬순대",
        imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
        price = 11900,
    )

val DUMMY_PRODUCT_2 =
    Product(
        id = 2,
        name = "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
        imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg",
        price = 5000,
    )

val DUMMY_PRODUCT_3 =
    Product(
        id = 3,
        name = "[애슐리] 크런치즈엣지 포테이토피자 495g",
        imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/23efcafe-0765-478f-afe9-f9af7bb9b7df.jpg",
        price = 10900,
    )

val DUMMY_PRODUCT_4 =
    Product(
        id = 4,
        name = "치밥하기 좋은 순살 바베큐치킨",
        imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f864b361-85da-4482-aec8-909397caac4e.jpg",
        price = 13990,
    )

val DUMMY_PRODUCT_5 =
    Product(
        id = 5,
        name = "[이연복의 목란] 짜장면 2인분",
        imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/90256eb2-b02f-493a-ab7a-29a8724254e4.jpeg",
        price = 9980,
    )

val DUMMY_CART_PRODUCT_1 = CartProduct(DUMMY_PRODUCT_1, 1)
val DUMMY_CART_PRODUCT_2 = CartProduct(DUMMY_PRODUCT_2, 1)
val DUMMY_CART_PRODUCT_3 = CartProduct(DUMMY_PRODUCT_3, 1)
val DUMMY_CART_PRODUCT_4 = CartProduct(DUMMY_PRODUCT_4, 1)
val DUMMY_CART_PRODUCT_5 = CartProduct(DUMMY_PRODUCT_5, 1)

val DUMMY_CART_PRODUCTS =
    CartProducts(
        products =
            listOf(
                DUMMY_CART_PRODUCT_1,
                DUMMY_CART_PRODUCT_2,
                DUMMY_CART_PRODUCT_3,
                DUMMY_CART_PRODUCT_4,
                DUMMY_CART_PRODUCT_5,
            ),
        totalPage = 1,
    )
