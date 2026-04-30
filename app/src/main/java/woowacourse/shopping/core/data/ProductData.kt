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
            Product(
                name = ProductName("서울 석양 오미자 피지오 2"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                name = ProductName("서울 석양 오미자 피지오 3"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                name = ProductName("서울 석양 오미자 피지오 4"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                name = ProductName("서울 석양 오미자 피지오 5"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                name = ProductName("서울 석양 오미자 피지오 6"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                name = ProductName("우디 카우보이 쿠키 콜드 브루 1"),
                price = Money(7000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000006920]_20260408132904084.jpg",
            ),
            Product(
                name = ProductName("나이트로 바닐라 크림 2"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                name = ProductName("초코 바나나 마카다미아 오트 프라푸치노 3"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("제주 말차 크림 프라푸치노 4"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002502]_20250626100915378.jpg",
            ),
            Product(
                name = ProductName("카페 라떼"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                name = ProductName("카푸치노"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                name = ProductName("카라멜 마키아토"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("바닐라 라떼"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                name = ProductName("카페 모카"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("화이트 초콜릿 모카"),
                price = Money(7000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("헤이즐넛 라떼"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                name = ProductName("돌체 라떼"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                name = ProductName("에스프레소 콘 파나"),
                price = Money(5000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                name = ProductName("에스프레소 마키아토"),
                price = Money(5000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                name = ProductName("디카페인 카페 라떼"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                name = ProductName("디카페인 콜드 브루"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000006920]_20260408132904084.jpg",
            ),
            Product(
                name = ProductName("자몽 허니 블랙 티"),
                price = Money(5500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[4004000000059]_20250626095245748.jpg",
            ),
            Product(
                name = ProductName("유자 민트 티"),
                price = Money(5500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[4004000000059]_20250626095245748.jpg",
            ),
            Product(
                name = ProductName("캐모마일 릴렉서"),
                price = Money(5000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[4004000000059]_20250626095245748.jpg",
            ),
            Product(
                name = ProductName("잉글리시 브렉퍼스트 티 라떼"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                name = ProductName("그린 티 크림 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002502]_20250626100915378.jpg",
            ),
            Product(
                name = ProductName("클래식 핫 초콜릿"),
                price = Money(5500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("화이트 핫 초콜릿"),
                price = Money(5500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                name = ProductName("망고 패션 프루트 블렌디드"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000004753]_20250626175350251.jpg",
            ),
            Product(
                name = ProductName("딸기 요거트 블렌디드"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000004753]_20250626175350251.jpg",
            ),
            Product(
                name = ProductName("자바 칩 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("더블 에스프레소 칩 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("모카 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("바닐라 크림 프라푸치노"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                name = ProductName("카라멜 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                name = ProductName("라임 패션 티 피지오"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                name = ProductName("자몽 민트 피지오"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
        ).toImmutableList()
}
