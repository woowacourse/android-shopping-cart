package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.model.entity.CartProductEntity
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.repository.CartRepository

class CartRepository(
    private val dao: CartDao,
) : CartRepository {
    override fun fetchCartProductDetail(productId: Int): CartProduct? = dao.getCartProductDetailById(productId)?.toDomain()

    override fun fetchCartProducts(
        page: Int,
        size: Int,
    ): List<CartProduct> = dao.getCartProductDetails(page, size).map { it.toDomain() }

    override fun fetchCartItemCount(): Int = dao.getCartItemCount()

    override fun saveCartProduct(
        productId: Int,
        quantity: Int,
    ) {
        dao.insertCartProduct(CartProductEntity(productId, quantity))
    }

    override fun deleteCartProduct(productId: Int) {
        dao.deleteCartProduct(productId)
    }
}
