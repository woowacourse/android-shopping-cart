package woowacourse.shopping.data.cart

interface CartRepository {
    fun insert(cart: Cart): Long

    fun find(id: Long): Cart

    fun findAll(): List<Cart>

    fun delete(id: Long)

    fun deleteByProductId(productId: Long)

    fun deleteAll()

    fun itemSize(): Int

    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Cart>

    fun plusQuantityByProductId(productId: Long)

    fun minusQuantityByProductId(productId: Long)
}
