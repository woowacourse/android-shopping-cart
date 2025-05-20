package woowacourse.shopping.data.dummy

import woowacourse.shopping.domain.Product

@Suppress("ktlint:standard:max-line-length")
object ProductData {
    private val items: List<Product> =
        listOf(
            Product(
                1L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_26/1715596375297jOjfM_JPEG/%BE%C6%C0%CC%BD%BA%BE%C6%B8%DE%B8%AE%C4%AB%B3%EB24.jpg",
                name = "아메리카노",
                price = 2_500,
            ),
            Product(
                2L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_157/1715596288161xxA8S_JPEG/%B7%D5%BA%ED%B7%A2.JPG",
                name = "롱블랙",
                price = 2_300,
            ),
            Product(
                3L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_109/1715596308956Qe7Ct_JPEG/%B7%D5%BA%ED%B7%A2_%B6%F3%B6%BC.JPG",
                name = "롱블랙 라떼",
                price = 3_000,
            ),
            Product(
                4L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_50/1715596488074wl7IL_JPEG/%B6%F4%BF%C2%C5%A9%B8%B2%B6%F3%B6%BC.JPG",
                name = "크림 라떼",
                price = 4_200,
            ),
            Product(
                5L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_161/1715596441385iiOuC_JPEG/%B9%D9%B4%D2%B6%F3%B6%F3%B6%BC.jpg",
                name = "바닐라 카페 라떼",
                price = 3_800,
            ),
            Product(
                6L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_111/1715596625693FYpkx_JPEG/%BF%A1%BD%BA%C7%C1%B7%B9%BC%D2.JPG",
                name = "에스프레소",
                price = 2_000,
            ),
            Product(
                7L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_26/1715596375297jOjfM_JPEG/%BE%C6%C0%CC%BD%BA%BE%C6%B8%DE%B8%AE%C4%AB%B3%EB24.jpg",
                name = "아메리카노",
                price = 2_500,
            ),
            Product(
                8L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_157/1715596288161xxA8S_JPEG/%B7%D5%BA%ED%B7%A2.JPG",
                name = "롱블랙",
                price = 2_300,
            ),
            Product(
                9L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_109/1715596308956Qe7Ct_JPEG/%B7%D5%BA%ED%B7%A2_%B6%F3%B6%BC.JPG",
                name = "롱블랙 라떼",
                price = 3_000,
            ),
            Product(
                10L,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_50/1715596488074wl7IL_JPEG/%B6%F4%BF%C2%C5%A9%B8%B2%B6%F3%B6%BC.JPG",
                name = "크림 라떼",
                price = 4_200,
            ),
        )

    val products: List<Product> =
        (0..5).flatMap { i ->
            items.map { it.copy(id = it.id + i * 10) }
        }
}
