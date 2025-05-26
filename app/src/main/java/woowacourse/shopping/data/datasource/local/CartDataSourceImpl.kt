package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.entity.CartEntity

class CartDataSourceImpl(
    private val dao: CartDao,
) : CartDataSource {
    override fun getCartProductCount(): Int = dao.getAllProductCount()

    override fun getTotalQuantity(): Int? = dao.getTotalQuantity()

    override fun getQuantityById(productId: Long): Int = dao.getQuantityById(productId)

    override fun getPagedCartProducts(
        limit: Int,
        page: Int,
    ): List<CartEntity> {
        val offset = limit * page
        return dao.getPagedProducts(limit, offset)
    }

    override fun existsByProductId(productId: Long): Boolean = dao.existsByProductId(productId)

    override fun increaseQuantity(
        productId: Long,
        quantity: Int,
    ) = dao.increaseQuantity(productId, quantity)

    override fun decreaseQuantity(productId: Long) = dao.decreaseQuantity(productId)

    override fun insertProduct(cartEntity: CartEntity) = dao.insertProduct(cartEntity)

    override fun deleteProductById(productId: Long) = dao.deleteProductById(productId)
}
