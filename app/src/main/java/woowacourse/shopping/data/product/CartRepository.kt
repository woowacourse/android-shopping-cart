package woowacourse.shopping.data.product

interface CartRepository {
    fun add(productEntity: ProductEntity)

    fun fetch(id: Long): ProductEntity

    fun fetchAll(): List<ProductEntity>

    fun remove(productEntity: ProductEntity)
}
