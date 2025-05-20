package woowacourse.shopping.data

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

@Suppress("ktlint:standard:max-line-length")
object CartDatabase {
    val cart: MutableSet<Product> =
        mutableSetOf(
            Product(
                1L,
                "마리오 그린올리브 300g",
                Price(3980),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
            ),
            Product(
                2L,
                "비비고 통새우 만두 200g",
                Price(81980),
                "https://images.emarteveryday.co.kr/images/product/8801392067167/vSYMPCA3qqbLJjhv.png",
            ),
            Product(
                3L,
                "스테비아 방울토마토 500g",
                Price(89860),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/97/12/2500000351297_1.png",
            ),
            Product(
                4L,
                "디벨라 스파게티면 500g",
                Price(1980),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/85/00/8005121000085_1.png",
            ),
            Product(
                5L,
                "생훈제연어 150g",
                Price(89810),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/00/29/8809433792900_1.png",
            ),
            Product(
                6L,
                "CJ 고메 소바바치킨 소이허니윙 300g",
                Price(89820),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/32/30/8801392033032_1.png",
            ),
            Product(
                7L,
                "아리기 바질패스토 190g",
                Price(8980),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/65/07/8003740660765_1.jpg",
            ),
            Product(
                8L,
                "피코크 초마짬뽕 1240g",
                Price(9980),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/77/16/8809269671677_1.png",
            ),
            Product(
                9L,
                "[병천아우내] 모듬순대",
                Price(11900),
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
            ),
            Product(
                10L,
                "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
                Price(5000),
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg",
            ),
            Product(
                11L,
                "[애슐리] 크런치즈엣지 포테이토피자 495g",
                Price(10900),
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/23efcafe-0765-478f-afe9-f9af7bb9b7df.jpg",
            ),
        )
}
