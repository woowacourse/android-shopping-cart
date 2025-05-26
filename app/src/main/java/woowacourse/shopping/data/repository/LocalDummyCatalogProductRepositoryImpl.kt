package woowacourse.shopping.data.repository

import woowacourse.shopping.product.catalog.ProductUiModel

object LocalDummyCatalogProductRepositoryImpl : CatalogProductRepository {
    override fun getAllProductsSize(callback: (Int) -> Unit) {
        callback(dummyProducts.size)
    }

    override fun getProductsInRange(
        startIndex: Int,
        endIndex: Int,
        callback: (List<ProductUiModel>) -> Unit,
    ) {
        callback(dummyProducts.subList(startIndex, endIndex))
    }

    override fun getCartProductsByUids(
        uids: List<Int>,
        callback: (List<ProductUiModel>) -> Unit,
    ) {
        callback(uids.mapNotNull { uid -> dummyProducts.find { product -> product.id == uid } })
    }

    val dummyProducts =
        mutableListOf(
            ProductUiModel(
                id = 1,
                name = "1 아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 2,
                name = "2 아이스 카라멜 마키아또",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110582]_20210415142706229.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 3,
                name = "3 아이스 카푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110601]_20210415143400922.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 4,
                name = "4 딸기 콜드폼 딸기 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 5,
                name = "5 바닐라 크림 콜드 브루",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000487]_20210430112319174.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 6,
                name = "6 베르가못 콜드브루",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000005991]_20250428101116135.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 7,
                name = "7 아이스 스타벅스 돌체 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[128695]_20210426092032110.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 8,
                name = "8 아이스 인절미 크림 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/05/[9200000005285]_20240509131125607.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 9,
                name = "9 아이스 블론드 바닐라 더블 샷 마키아또",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002953]_20210427132718311.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 10,
                name = "10 자바 칩 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/12/[168016]_20241230125952318.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 11,
                name = "11 더블 에스프레소 칩 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/12/[9200000002760]_20241230130417833.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 12,
                name = "12 인절미 제주 말차 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/01/[9200000005782]_20250110103443498.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 13,
                name = "13 딸기 글레이즈드 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/06/[9200000005369]_20240614143554575.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 14,
                name = "14 제주 까망 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2020/09/[9200000002088]_20200921171733536.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 15,
                name = "15 화이트 타이거 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002403]_20210419131548806.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 16,
                name = "16 여수 바다 유자 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9200000004870]_20230905110300704.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 17,
                name = "17 한라봉 천혜향 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/07/[9200000005377]_20240701133052589.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 18,
                name = "18 딸기 딜라이트 요거트 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000003276]_20250410084448397.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 19,
                name = "19 딸기 아사이 레모네이드 스타벅스 리프레셔",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/07/[9200000004753]_20230720103623518.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 20,
                name = "20 스타벅스 슬래머",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
            // test
            ProductUiModel(
                id = 21,
                name = "21 아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 22,
                name = "22 아이스 카라멜 마키아또",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110582]_20210415142706229.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 23,
                name = "23 아이스 카푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110601]_20210415143400922.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 24,
                name = "24 딸기 콜드폼 딸기 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 25,
                name = "25 바닐라 크림 콜드 브루",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000487]_20210430112319174.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 26,
                name = "26 베르가못 콜드브루",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000005991]_20250428101116135.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 27,
                name = "27 아이스 스타벅스 돌체 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[128695]_20210426092032110.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 28,
                name = "28 아이스 인절미 크림 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/05/[9200000005285]_20240509131125607.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 29,
                name = "29 아이스 블론드 바닐라 더블 샷 마키아또",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002953]_20210427132718311.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 30,
                name = "30 자바 칩 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/12/[168016]_20241230125952318.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 31,
                name = "31 더블 에스프레소 칩 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/12/[9200000002760]_20241230130417833.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 32,
                name = "32 인절미 제주 말차 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/01/[9200000005782]_20250110103443498.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 33,
                name = "33 딸기 글레이즈드 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/06/[9200000005369]_20240614143554575.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 34,
                name = "34 제주 까망 크림 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2020/09/[9200000002088]_20200921171733536.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 35,
                name = "35 화이트 타이거 프라푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002403]_20210419131548806.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 36,
                name = "36 여수 바다 유자 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9200000004870]_20230905110300704.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 37,
                name = "37 한라봉 천혜향 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/07/[9200000005377]_20240701133052589.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 38,
                name = "38 딸기 딜라이트 요거트 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000003276]_20250410084448397.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 39,
                name = "39 딸기 아사이 레모네이드 스타벅스 리프레셔",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/07/[9200000004753]_20230720103623518.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 40,
                name = "40 스타벅스 슬래머",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 41,
                name = "41 여수 바다 유자 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9200000004870]_20230905110300704.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 42,
                name = "42 한라봉 천혜향 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/07/[9200000005377]_20240701133052589.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 43,
                name = "43 딸기 딜라이트 요거트 블렌디드",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/04/[9200000003276]_20250410084448397.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 44,
                name = "44 딸기 아사이 레모네이드 스타벅스 리프레셔",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2023/07/[9200000004753]_20230720103623518.jpg",
                price = 10000,
            ),
            ProductUiModel(
                id = 45,
                name = "45 스타벅스 슬래머",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
        )
}
