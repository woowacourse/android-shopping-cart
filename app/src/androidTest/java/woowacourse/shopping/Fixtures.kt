@file:Suppress("ktlint")

package woowacourse.shopping

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductUiModel

val inventoryItem = Product(
    0,
    "[병천아우내] 모듬순대",
    11900,
    "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
)

val allInventoryProducts = listOf(
    ProductUiModel(
        Product(
            0,
            "[병천아우내] 모듬순대",
            11900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            1,
            "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
            5000,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            2,
            "[애슐리] 크런치즈엣지 포테이토피자 495g",
            10900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/23efcafe-0765-478f-afe9-f9af7bb9b7df.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            3,
            "치밥하기 좋은 순살 바베큐치킨",
            13990,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f864b361-85da-4482-aec8-909397caac4e.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            4,
            "[이연복의 목란] 짜장면 2인분",
            9980,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/90256eb2-b02f-493a-ab7a-29a8724254e4.jpeg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            5,
            "[콜린스 다이닝] 마르게리타 미트볼",
            11400,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f92fa98a-524c-431e-a974-e32fcc8fe2ca.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            6,
            "[투다리] 푸짐한 김치어묵 우동전골",
            13900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1568a441-bb45-4732-8a69-c599aa8ecfbf.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            7,
            "[투다리] 한우대창나베",
            17200,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/997b370e-16a5-473d-9586-4dc97f1530aa.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            8,
            "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
            42000,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3c68d05b-d392-4a38-8637-a25068220fa4.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            9,
            "[투다리] 오리지널 알탕",
            14900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/ace6bb54-2434-4ca0-86ab-5bea178f5669.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            10,
            "[소반옥] 왕갈비탕 1kg",
            11900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/860123d3-be82-4c90-ae47-0b56e2869eca.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            11,
            "[금룡각] 마라탕",
            15900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/0634a5e8-51e8-4dcd-8b82-6f6237e8c261.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            12,
            "[모현상회] 대광어회 150g (냉장)",
            16900,
            "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/160369196760l0.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            13,
            "[사미헌] 우거지 갈비탕",
            9900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/fe13821c-60a1-4c99-bdc7-c360ec445ea0.jpeg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            14,
            "[최현석의 쵸이닷] 파스타 인기 메뉴 12종 (택1)",
            7900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/4b873e8d-b161-45ea-92ad-95d01cc8a9fa.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            15,
            "[일상식탁] 부산식 얼큰 낙곱새",
            18900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/39b48630-0359-4f08-aea3-8193aea1fc52.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            16,
            "[본죽] 메추리알 장조림",
            10900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/b4f210d2-b6b0-4328-9357-c96a875d5b29.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            17,
            "[미트클레버] 대구막창",
            19900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1b2fd7b6-32ad-4c9f-9303-6894b2a8bfb9.jpeg?v=0531",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            18,
            "[배나무골] 연잎 삼겹살 (냉장)",
            12900,
            "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1600669626616l0.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            19,
            "[하루한킷] 송탄식 부대찌개",
            15900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/a872cc78-6dd5-4c7e-9a59-f84128fada19.jpg?v=0531",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            20,
            "[성수동 팩피 : FAGP] 감바스 파스타",
            9200,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f16d346d-1a89-4620-8c18-9d29e7666971.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            21,
            "[크리스피크림도넛] 오리지널 글레이즈드 (9개입)",
            16200,
            "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/164870681737l0.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            22,
            "[궁] 고추장 제육 돈불고기 600g",
            9900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/79a25a83-529b-4be2-b67f-e62bdc235f9a.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            23,
            "[부산 상국이네] 떡볶이 (2~3인분)",
            8400,
            "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1646963339667l0.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            24,
            "[최현석의 쵸이닷] 트러플 크림 뇨끼",
            7900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1d6a8e22-3227-4974-a932-93bbb244e49e.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            25,
            "[골라담기] 오뚜기 라면 6종 균일가 (택3)",
            4180,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/d7c9fb3c-81bc-4f7b-bcf9-dea831cef649.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            26,
            "냉동 유기농 블루베리 700g (미국산)",
            22900,
            "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1653037727503l0.jpeg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            27,
            "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
            11900,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            28,
            "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
            12980,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg",
        ),
        1,
    ),
    ProductUiModel(
        Product(
            29,
            "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
            39700,
            "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg",
        ),
        1,
    ),
)


val allCartProducts = listOf(
    CartProduct(
        11,
        "[금룡각] 마라탕",
        15900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/0634a5e8-51e8-4dcd-8b82-6f6237e8c261.jpg",
    ),
    CartProduct(
        12,
        "[모현상회] 대광어회 150g (냉장)",
        16900,
        1,
        "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/160369196760l0.jpg",
    ),
    CartProduct(
        13,
        "[사미헌] 우거지 갈비탕",
        9900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/fe13821c-60a1-4c99-bdc7-c360ec445ea0.jpeg",
    ),
    CartProduct(
        14,
        "[최현석의 쵸이닷] 파스타 인기 메뉴 12종 (택1)",
        7900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/4b873e8d-b161-45ea-92ad-95d01cc8a9fa.jpg",
    ),
    CartProduct(
        15,
        "[일상식탁] 부산식 얼큰 낙곱새",
        18900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/39b48630-0359-4f08-aea3-8193aea1fc52.jpg",
    ),
    CartProduct(
        16,
        "[본죽] 메추리알 장조림",
        10900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/b4f210d2-b6b0-4328-9357-c96a875d5b29.jpg",
    ),
    CartProduct(
        17,
        "[미트클레버] 대구막창",
        19900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1b2fd7b6-32ad-4c9f-9303-6894b2a8bfb9.jpeg?v=0531",
    ),
    CartProduct(
        18,
        "[배나무골] 연잎 삼겹살 (냉장)",
        12900,
        1,
        "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1600669626616l0.jpg",
    ),
    CartProduct(
        19,
        "[하루한킷] 송탄식 부대찌개",
        15900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/a872cc78-6dd5-4c7e-9a59-f84128fada19.jpg?v=0531",
    ),
    CartProduct(
        20,
        "[성수동 팩피 : FAGP] 감바스 파스타",
        9200,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f16d346d-1a89-4620-8c18-9d29e7666971.jpg",
    ),
    CartProduct(
        21,
        "[크리스피크림도넛] 오리지널 글레이즈드 (9개입)",
        16200,
        1,
        "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/164870681737l0.jpg",
    ),
    CartProduct(
        22,
        "[궁] 고추장 제육 돈불고기 600g",
        9900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/79a25a83-529b-4be2-b67f-e62bdc235f9a.jpg",
    ),
    CartProduct(
        23,
        "[부산 상국이네] 떡볶이 (2~3인분)",
        8400,
        1,
        "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1646963339667l0.jpg",
    ),
    CartProduct(
        24,
        "[최현석의 쵸이닷] 트러플 크림 뇨끼",
        7900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1d6a8e22-3227-4974-a932-93bbb244e49e.jpg",
    ),
    CartProduct(
        25,
        "[골라담기] 오뚜기 라면 6종 균일가 (택3)",
        4180,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/d7c9fb3c-81bc-4f7b-bcf9-dea831cef649.jpg",
    ),
    CartProduct(
        26,
        "냉동 유기농 블루베리 700g (미국산)",
        22900,
        1,
        "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1653037727503l0.jpeg",
    ),
    CartProduct(
        27,
        "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
        11900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
    ),
    CartProduct(
        28,
        "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
        12980,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg",
    ),
    CartProduct(
        29,
        "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
        39700,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg",
    ),
)

val cartProduct = CartProduct(
    8,
    "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
    42000,
    1,
    "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3c68d05b-d392-4a38-8637-a25068220fa4.jpg",
)

val cartFirstPageProducts = listOf(
    CartProduct(
        11,
        "[금룡각] 마라탕",
        15900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/0634a5e8-51e8-4dcd-8b82-6f6237e8c261.jpg",
    ),
    CartProduct(
        12,
        "[모현상회] 대광어회 150g (냉장)",
        16900,
        1,
        "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/160369196760l0.jpg",
    ),
    CartProduct(
        13,
        "[사미헌] 우거지 갈비탕",
        9900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/fe13821c-60a1-4c99-bdc7-c360ec445ea0.jpeg",
    ),
    CartProduct(
        14,
        "[최현석의 쵸이닷] 파스타 인기 메뉴 12종 (택1)",
        7900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/4b873e8d-b161-45ea-92ad-95d01cc8a9fa.jpg",
    ),
    CartProduct(
        15,
        "[일상식탁] 부산식 얼큰 낙곱새",
        18900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/39b48630-0359-4f08-aea3-8193aea1fc52.jpg",
    ),
)

val cartLastPageProduct = CartProduct(
    28,
    "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
    12980,
    1,
    "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg",
)

val cartLastPageProducts = listOf(
    CartProduct(
        26,
        "냉동 유기농 블루베리 700g (미국산)",
        22900,
        1,
        "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1653037727503l0.jpeg",
    ),
    CartProduct(
        27,
        "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
        11900,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
    ),
    CartProduct(
        28,
        "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
        12980,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg",
    ),
    CartProduct(
        29,
        "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
        39700,
        1,
        "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg",
    ),
)
