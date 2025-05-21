package woowacourse.shopping.data

import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.Product
import woowacourse.shopping.product.catalog.ProductUiModel

object MockProducts : ProductsDataSource {
    override fun getProducts(): List<ProductUiModel> = mockProducts.map { it.toUiModel() }

    override fun getProductsSize(): Int = mockProducts.size

    override fun getSubListedProducts(
        startIndex: Int,
        lastIndex: Int,
    ): List<ProductUiModel> = mockProducts.subList(startIndex, lastIndex).map { it.toUiModel() }

    val mockProducts =
        listOf(
            Product(
                name = "아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 카라멜 마키아또",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110582]_20210415142706229.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 카푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110601]_20210415143400922.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 콜드폼 딸기 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
            Product(
                name = "바닐라 크림 콜드 브루",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000487]_20210430112319174.jpg",
                price = 10000,
            ),
            Product(
                name = "베르가못 콜드브루",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000005991]_20250428101116135.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 스타벅스 돌체 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[128695]_20210426092032110.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 인절미 크림 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/05/[9200000005285]_20240509131125607.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 블론드 바닐라 더블 샷 마키아또",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002953]_20210427132718311.jpg",
                price = 10000,
            ),
            Product(
                name = "자바 칩 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/12/[168016]_20241230125952318.jpg",
                price = 10000,
            ),
            Product(
                name = "더블 에스프레소 칩 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/12/[9200000002760]_20241230130417833.jpg",
                price = 10000,
            ),
            Product(
                name = "인절미 제주 말차 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/01/[9200000005782]_20250110103443498.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 글레이즈드 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/06/[9200000005369]_20240614143554575.jpg",
                price = 10000,
            ),
            Product(
                name = "제주 까망 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2020/09/[9200000002088]_20200921171733536.jpg",
                price = 10000,
            ),
            Product(
                name = "화이트 타이거 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002403]_20210419131548806.jpg",
                price = 10000,
            ),
            Product(
                name = "여수 바다 유자 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9200000004870]_20230905110300704.jpg",
                price = 10000,
            ),
            Product(
                name = "한라봉 천혜향 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/07/[9200000005377]_20240701133052589.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 딜라이트 요거트 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000003276]_20250410084448397.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 아사이 레모네이드 스타벅스 리프레셔",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/07/[9200000004753]_20230720103623518.jpg",
                price = 10000,
            ),
            Product(
                name = "스타벅스 슬래머",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
            // test
            Product(
                name = "아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 카라멜 마키아또",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110582]_20210415142706229.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 카푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110601]_20210415143400922.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 콜드폼 딸기 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
            Product(
                name = "바닐라 크림 콜드 브루",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000487]_20210430112319174.jpg",
                price = 10000,
            ),
            Product(
                name = "베르가못 콜드브루",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000005991]_20250428101116135.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 스타벅스 돌체 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[128695]_20210426092032110.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 인절미 크림 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/05/[9200000005285]_20240509131125607.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 블론드 바닐라 더블 샷 마키아또",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002953]_20210427132718311.jpg",
                price = 10000,
            ),
            Product(
                name = "자바 칩 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/12/[168016]_20241230125952318.jpg",
                price = 10000,
            ),
            Product(
                name = "더블 에스프레소 칩 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/12/[9200000002760]_20241230130417833.jpg",
                price = 10000,
            ),
            Product(
                name = "인절미 제주 말차 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/01/[9200000005782]_20250110103443498.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 글레이즈드 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/06/[9200000005369]_20240614143554575.jpg",
                price = 10000,
            ),
            Product(
                name = "제주 까망 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2020/09/[9200000002088]_20200921171733536.jpg",
                price = 10000,
            ),
            Product(
                name = "화이트 타이거 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002403]_20210419131548806.jpg",
                price = 10000,
            ),
            Product(
                name = "여수 바다 유자 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9200000004870]_20230905110300704.jpg",
                price = 10000,
            ),
            Product(
                name = "한라봉 천혜향 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/07/[9200000005377]_20240701133052589.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 딜라이트 요거트 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000003276]_20250410084448397.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 아사이 레모네이드 스타벅스 리프레셔",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/07/[9200000004753]_20230720103623518.jpg",
                price = 10000,
            ),
            Product(
                name = "스타벅스 슬래머",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
            Product(
                name = "여수 바다 유자 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9200000004870]_20230905110300704.jpg",
                price = 10000,
            ),
            Product(
                name = "한라봉 천혜향 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/07/[9200000005377]_20240701133052589.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 딜라이트 요거트 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000003276]_20250410084448397.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 아사이 레모네이드 스타벅스 리프레셔",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/07/[9200000004753]_20230720103623518.jpg",
                price = 10000,
            ),
            Product(
                name = "스타벅스 슬래머",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
        )
}
