package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Goods

object GoodsDataBase : GoodsRepository {
    @Suppress("ktlint:standard:max-line-length")
    override fun getAllGoods(): List<Goods> =
        listOf(
            Goods.of(
                "[병천아우내] 모듬순대",
                11900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
            ),
            Goods.of(
                "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
                5000,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg",
            ),
            Goods.of(
                "[애슐리] 크런치즈엣지 포테이토피자 495g",
                10900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/23efcafe-0765-478f-afe9-f9af7bb9b7df.jpg",
            ),
            Goods.of(
                "치밥하기 좋은 순살 바베큐치킨",
                13990,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f864b361-85da-4482-aec8-909397caac4e.jpg",
            ),
            Goods.of(
                "[이연복의 목란] 짜장면 2인분",
                9980,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/90256eb2-b02f-493a-ab7a-29a8724254e4.jpeg",
            ),
            Goods.of(
                "[콜린스 다이닝] 마르게리타 미트볼",
                11400,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f92fa98a-524c-431e-a974-e32fcc8fe2ca.jpg",
            ),
            Goods.of(
                "[투다리] 푸짐한 김치어묵 우동전골",
                13900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1568a441-bb45-4732-8a69-c599aa8ecfbf.jpg",
            ),
            Goods.of(
                "[투다리] 한우대창나베",
                17200,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/997b370e-16a5-473d-9586-4dc97f1530aa.jpg",
            ),
            Goods.of(
                "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
                42000,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3c68d05b-d392-4a38-8637-a25068220fa4.jpg",
            ),
            Goods.of(
                "[투다리] 오리지널 알탕",
                14900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/ace6bb54-2434-4ca0-86ab-5bea178f5669.jpg",
            ),
            Goods.of(
                "[소반옥] 왕갈비탕 1kg",
                11900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/860123d3-be82-4c90-ae47-0b56e2869eca.jpg",
            ),
            Goods.of(
                "[금룡각] 마라탕",
                15900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/0634a5e8-51e8-4dcd-8b82-6f6237e8c261.jpg",
            ),
            Goods.of(
                "[모현상회] 대광어회 150g (냉장)",
                16900,
                "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/160369196760l0.jpg",
            ),
            Goods.of(
                "[사미헌] 우거지 갈비탕",
                9900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/fe13821c-60a1-4c99-bdc7-c360ec445ea0.jpeg",
            ),
            Goods.of(
                "[최현석의 쵸이닷] 파스타 인기 메뉴 12종 (택1)",
                7900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/4b873e8d-b161-45ea-92ad-95d01cc8a9fa.jpg",
            ),
            Goods.of(
                "[일상식탁] 부산식 얼큰 낙곱새",
                18900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/39b48630-0359-4f08-aea3-8193aea1fc52.jpg",
            ),
            Goods.of(
                "[본죽] 메추리알 장조림",
                10900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/b4f210d2-b6b0-4328-9357-c96a875d5b29.jpg",
            ),
            Goods.of(
                "[미트클레버] 대구막창",
                19900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1b2fd7b6-32ad-4c9f-9303-6894b2a8bfb9.jpeg?v=0531",
            ),
            Goods.of(
                "[배나무골] 연잎 삼겹살 (냉장)",
                12900,
                "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1600669626616l0.jpg",
            ),
            Goods.of(
                "[하루한킷] 송탄식 부대찌개",
                15900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/a872cc78-6dd5-4c7e-9a59-f84128fada19.jpg?v=0531",
            ),
            Goods.of(
                "[성수동 팩피 : FAGP] 감바스 파스타",
                9200,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f16d346d-1a89-4620-8c18-9d29e7666971.jpg",
            ),
            Goods.of(
                "[크리스피크림도넛] 오리지널 글레이즈드 (9개입)",
                16200,
                "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/164870681737l0.jpg",
            ),
            Goods.of(
                "[궁] 고추장 제육 돈불고기 600g",
                9900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/79a25a83-529b-4be2-b67f-e62bdc235f9a.jpg",
            ),
            Goods.of(
                "[부산 상국이네] 떡볶이 (2~3인분)",
                8400,
                "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1646963339667l0.jpg",
            ),
            Goods.of(
                "[최현석의 쵸이닷] 트러플 크림 뇨끼",
                7900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1d6a8e22-3227-4974-a932-93bbb244e49e.jpg",
            ),
            Goods.of(
                "[골라담기] 오뚜기 라면 6종 균일가 (택3)",
                4180,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/d7c9fb3c-81bc-4f7b-bcf9-dea831cef649.jpg",
            ),
            Goods.of(
                "냉동 유기농 블루베리 700g (미국산)",
                22900,
                "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1653037727503l0.jpeg",
            ),
            Goods.of(
                "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
                11900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
            ),
            Goods.of(
                "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
                12980,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg",
            ),
            Goods.of(
                "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
                39700,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg",
            ),
        )

    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods> {
        val fromIndex = page * count
        val toIndex = minOf(fromIndex + count, getAllGoods().size)

        return if (fromIndex in 0 until toIndex) {
            getAllGoods().subList(fromIndex, toIndex).toList()
        } else {
            emptyList()
        }
    }
}
