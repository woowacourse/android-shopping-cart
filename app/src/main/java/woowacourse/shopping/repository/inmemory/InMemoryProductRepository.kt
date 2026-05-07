package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository

object InMemoryProductRepository : ProductRepository {
    private const val PRODUCT_IMAGE_URL = "https://cdn.frame-less.co.kr/news/photo/202510/1344_3681_750.jpg"

    val DARAM =
        Product(
            name = "다람",
            price = Money(10_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val BBOYAMI =
        Product(
            name = "뽀야미",
            price = Money(11_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val BANILLA =
        Product(
            name = "바닐라",
            price = Money(12_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val APPLE =
        Product(
            name = "애플",
            price = Money(13_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val SYANTI =
        Product(
            name = "샤니",
            price = Money(14_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val GLLUMIN =
        Product(
            name = "글루민",
            price = Money(15_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val DARAM2 =
        Product(
            name = "다람2",
            price = Money(10_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val BBOYAMI2 =
        Product(
            name = "뽀야미2",
            price = Money(11_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val BANILLA2 =
        Product(
            name = "바닐라2",
            price = Money(12_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val APPLE2 =
        Product(
            name = "애플2",
            price = Money(13_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val SYANTI2 =
        Product(
            name = "샤니2",
            price = Money(14_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val GLLUMIN2 =
        Product(
            name = "글루민2",
            price = Money(15_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val DARAM3 =
        Product(
            name = "다람3",
            price = Money(10_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val BBOYAMI3 =
        Product(
            name = "뽀야미3",
            price = Money(11_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val BANILLA3 =
        Product(
            name = "바닐라3",
            price = Money(12_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val APPLE3 =
        Product(
            name = "애플3",
            price = Money(13_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val SYANTI3 =
        Product(
            name = "샤니3",
            price = Money(14_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val GLLUMIN3 =
        Product(
            name = "글루민3",
            price = Money(15_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val DARAM4 =
        Product(
            name = "다람4",
            price = Money(10_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val BBOYAMI4 =
        Product(
            name = "뽀야미4",
            price = Money(11_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val BANILLA4 =
        Product(
            name = "바닐라4",
            price = Money(12_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val APPLE4 =
        Product(
            name = "애플4",
            price = Money(13_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val SYANTI4 =
        Product(
            name = "샤니4",
            price = Money(14_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val GLLUMIN4 =
        Product(
            name = "글루민4",
            price = Money(15_000),
            imageUrl = PRODUCT_IMAGE_URL,
        )
    val products =
        Products(
            listOf(
                DARAM,
                BBOYAMI,
                BANILLA,
                APPLE,
                GLLUMIN,
                SYANTI,
                DARAM2,
                BBOYAMI2,
                BANILLA2,
                APPLE2,
                GLLUMIN2,
                SYANTI2,
                DARAM3,
                BBOYAMI3,
                BANILLA3,
                APPLE3,
                GLLUMIN3,
                SYANTI3,
                DARAM4,
                BBOYAMI4,
                BANILLA4,
                APPLE4,
                GLLUMIN4,
                SYANTI4,
            ),
        )
    override val size: Int
        get() = products.count()

    private val productMap: Map<ProductId, Product> = products.associateBy { it.id }

    override suspend fun getProducts(
        fromIndex: Int,
        limit: Int,
    ): Products {
        val safeFrom = fromIndex.coerceIn(0, products.count())
        val safeLimit = limit.coerceAtLeast(0)
        val safeTo = minOf(safeFrom + safeLimit, products.count())

        return Products(products.toList().subList(safeFrom, safeTo))
    }

    override suspend fun hasNext(current: Int) = current < products.toList().lastIndex

    override suspend fun findAllByIds(ids: Set<ProductId>): Map<ProductId, Product> =
        ids
            .mapNotNull { id ->
                productMap[id]?.let { id to it }
            }.toMap()
}
