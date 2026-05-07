package woowacourse.shopping.data

import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.domain.model.Money
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductName

object ProductData {
    val products =
        listOf(
            Product(
                id = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                name = ProductName("아메리카노"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                id = "550e8400-e29b-41d4-a716-446655440000",
                name = ProductName("우디 카우보이 쿠키 콜드 브루"),
                price = Money(7000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000006920]_20260408132904084.jpg",
            ),
            Product(
                id = "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
                name = ProductName("나이트로 바닐라 크림"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                id = "6ba7b811-9dad-11d1-80b4-00c04fd430c8",
                name = ProductName("초코 바나나 마카다미아 오트 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "6ba7b812-9dad-11d1-80b4-00c04fd430c8",
                name = ProductName("제주 말차 크림 프라푸치노"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002502]_20250626100915378.jpg",
            ),
            Product(
                id = "6ba7b814-9dad-11d1-80b4-00c04fd430c8",
                name = ProductName("아이스티"),
                price = Money(4000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[4004000000059]_20250626095245748.jpg",
            ),
            Product(
                id = "886313e1-3b8a-5372-9b90-0c9aedf60d24",
                name = ProductName("딸기 아사이 레모네이드 스타벅스 리프레셔"),
                price = Money(9000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000004753]_20250626175350251.jpg",
            ),
            Product(
                id = "1a2b3c4d-5e6f-4a8b-abcd-ef0123456789",
                name = ProductName("서울 석양 오미자 피지오"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                id = "2b3c4d5e-6f70-4910-bcde-f01234567890",
                name = ProductName("서울 석양 오미자 피지오 2"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                id = "3c4d5e6f-7081-4012-cdef-012345678901",
                name = ProductName("서울 석양 오미자 피지오 3"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                id = "4d5e6f70-8192-4123-defa-123456789012",
                name = ProductName("서울 석양 오미자 피지오 4"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                id = "5e6f7081-9203-4234-efab-234567890123",
                name = ProductName("서울 석양 오미자 피지오 5"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                id = "6f708192-0314-4345-fabc-345678901234",
                name = ProductName("서울 석양 오미자 피지오 6"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                id = "70819203-1425-4456-abcd-456789012345",
                name = ProductName("우디 카우보이 쿠키 콜드 브루 1"),
                price = Money(7000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000006920]_20260408132904084.jpg",
            ),
            Product(
                id = "81920314-2536-4567-bcde-567890123456",
                name = ProductName("나이트로 바닐라 크림 2"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                id = "92031425-3647-4678-cdef-678901234567",
                name = ProductName("초코 바나나 마카다미아 오트 프라푸치노 3"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "a3142536-4758-4789-defa-789012345678",
                name = ProductName("제주 말차 크림 프라푸치노 4"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002502]_20250626100915378.jpg",
            ),
            Product(
                id = "b4253647-5869-4890-efab-890123456789",
                name = ProductName("카페 라떼"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                id = "c5364758-6970-4901-fabc-901234567890",
                name = ProductName("카푸치노"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                id = "d6475869-7081-4012-abcd-012345678901",
                name = ProductName("카라멜 마키아토"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "e7586970-8192-4123-bcde-123456789012",
                name = ProductName("바닐라 라떼"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                id = "f8697081-9203-4234-cdef-234567890123",
                name = ProductName("카페 모카"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "098a7192-0314-4345-defa-345678901234",
                name = ProductName("화이트 초콜릿 모카"),
                price = Money(7000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "19b8a203-1425-4456-efab-456789012345",
                name = ProductName("헤이즐넛 라떼"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                id = "2ac9b314-2536-4567-fabc-567890123456",
                name = ProductName("돌체 라떼"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                id = "3bdac425-3647-4678-abcd-678901234567",
                name = ProductName("에스프레소 콘 파나"),
                price = Money(5000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                id = "4cebd536-4758-4789-bcde-789012345678",
                name = ProductName("에스프레소 마키아토"),
                price = Money(5000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                id = "5dfce647-5869-4890-cdef-890123456789",
                name = ProductName("디카페인 카페 라떼"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
            ),
            Product(
                id = "6e0df758-6970-4901-defa-901234567890",
                name = ProductName("디카페인 콜드 브루"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/04/[9200000006920]_20260408132904084.jpg",
            ),
            Product(
                id = "7f1ea869-7081-4012-efab-012345678901",
                name = ProductName("자몽 허니 블랙 티"),
                price = Money(5500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[4004000000059]_20250626095245748.jpg",
            ),
            Product(
                id = "802fb970-8192-4123-fabc-123456789012",
                name = ProductName("유자 민트 티"),
                price = Money(5500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[4004000000059]_20250626095245748.jpg",
            ),
            Product(
                id = "9130ca81-9203-4234-abcd-234567890123",
                name = ProductName("캐모마일 릴렉서"),
                price = Money(5000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[4004000000059]_20250626095245748.jpg",
            ),
            Product(
                id = "a241db92-0314-4345-bcde-345678901234",
                name = ProductName("잉글리시 브렉퍼스트 티 라떼"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                id = "b352eca3-1425-4456-cdef-456789012345",
                name = ProductName("그린 티 크림 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002502]_20250626100915378.jpg",
            ),
            Product(
                id = "c463fdb4-2536-4567-defa-567890123456",
                name = ProductName("클래식 핫 초콜릿"),
                price = Money(5500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "d5740ec5-3647-4678-efab-678901234567",
                name = ProductName("화이트 핫 초콜릿"),
                price = Money(5500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                id = "e6851fd6-4758-4789-fabc-789012345678",
                name = ProductName("망고 패션 프루트 블렌디드"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000004753]_20250626175350251.jpg",
            ),
            Product(
                id = "f79620e7-5869-4890-abcd-890123456789",
                name = ProductName("딸기 요거트 블렌디드"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000004753]_20250626175350251.jpg",
            ),
            Product(
                id = "08a731f8-6970-4901-bcde-901234567890",
                name = ProductName("자바 칩 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "19b84209-7081-4012-cdef-012345678901",
                name = ProductName("더블 에스프레소 칩 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "2ac95310-8192-4123-defa-123456789012",
                name = ProductName("모카 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "3bda6421-9203-4234-efab-234567890123",
                name = ProductName("바닐라 크림 프라푸치노"),
                price = Money(6000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000002487]_20250626171201110.jpg",
            ),
            Product(
                id = "4ceb7532-0314-4345-fabc-345678901234",
                name = ProductName("카라멜 프라푸치노"),
                price = Money(6500),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/11/[9200000006594]_20251112142046388.jpg",
            ),
            Product(
                id = "5dfc8643-1425-4456-abcd-456789012345",
                name = ProductName("라임 패션 티 피지오"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
            Product(
                id = "6e0d9754-2536-4567-bcde-567890123456",
                name = ProductName("자몽 민트 피지오"),
                price = Money(8000),
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2026/03/[9200000006937]_20260304125026093.jpg",
            ),
        ).toImmutableList()
}
