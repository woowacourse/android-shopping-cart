package woowacourse.shopping.model.db

interface CartDao {
    fun itemSize(): Int

    fun save(cart: Cart): Long

    fun find(id: Long): Cart

    fun findAll(): List<Cart>

    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Cart>

    fun delete(id: Long)

    fun deleteByProductId(productId: Long)

    fun plusQuantityByProductId(productId: Long)

    fun minusQuantityByProductId(productId: Long)

    fun deleteAll()
}
