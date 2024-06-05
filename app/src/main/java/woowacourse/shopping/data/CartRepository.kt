package woowacourse.shopping.data

import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.model.Product
import kotlin.random.Random

class CartRepository(
    private val dao: CartProductDao,
) {
    suspend fun addCartProduct(product: Product) {
        dao.insert(product.toEntity())
    }

    suspend fun getAllCartProducts(): List<CartProductEntity> {
        return dao.getAll()
    }

    suspend fun deleteCartProduct(id: Long) {
        dao.delete(id)
    }
}
