package woowacourse.shopping.database.product

import woowacourse.shopping.productdetail.ProductUiModel

object MockProduct {

    val products = listOf<ProductUiModel>(
        ProductUiModel(
            id = 0,
            name = "돌체 콜드 브루",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg",
            price = 5000
        ),
        ProductUiModel(
            id = 1,
            name = "민트 콜드 브루",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2022/10/[9200000004312]_20221005145029134.jpg",
            price = 5000
        ),
        ProductUiModel(
            id = 2,
            name = "에스프레소 마키아또",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[25]_20210415144211211.jpg",
            price = 5500
        ),
        ProductUiModel(
            id = 3,
            name = "카라멜 마키아또",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[126197]_20210415154609863.jpg",
            price = 6500
        ),
        ProductUiModel(
            id = 4,
            name = "라벤더 카페 브레베",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2022/04/[9200000004119]_20220412083025862.png",
            price = 5000
        ),
        ProductUiModel(
            id = 5,
            name = "아이스 더 그린 쑥 크림 라떼",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/02/[9200000004529]_20230206091908618.jpg",
            price = 4500
        ),
        ProductUiModel(
            id = 6,
            name = "아이스 화이트 초콜릿 모카",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110572]_20210415155545375.jpg",
            price = 5000
        ),
        ProductUiModel(
            id = 7,
            name = "클래식 민트 모카",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2022/10/[9200000004313]_20221005145156959.jpg",
            price = 5000
        ),
        ProductUiModel(
            id = 8,
            name = "바닐라 플랫 화이트",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002406]_20210415135507733.jpg",
            price = 6000
        ),
        ProductUiModel(
            id = 9,
            name = "바닐라 아포가토",
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2022/09/[9200000004308]_20220916101121079.jpg",
            price = 5000
        ),
    )
}
