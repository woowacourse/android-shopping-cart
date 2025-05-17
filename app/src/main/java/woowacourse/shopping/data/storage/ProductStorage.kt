package woowacourse.shopping.data.storage

import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductResult

@Suppress("ktlint:standard:max-line-length")
class ProductStorage {
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
            Product(
                id = 12,
                name = "치밥하기 좋은 순살 바베큐치킨",
                price = Price(13990),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f864b361-85da-4482-aec8-909397caac4e.jpg",
            ),
            Product(
                id = 13,
                name = "[이연복의 목란] 짜장면 2인분",
                price = Price(9980),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/90256eb2-b02f-493a-ab7a-29a8724254e4.jpeg",
            ),
            Product(
                id = 14,
                name = "[콜린스 다이닝] 마르게리타 미트볼",
                price = Price(11400),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f92fa98a-524c-431e-a974-e32fcc8fe2ca.jpg",
            ),
            Product(
                id = 15,
                name = "[투다리] 푸짐한 김치어묵 우동전골",
                price = Price(13900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1568a441-bb45-4732-8a69-c599aa8ecfbf.jpg",
            ),
            Product(
                id = 16,
                name = "[투다리] 한우대창나베",
                price = Price(17200),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/997b370e-16a5-473d-9586-4dc97f1530aa.jpg",
            ),
            Product(
                id = 17,
                name = "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
                price = Price(42000),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3c68d05b-d392-4a38-8637-a25068220fa4.jpg",
            ),
            Product(
                id = 18,
                name = "[투다리] 오리지널 알탕",
                price = Price(14900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/ace6bb54-2434-4ca0-86ab-5bea178f5669.jpg",
            ),
            Product(
                id = 19,
                name = "[소반옥] 왕갈비탕 1kg",
                price = Price(11900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/860123d3-be82-4c90-ae47-0b56e2869eca.jpg",
            ),
            Product(
                id = 20,
                name = "[금룡각] 마라탕",
                price = Price(15900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/0634a5e8-51e8-4dcd-8b82-6f6237e8c261.jpg",
            ),
            Product(
                id = 21,
                name = "[모현상회] 대광어회 150g (냉장)",
                price = Price(16900),
                imgUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/160369196760l0.jpg",
            ),
            Product(
                id = 22,
                name = "[사미헌] 우거지 갈비탕",
                price = Price(9900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/fe13821c-60a1-4c99-bdc7-c360ec445ea0.jpeg",
            ),
            Product(
                id = 23,
                name = "[최현석의 쵸이닷] 파스타 인기 메뉴 12종 (택1)",
                price = Price(7900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/4b873e8d-b161-45ea-92ad-95d01cc8a9fa.jpg",
            ),
            Product(
                id = 24,
                name = "[일상식탁] 부산식 얼큰 낙곱새",
                price = Price(18900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/39b48630-0359-4f08-aea3-8193aea1fc52.jpg",
            ),
            Product(
                id = 25,
                name = "[본죽] 메추리알 장조림",
                price = Price(10900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/b4f210d2-b6b0-4328-9357-c96a875d5b29.jpg",
            ),
            Product(
                id = 26,
                name = "[미트클레버] 대구막창",
                price = Price(19900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1b2fd7b6-32ad-4c9f-9303-6894b2a8bfb9.jpeg?v=0531",
            ),
            Product(
                id = 27,
                name = "[배나무골] 연잎 삼겹살 (냉장)",
                price = Price(12900),
                imgUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1600669626616l0.jpg",
            ),
            Product(
                id = 28,
                name = "[하루한킷] 송탄식 부대찌개",
                price = Price(15900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/a872cc78-6dd5-4c7e-9a59-f84128fada19.jpg?v=0531",
            ),
            Product(
                id = 29,
                name = "[성수동 팩피 : FAGP] 감바스 파스타",
                price = Price(9200),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f16d346d-1a89-4620-8c18-9d29e7666971.jpg",
            ),
            Product(
                id = 30,
                name = "[크리스피크림도넛] 오리지널 글레이즈드 (9개입)",
                price = Price(16200),
                imgUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/164870681737l0.jpg",
            ),
            Product(
                id = 31,
                name = "[궁] 고추장 제육 돈불고기 600g",
                price = Price(9900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/79a25a83-529b-4be2-b67f-e62bdc235f9a.jpg",
            ),
            Product(
                id = 32,
                name = "[부산 상국이네] 떡볶이 (2~3인분)",
                price = Price(8400),
                imgUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1646963339667l0.jpg",
            ),
            Product(
                id = 33,
                name = "[최현석의 쵸이닷] 트러플 크림 뇨끼",
                price = Price(7900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1d6a8e22-3227-4974-a932-93bbb244e49e.jpg",
            ),
            Product(
                id = 34,
                name = "[골라담기] 오뚜기 라면 6종 균일가 (택3)",
                price = Price(4180),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/d7c9fb3c-81bc-4f7b-bcf9-dea831cef649.jpg",
            ),
            Product(
                id = 35,
                name = "냉동 유기농 블루베리 700g (미국산)",
                price = Price(22900),
                imgUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1653037727503l0.jpeg",
            ),
            Product(
                id = 36,
                name = "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
                price = Price(11900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
            ),
            Product(
                id = 37,
                name = "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
                price = Price(12980),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg",
            ),
            Product(
                id = 38,
                name = "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
                price = Price(39700),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg",
            ),
            Product(
                id = 39,
                name = "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
                price = Price(11900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
            ),
            Product(
                id = 40,
                name = "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
                price = Price(11900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
            ),
            Product(
                id = 41,
                name = "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
                price = Price(11900),
                imgUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
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

        val result = productsValues.subList(fromIndex, endIndex)
        val hasNextPage = endIndex < products.size

        return ProductResult(result, hasNextPage)
    }
}
