package woowacourse.shopping.data.storage

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductResult

@Suppress("ktlint:standard:max-line-length")
object ProductStorage {
    private val products = mutableMapOf<Long, Product>()
    private val productsValues get() = products.values.toList()

    init {
        initialize()
    }

    private fun initialize() {
        listOf(
            Product(
                1L,
                "마리오 그린올리브 300g",
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                Price(3980),
                Quantity(0),
            ),
            Product(
                2L,
                "비비고 통새우 만두 200g",
                "https://images.emarteveryday.co.kr/images/product/8801392067167/vSYMPCA3qqbLJjhv.png",
                Price(81980),
                Quantity(10),
            ),
            Product(
                3L,
                "스테비아 방울토마토 500g",
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/97/12/2500000351297_1.png",
                Price(89860),
                Quantity(10),
            ),
            Product(
                4L,
                "디벨라 스파게티면 500g",
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/85/00/8005121000085_1.png",
                Price(1980),
                Quantity(10),
            ),
            Product(
                5L,
                "생훈제연어 150g",
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/00/29/8809433792900_1.png",
                Price(89810),
                Quantity(10),
            ),
            Product(
                6L,
                "CJ 고메 소바바치킨 소이허니윙 300g",
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/32/30/8801392033032_1.png",
                Price(89820),
                Quantity(5),
            ),
            Product(
                7L,
                "아리기 바질패스토 190g",
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/65/07/8003740660765_1.jpg",
                Price(8980),
                Quantity(5),
            ),
            Product(
                8L,
                "피코크 초마짬뽕 1240g",
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/77/16/8809269671677_1.png",
                Price(9980),
                Quantity(5),
            ),
            Product(
                9L,
                "[병천아우내] 모듬순대",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
                Price(11900),
                Quantity(3),
            ),
            Product(
                10L,
                "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg",
                Price(5000),
                Quantity(8),
            ),
            Product(
                11L,
                "[애슐리] 크런치즈엣지 포테이토피자 495g",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/23efcafe-0765-478f-afe9-f9af7bb9b7df.jpg",
                Price(10900),
                Quantity(2),
            ),
            Product(
                12L,
                "치밥하기 좋은 순살 바베큐치킨",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f864b361-85da-4482-aec8-909397caac4e.jpg",
                Price(13990),
                Quantity(7),
            ),
            Product(
                13L,
                "[이연복의 목란] 짜장면 2인분",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/90256eb2-b02f-493a-ab7a-29a8724254e4.jpeg",
                Price(9980),
                Quantity(6),
            ),
            Product(
                14L,
                "[콜린스 다이닝] 마르게리타 미트볼",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f92fa98a-524c-431e-a974-e32fcc8fe2ca.jpg",
                Price(11400),
                Quantity(2),
            ),
            Product(
                15L,
                "[투다리] 푸짐한 김치어묵 우동전골",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1568a441-bb45-4732-8a69-c599aa8ecfbf.jpg",
                Price(13900),
                Quantity(7),
            ),
            Product(
                16L,
                "[투다리] 한우대창나베",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/997b370e-16a5-473d-9586-4dc97f1530aa.jpg",
                Price(17200),
                Quantity(9),
            ),
            Product(
                17L,
                "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3c68d05b-d392-4a38-8637-a25068220fa4.jpg",
                Price(42000),
                Quantity(1),
            ),
            Product(
                18L,
                "[투다리] 오리지널 알탕",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/ace6bb54-2434-4ca0-86ab-5bea178f5669.jpg",
                Price(14900),
                Quantity(10),
            ),
            Product(
                19L,
                "[소반옥] 왕갈비탕 1kg",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/860123d3-be82-4c90-ae47-0b56e2869eca.jpg",
                Price(11900),
                Quantity(4),
            ),
            Product(
                20L,
                "[금룡각] 마라탕",
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/0634a5e8-51e8-4dcd-8b82-6f6237e8c261.jpg",
                Price(15900),
                Quantity(3),
            ),
            Product(
                21L,
                "[모현상회] 대광어회 150g (냉장)",
                "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/160369196760l0.jpg",
                Price(16900),
                Quantity(5),
            ),
            Product(
                id = 22,
                name = "[사미헌] 우거지 갈비탕",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/fe13821c-60a1-4c99-bdc7-c360ec445ea0.jpeg",
                price = Price(9900),
                quantity = Quantity(10),
            ),
            Product(
                id = 23,
                name = "[최현석의 쵸이닷] 파스타 인기 메뉴 12종 (택1)",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/4b873e8d-b161-45ea-92ad-95d01cc8a9fa.jpg",
                price = Price(7900),
                quantity = Quantity(10),
            ),
            Product(
                id = 24,
                name = "[일상식탁] 부산식 얼큰 낙곱새",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/39b48630-0359-4f08-aea3-8193aea1fc52.jpg",
                price = Price(18900),
                quantity = Quantity(10),
            ),
            Product(
                id = 25,
                name = "[본죽] 메추리알 장조림",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/b4f210d2-b6b0-4328-9357-c96a875d5b29.jpg",
                price = Price(10900),
                quantity = Quantity(10),
            ),
            Product(
                id = 26,
                name = "[미트클레버] 대구막창",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1b2fd7b6-32ad-4c9f-9303-6894b2a8bfb9.jpeg?v=0531",
                price = Price(19900),
                quantity = Quantity(12),
            ),
            Product(
                id = 27,
                name = "[배나무골] 연잎 삼겹살 (냉장)",
                imgUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1600669626616l0.jpg",
                price = Price(12900),
                quantity = Quantity(10),
            ),
            Product(
                id = 28,
                name = "[하루한킷] 송탄식 부대찌개",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/a872cc78-6dd5-4c7e-9a59-f84128fada19.jpg?v=0531",
                price = Price(15900),
                quantity = Quantity(2),
            ),
            Product(
                id = 29,
                name = "[성수동 팩피 : FAGP] 감바스 파스타",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f16d346d-1a89-4620-8c18-9d29e7666971.jpg",
                price = Price(9200),
                quantity = Quantity(20),
            ),
            Product(
                id = 30,
                name = "[크리스피크림도넛] 오리지널 글레이즈드 (9개입)",
                imgUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/164870681737l0.jpg",
                price = Price(16200),
                quantity = Quantity(10),
            ),
            Product(
                id = 31,
                name = "[궁] 고추장 제육 돈불고기 600g",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/79a25a83-529b-4be2-b67f-e62bdc235f9a.jpg",
                price = Price(9900),
                quantity = Quantity(30),
            ),
            Product(
                id = 32,
                name = "[부산 상국이네] 떡볶이 (2~3인분)",
                imgUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1646963339667l0.jpg",
                price = Price(8400),
                quantity = Quantity(10),
            ),
            Product(
                id = 33,
                name = "[최현석의 쵸이닷] 트러플 크림 뇨끼",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1d6a8e22-3227-4974-a932-93bbb244e49e.jpg",
                price = Price(7900),
                quantity = Quantity(0),
            ),
            Product(
                id = 34,
                name = "[골라담기] 오뚜기 라면 6종 균일가 (택3)",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/d7c9fb3c-81bc-4f7b-bcf9-dea831cef649.jpg",
                price = Price(4180),
                quantity = Quantity(8),
            ),
            Product(
                id = 35,
                name = "냉동 유기농 블루베리 700g (미국산)",
                imgUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1653037727503l0.jpeg",
                price = Price(22900),
                quantity = Quantity(9),
            ),
            Product(
                id = 36,
                name = "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
                price = Price(11900),
                quantity = Quantity(10),
            ),
            Product(
                id = 37,
                name = "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg",
                price = Price(12980),
                quantity = Quantity(10),
            ),
            Product(
                id = 38,
                name = "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg",
                price = Price(39700),
                quantity = Quantity(10),
            ),
        ).forEach {
            products[it.id] = it
        }
    }

    operator fun get(id: Long): Product = products[id] ?: throw IllegalArgumentException()

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): ProductResult {
        val endIndex = minOf(toIndex, products.size)

        if (fromIndex >= products.size || fromIndex < 0) {
            return ProductResult(emptyList(), false)
        }

        val result = productsValues.subList(fromIndex, endIndex)
        val hasNextPage = endIndex < products.size

        return ProductResult(result, hasNextPage)
    }
}
