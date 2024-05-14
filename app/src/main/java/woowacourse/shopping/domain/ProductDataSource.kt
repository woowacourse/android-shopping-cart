package woowacourse.shopping.domain

interface ProductDataSource {
    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun getProductById(id: Long): Product
}
