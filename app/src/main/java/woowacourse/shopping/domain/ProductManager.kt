package woowacourse.shopping.domain

class ProductManager(
    val products: List<Product>,
) {
    fun getProductById(id: String): Product =
        products.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다. 삐용삐용")
}
