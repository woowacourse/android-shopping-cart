package woowacourse.shopping.data

import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi

object ProductFixture {
    @OptIn(ExperimentalUuidApi::class)
    val productList =
        listOf(
            Product(
                productId = 1,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000006948]_20260415131310868.jpg",
                productName = "프렌치 바닐라 콜드 브루",
                price = Price(10000),
            ),
            Product(
                productId = 2,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/02/[9200000006852]_20260205135209835.jpg",
                productName = "스위트 밀크 커피",
                price = Price(12000),
            ),
            Product(
                productId = 3,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
                productName = "아이스 브루드 커피",
                price = Price(10000),
            ),
            Product(
                productId = 4,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000000415]_20260414102504471.jpg",
                productName = "슈크림 라떼",
                price = Price(12000),
            ),
            Product(
                productId = 5,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000006787]_20260414104858080.jpg",
                productName = "아이스 슈 폼 라떼",
                price = Price(10000),
            ),
            Product(
                productId = 6,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000000418]_20260414103100438.jpg",
                productName = "아이스 슈크림 라떼",
                price = Price(12000),
            ),
            Product(
                productId = 7,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[110563]_20250626094353711.jpg",
                productName = "아이스 카페 아메리카노",
                price = Price(99800),
            ),
            Product(
                productId = 8,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[110582]_20250626095111658.jpg",
                productName = "아이스 카라멜 마키아또",
                price = Price(10000),
            ),
            Product(
                productId = 9,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/10/[9200000006343]_20251029141705796.jpg",
                productName = "바닐라 라떼",
                price = Price(12000),
            ),
            Product(
                productId = 10,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/10/[9200000006346]_20251029141759604.jpg",
                productName = "아이스 바닐라 라떼",
                price = Price(10000),
            ),
            Product(
                productId = 11,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[128695]_20250626095651684.jpg",
                productName = "아이스 스타벅스 돌체 라떼",
                price = Price(12000),
            ),
            Product(
                productId = 12,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[110566]_20250626113005628.jpg",
                productName = "아이스 카페 모카",
                price = Price(10000),
            ),
            Product(
                productId = 13,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[110572]_20250626113205748.jpg",
                productName = "아이스 화이트 초콜릿 모카",
                price = Price(12000),
            ),
            Product(
                productId = 14,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001631]_20210225090916684.jpg",
                productName = "클래식 아포가토",
                price = Price(99800),
            ),
            Product(
                productId = 15,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[168016]_20250626113600873.jpg",
                productName = "자바 칩 프라푸치노",
                price = Price(10000),
            ),
            Product(
                productId = 16,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2024/06/[9200000005369]_20240614143554454.jpg",
                productName = "딸기 글레이즈드 크림 프라푸치노",
                price = Price(12000),
            ),
            Product(
                productId = 17,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/07/[9200000006304]_20250708105445804.jpg",
                productName = "베르가못 딸기 크림 프라푸치노",
                price = Price(10000),
            ),
            Product(
                productId = 18,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/05/[9200000005363]_20260507150652932.jpg",
                productName = "자몽 망고 코코 프라푸치노",
                price = Price(12000),
            ),
            Product(
                productId = 19,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000006939]_20260410134521787.jpg",
                productName = "레드빈 빙수 블렌디드",
                price = Price(10000),
            ),
            Product(
                productId = 20,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[107051]_20250626093749068.jpg}",
                productName = "쿨 라임 피지오",
                price = Price(12000),
            ),
            Product(
                productId = 21,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9300000006547]_20260327123646367.jpg",
                productName = "딸기 생크림 초코 케이크",
                price = Price(99800),
            ),
            Product(
                productId = 22,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9300000006566]_20260427124402362.jpg",
                productName = "말차 둥둥베어리",
                price = Price(10000),
            ),
            Product(
                productId = 23,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9300000006563]_20260326154422966.jpg",
                productName = "말차 티라미수",
                price = Price(12000),
            ),
            Product(
                productId = 24,
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/02/[9300000005594]_20250205141352684.jpg",
                productName = "딸기 촉촉 초코 생크림 케이크",
                price = Price(99800),
            ),
        )
}
