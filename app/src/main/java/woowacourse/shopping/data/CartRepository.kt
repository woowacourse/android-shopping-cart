package woowacourse.shopping.data

import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.model.Product

class CartRepository(
    private val dao: CartProductDao,
) {
    fun addCartProduct(product: Product) {
        dao.insert(product.toEntity())
    }

    fun getAllCartProducts(): List<CartProductEntity> {
        return dao.getAll()
    }

    fun deleteCartProduct(id: Long) {
        dao.delete(id)
    }
}

//interface CartRepository {
//    suspend fun addCartProduct(product: Product)
//    suspend fun getAllCartProducts(): List<CartProductEntity>
//    suspend fun deleteCartProduct(id: Long)
//}
