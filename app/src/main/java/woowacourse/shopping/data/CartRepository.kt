package woowacourse.shopping.data

import kotlinx.coroutines.delay
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.model.Product

class CartRepository(
    private val dao: CartProductDao,
) {
    suspend fun addCartProduct(product: Product) {
        dao.insert(product.toEntity())
    }

    suspend fun getAllCartProducts(): List<CartProductEntity> {
        delay(3_000)
        return dao.getAll()
    }

    suspend fun deleteCartProduct(id: Long) {
        dao.delete(id)
    }
}
