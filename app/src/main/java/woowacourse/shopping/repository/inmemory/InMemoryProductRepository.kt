package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository

object InMemoryProductRepository : ProductRepository {
    val DARAM = Product(
        name = "다람",
        price = Money(10_000),
        imageUrl = "https://i.namu.wiki/i/MVXBZRYvGBlNoYN2ZzV9Ttk68Euao5BK_eK4FpCF30TjqAH4FmkkLWlB-jJcnejeoSpo5lsIqqU1abloNZ4mdw.webp"
    )

    val BBOYAMI = Product(
        name = "뽀야미",
        price = Money(11_000),
        imageUrl = "https://i.namu.wiki/i/Fl6hgrl84FWyeRUFM42oScLecflbDFrnMvgNmTo15v7WZpFj_m_EQ7XAK7EX-0G8x7l10E4NqLaTUFL5J6LZCQ.webp"
    )

    val BANILLA = Product(
        name = "바닐라",
        price = Money(12_000),
        imageUrl = "https://i.namu.wiki/i/cjd1kMdYSIteKb_SQyWcrfz6WEEpLQi1RsvUPoEsPxWrVGZKoRBGhBTz0F2y9-6ws0XPD9jQgCfhQgYioHNJrw.webp"
    )

    val APPLE = Product(
        name = "애플",
        price = Money(13_000),
        imageUrl = "https://i.namu.wiki/i/dmM9VlFuGbkvS3b4B9uEcNntBb7egFMySJi9MFSY2_u8DvDEC0W1sysET0UJjPanUz1hiZPQ-s7nWNdYR4Kzgw.webp"
    )

    val SYANTI = Product(
        name = "샤니",
        price = Money(14_000),
        imageUrl = "https://i.namu.wiki/i/uxeGhj6C7SKpvwV_ViQMn_wVojVACIUWpFzPw7ukBjcDaEwt8l32a4un10JhT-nBsY2uayXLkrfg1vzCncXwTw.webp"
    )


    val GLLUMIN = Product(
        name = "글루민",
        price = Money(15_000),
        imageUrl = "https://i.namu.wiki/i/TcJbe57AzBWnJ9jXsj71eI-sdCx5nt63Oxqw_Y9PXOxCcFjAx4d1DTa3Sw35PmVoZZcQX2gpETrwIqOjDfdegA.webp"
    )

    val products = Products(
        listOf(
            DARAM,
            BBOYAMI,
            BANILLA,
            APPLE,
            GLLUMIN,
             SYANTI
        )
    )

    override fun getAllProduct() = products
}
