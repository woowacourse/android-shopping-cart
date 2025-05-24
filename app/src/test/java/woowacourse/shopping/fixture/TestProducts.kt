@file:Suppress("ktlint")

package woowacourse.shopping.fixture

import woowacourse.shopping.domain.Product

object TestProducts {
    val products =
        """
        [
          {
            "id": 1,
            "name": "[병천아우내] 모듬순대",
            "price": 11900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg"
          },
          {
            "id": 2,
            "name": "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
            "price": 5000,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg"
          },
          {
            "id": 3,
            "name": "[애슐리] 크런치즈엣지 포테이토피자 495g",
            "price": 10900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/23efcafe-0765-478f-afe9-f9af7bb9b7df.jpg"
          },
          {
            "id": 4,
            "name": "치밥하기 좋은 순살 바베큐치킨",
            "price": 13990,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f864b361-85da-4482-aec8-909397caac4e.jpg"
          },
          {
            "id": 5,
            "name": "[이연복의 목란] 짜장면 2인분",
            "price": 9980,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/90256eb2-b02f-493a-ab7a-29a8724254e4.jpeg"
          },
          {
            "id": 6,
            "name": "[콜린스 다이닝] 마르게리타 미트볼",
            "price": 11400,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f92fa98a-524c-431e-a974-e32fcc8fe2ca.jpg"
          },
          {
            "id": 7,
            "name": "[투다리] 푸짐한 김치어묵 우동전골",
            "price": 13900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1568a441-bb45-4732-8a69-c599aa8ecfbf.jpg"
          },
          {
            "id": 8,
            "name": "[투다리] 한우대창나베",
            "price": 17200,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/997b370e-16a5-473d-9586-4dc97f1530aa.jpg
          },
          {
            "id": 9,
            "name": "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
            "price": 42000,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3c68d05b-d392-4a38-8637-a25068220fa4.jpg"
          },
          {
            "id": 10,
            "name": "[투다리] 오리지널 알탕",
            "price": 14900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/ace6bb54-2434-4ca0-86ab-5bea178f5669.jpg"
          },
          {
            "id": 11,
            "name": "[소반옥] 왕갈비탕 1kg",
            "price": 11900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/860123d3-be82-4c90-ae47-0b56e2869eca.jpg"
          },
          {
            "id": 12,
            "name": "[금룡각] 마라탕",
            "price": 15900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/0634a5e8-51e8-4dcd-8b82-6f6237e8c261.jpg"
          },
          {
            "id": 13,
            "name": "[모현상회] 대광어회 150g (냉장)",
            "price": 16900,
            "imageUrl": "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/160369196760l0.jpg"
          },
          {
            "id": 14,
            "name": "[사미헌] 우거지 갈비탕",
            "price": 9900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/fe13821c-60a1-4c99-bdc7-c360ec445ea0.jpeg"
          },
          {
            "id": 15,
            "name": "[최현석의 쵸이닷] 파스타 인기 메뉴 12종 (택1)",
            "price": 7900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/4b873e8d-b161-45ea-92ad-95d01cc8a9fa.jpg"
          },
          {
            "id": 16,
            "name": "[일상식탁] 부산식 얼큰 낙곱새",
            "price": 18900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/39b48630-0359-4f08-aea3-8193aea1fc52.jpg"
          },
          {
            "id": 17,
            "name": "[본죽] 메추리알 장조림",
            "price": 10900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/b4f210d2-b6b0-4328-9357-c96a875d5b29.jpg"
          },
          {
            "id": 18,
            "name": "[미트클레버] 대구막창",
            "price": 19900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1b2fd7b6-32ad-4c9f-9303-6894b2a8bfb9.jpeg?v=0531"
          },
          {
            "id": 19,
            "name": "[배나무골] 연잎 삼겹살 (냉장)",
            "price": 12900,
            "imageUrl": "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1600669626616l0.jpg"
          },
          {
            "id": 20,
            "name": "[하루한킷] 송탄식 부대찌개",
            "price": 15900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/a872cc78-6dd5-4c7e-9a59-f84128fada19.jpg?v=0531"
          },
          {
            "id": 21,
            "name": "[성수동 팩피 : FAGP] 감바스 파스타",
            "price": 9200,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f16d346d-1a89-4620-8c18-9d29e7666971.jpg"
          },
          {
            "id": 22,
            "name": "[크리스피크림도넛] 오리지널 글레이즈드 (9개입)",
            "price": 16200,
            "imageUrl": "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/164870681737l0.jpg"
          },
          {
            "id": 23,
            "name": "[궁] 고추장 제육 돈불고기 600g",
            "price": 9900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/79a25a83-529b-4be2-b67f-e62bdc235f9a.jpg"
          },
          {
            "id": 24,
            "name": "[부산 상국이네] 떡볶이 (2~3인분)",
            "price": 8400,
            "imageUrl": "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1646963339667l0.jpg"
          },
          {
            "id": 25,
            "name": "[최현석의 쵸이닷] 트러플 크림 뇨끼",
            "price": 7900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1d6a8e22-3227-4974-a932-93bbb244e49e.jpg"
          },
          {
            "id": 26,
            "name": "[골라담기] 오뚜기 라면 6종 균일가 (택3)",
            "price": 4180,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/d7c9fb3c-81bc-4f7b-bcf9-dea831cef649.jpg"
          },
          {
            "id": 27,
            "name": "냉동 유기농 블루베리 700g (미국산)",
            "price": 22900,
            "imageUrl": "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1653037727503l0.jpeg"
          },
          {
            "id": 28,
            "name": "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
            "price": 11900,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg"
          },
          {
            "id": 29,
            "name": "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
            "price": 12980,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg"
          },
          {
            "id": 30,
            "name": "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
            "price": 39700,
            "imageUrl": "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg"
          }
        ]
        
        """.trimIndent()

    val deserialized =
        listOf(
            Product(
                id = 1,
                name = "[병천아우내] 모듬순대",
                price = 11900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
            ),
            Product(
                id = 2,
                name = "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
                price = 5000,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg",
            ),
            Product(
                id = 3,
                name = "[애슐리] 크런치즈엣지 포테이토피자 495g",
                price = 10900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/23efcafe-0765-478f-afe9-f9af7bb9b7df.jpg",
            ),
            Product(
                id = 4,
                name = "치밥하기 좋은 순살 바베큐치킨",
                price = 13990,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f864b361-85da-4482-aec8-909397caac4e.jpg",
            ),
            Product(
                id = 5,
                name = "[이연복의 목란] 짜장면 2인분",
                price = 9980,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/90256eb2-b02f-493a-ab7a-29a8724254e4.jpeg",
            ),
            Product(
                id = 6,
                name = "[콜린스 다이닝] 마르게리타 미트볼",
                price = 11400,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f92fa98a-524c-431e-a974-e32fcc8fe2ca.jpg",
            ),
            Product(
                id = 7,
                name = "[투다리] 푸짐한 김치어묵 우동전골",
                price = 13900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1568a441-bb45-4732-8a69-c599aa8ecfbf.jpg",
            ),
            Product(
                id = 8,
                name = "[투다리] 한우대창나베",
                price = 17200,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/997b370e-16a5-473d-9586-4dc97f1530aa.jpg",
            ),
            Product(
                id = 9,
                name = "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
                price = 42000,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3c68d05b-d392-4a38-8637-a25068220fa4.jpg",
            ),
            Product(
                id = 10,
                name = "[투다리] 오리지널 알탕",
                price = 14900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/ace6bb54-2434-4ca0-86ab-5bea178f5669.jpg",
            ),
            Product(
                id = 11,
                name = "[소반옥] 왕갈비탕 1kg",
                price = 11900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/860123d3-be82-4c90-ae47-0b56e2869eca.jpg",
            ),
            Product(
                id = 12,
                name = "[금룡각] 마라탕",
                price = 15900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/0634a5e8-51e8-4dcd-8b82-6f6237e8c261.jpg",
            ),
            Product(
                id = 13,
                name = "[모현상회] 대광어회 150g (냉장)",
                price = 16900,
                imageUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/160369196760l0.jpg",
            ),
            Product(
                id = 14,
                name = "[사미헌] 우거지 갈비탕",
                price = 9900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/fe13821c-60a1-4c99-bdc7-c360ec445ea0.jpeg",
            ),
            Product(
                id = 15,
                name = "[최현석의 쵸이닷] 파스타 인기 메뉴 12종 (택1)",
                price = 7900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/4b873e8d-b161-45ea-92ad-95d01cc8a9fa.jpg",
            ),
            Product(
                id = 16,
                name = "[일상식탁] 부산식 얼큰 낙곱새",
                price = 18900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/39b48630-0359-4f08-aea3-8193aea1fc52.jpg",
            ),
            Product(
                id = 17,
                name = "[본죽] 메추리알 장조림",
                price = 10900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/b4f210d2-b6b0-4328-9357-c96a875d5b29.jpg",
            ),
            Product(
                id = 18,
                name = "[미트클레버] 대구막창",
                price = 19900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1b2fd7b6-32ad-4c9f-9303-6894b2a8bfb9.jpeg?v=0531",
            ),
            Product(
                id = 19,
                name = "[배나무골] 연잎 삼겹살 (냉장)",
                price = 12900,
                imageUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1600669626616l0.jpg",
            ),
            Product(
                id = 20,
                name = "[하루한킷] 송탄식 부대찌개",
                price = 15900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/a872cc78-6dd5-4c7e-9a59-f84128fada19.jpg?v=0531",
            ),
            Product(
                id = 21,
                name = "[성수동 팩피 : FAGP] 감바스 파스타",
                price = 9200,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/f16d346d-1a89-4620-8c18-9d29e7666971.jpg",
            ),
            Product(
                id = 22,
                name = "[크리스피크림도넛] 오리지널 글레이즈드 (9개입)",
                price = 16200,
                imageUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/164870681737l0.jpg",
            ),
            Product(
                id = 23,
                name = "[궁] 고추장 제육 돈불고기 600g",
                price = 9900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/79a25a83-529b-4be2-b67f-e62bdc235f9a.jpg",
            ),
            Product(
                id = 24,
                name = "[부산 상국이네] 떡볶이 (2~3인분)",
                price = 8400,
                imageUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1646963339667l0.jpg",
            ),
            Product(
                id = 25,
                name = "[최현석의 쵸이닷] 트러플 크림 뇨끼",
                price = 7900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/1d6a8e22-3227-4974-a932-93bbb244e49e.jpg",
            ),
            Product(
                id = 26,
                name = "[골라담기] 오뚜기 라면 6종 균일가 (택3)",
                price = 4180,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/d7c9fb3c-81bc-4f7b-bcf9-dea831cef649.jpg",
            ),
            Product(
                id = 27,
                name = "냉동 유기농 블루베리 700g (미국산)",
                price = 22900,
                imageUrl = "https://img-cf.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/shop/data/goods/1653037727503l0.jpeg",
            ),
            Product(
                id = 28,
                name = "[애슐리] 홈스토랑 볶음밥 6종 (4개입) (택1)",
                price = 11900,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/26376544-1943-4773-8665-7f7a1fa1dfb5.jpg",
            ),
            Product(
                id = 29,
                name = "[더건강한] 닭가슴살 2종 (100g*4) (택1)",
                price = 12980,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3d8a3861-f778-44ef-bff1-d665be4d8f19.jpg",
            ),
            Product(
                id = 30,
                name = "[태우한우] 1+ 한우 안심 스테이크 200g (냉장)",
                price = 39700,
                imageUrl = "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/c1ea8fff-29d9-4e12-b2f1-667d76e2bdc9.jpeg",
            ),
        )
}
