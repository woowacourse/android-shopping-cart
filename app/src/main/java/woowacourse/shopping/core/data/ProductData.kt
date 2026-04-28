package woowacourse.shopping.core.data

import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.model.Money
import woowacourse.shopping.core.model.Product
import woowacourse.shopping.core.model.ProductName

object ProductData {
    val products =
        listOf(
            Product(
                name = ProductName("아메리카노"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                name = ProductName("우디 카우보이 쿠키 콜드 브루"),
                price = Money(7000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000006920]_20260408132904084.jpg",
            ),
            Product(
                name = ProductName("나이트로 바닐라 크림"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                name = ProductName("초코 바나나 마카다미아 오트 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("제주 말차 크림 프라푸치노"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002502]_20250626100915378.jpg",
            ),
            Product(
                name = ProductName("아이스티"),
                price = Money(4000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[4004000000059]_20250626095245748.jpg",
            ),
            Product(
                name = ProductName("딸기 아사이 레모네이드 스타벅스 리프레셔"),
                price = Money(9000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000004753]_20250626175350251.jpg",
            ),
            Product(
                name = ProductName("서울 석양 오미자 피지오"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
        ).toImmutableList()

    fun getProductById(id: String): Product? = products.firstOrNull { it.id == id }
}
