package woowacourse.shopping

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import woowacourse.shopping.model.product.Product

val fakeContext: Context = ApplicationProvider.getApplicationContext()

val PRODUCTS_IN_RANGE_0_2 =
    listOf(
        Product(
            "[병천아우내] 모듬순대",
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
            11900,
        ),
        Product(
            "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg",
            5000,
        ),
        Product(
            "[애슐리] 크런치즈엣지 포테이토피자 495g",
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/23efcafe-0765-478f-afe9-f9af7bb9b7df.jpg",
            10900,
        ),
    )

val PRODUCT_1 =
    Product(
        "[병천아우내] 모듬순대",
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
        11900,
    )
val PRODUCT_2 =
    Product(
        "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg",
        5000,
    )
