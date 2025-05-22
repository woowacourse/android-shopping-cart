package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity

class CartDataSourceImpl(
    private val dao: CartDao,
) : CartDataSource {
    override fun getCartProductCount(): Int = dao.getAllProductCount()

    override fun getTotalQuantity(): Int? = dao.getTotalQuantity()

    override fun getCartProducts(): List<CartEntity> = dao.getAllProducts()

    override fun getCartItemById(productId: Long): CartEntity = dao.getCartItemById(productId) ?: throw NoSuchElementException()

    override fun getQuantityById(productId: Long): Int = dao.getQuantityById(productId)

    override fun getPagedCartProducts(
        limit: Int,
        page: Int,
    ): List<CartEntity> {
        val offset = limit * page
        return dao.getPagedProducts(limit, offset)
    }

    override fun increaseQuantity(
        productId: Long,
        quantity: Int,
    ) = dao.increaseQuantity(productId, quantity)

    override fun decreaseQuantity(productId: Long) = dao.decreaseQuantity(productId)

    override fun existsByProductId(productId: Long) = dao.existsByProductId(productId)

    override fun insertProduct(cartEntity: CartEntity) = dao.insertProduct(cartEntity)

    override fun deleteProductById(productId: Long) = dao.deleteProductById(productId)
}
