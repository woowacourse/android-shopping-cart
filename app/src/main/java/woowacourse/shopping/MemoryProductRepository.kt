package woowacourse.shopping

object MemoryProductRepository : ProductRepository {
    private val products: Set<Product> =
        setOf(
            Product(
                id = 1,
                title = "동원 스위트콘",
                price = 99_800,
                imageUrl = "https://img.dongwonmall.com/dwmall/static_root/model_img/main/153/15327_1_a.jpg?f=webp&q=80",
            ),
        )

    override fun getProduct(productId: Long): Product =
        products.find { it.id == productId } ?: throw IllegalArgumentException("해당 상품을 찾을 수 없습니다.")

    override fun getProducts(): List<Product> = products.toList()
}
