package woowacourse.shopping.data.dummy

import woowacourse.shopping.domain.Product

@Suppress("ktlint:standard:max-line-length")
object ProductData {
    private val items: List<Product> =
        listOf(
            Product(
                0,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_157/1715596288161xxA8S_JPEG/%B7%D5%BA%ED%B7%A2.JPG",
                name = "롱블랙",
                price = 2_300,
            ),
            Product(
                1,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_109/1715596308956Qe7Ct_JPEG/%B7%D5%BA%ED%B7%A2_%B6%F3%B6%BC.JPG",
                name = "롱블랙 라떼",
                price = 3_000,
            ),
            Product(
                2,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_161/1715596441385iiOuC_JPEG/%B9%D9%B4%D2%B6%F3%B6%F3%B6%BC.jpg",
                name = "바닐라 카페 라떼",
                price = 3_800,
            ),
            Product(
                3,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_268/17155964592709Bxgb_JPEG/%C7%EC%C0%CC%C1%F1%B3%D3%B6%F3%B6%BC.jpg",
                name = "헤이즐넛 카페 라떼",
                price = 3_800,
            ),
            Product(
                4,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_50/1715596488074wl7IL_JPEG/%B6%F4%BF%C2%C5%A9%B8%B2%B6%F3%B6%BC.JPG",
                name = "크림 라떼",
                price = 4_200,
            ),
            Product(
                5,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_190/1715596505969Ovyop_JPEG/%BE%F3%B1%D7%B7%B9%C0%CC%C5%A9%B8%B2.JPG",
                name = "얼그레이 크림 라떼",
                price = 4_300,
            ),
            Product(
                6,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_41/1715596527658w4zFY_JPEG/%BD%C3%B3%AA%B8%F3%C5%A9%B8%B2.JPG",
                name = "시나몬 크림 라떼",
                price = 4_300,
            ),
            Product(
                7,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_144/1715596583694p6jyR_JPEG/%C4%DA%C4%DA%B3%D3_%C5%A9%B8%B2.JPG",
                name = "코코넛 크림 라떼",
                price = 4_300,
            ),
            Product(
                8,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_111/1715596625693FYpkx_JPEG/%BF%A1%BD%BA%C7%C1%B7%B9%BC%D2.JPG",
                name = "에스프레소",
                price = 2_000,
            ),
            Product(
                9,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_44/1715596664853pkPWq_JPEG/%BF%A1%BD%BA%C7%C1%B7%B9%BC%D2_%C4%DC%C6%C7%B3%AA.JPG",
                name = "에스프레소 콘 판나",
                price = 2_500,
            ),
            Product(
                10,
                imageUrl = "https://naverbooking-phinf.pstatic.net/20240513_8/1715596689426STtY2_JPEG/%B8%B6%B3%A2%BE%DF%B6%C7.JPG",
                name = "에스프레소 마키아또",
                price = 2_500,
            ),
        )

    val products: List<Product> =
        (0..4).flatMap { i ->
            items.map { it.copy(id = it.id + i * items.size) }
        }
}
