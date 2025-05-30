package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.dao.CartProductDao
import woowacourse.shopping.data.entity.CartProductEntity

class CartProductLocalDataSource(
    private val dao: CartProductDao,
) {
    fun insert(
        cartProductEntity: CartProductEntity,
        onResult: (Result<Unit>) -> Unit,
    ) {
        dao.insert(cartProductEntity)
        onResult(Result.success(Unit))
    }

    fun getTotalCount(onResult: (Result<Int>) -> Unit) {
        val result = dao.getTotalCount()
        onResult(Result.success(result))
    }

    fun getPagedProducts(
        limit: Int,
        offset: Int,
        onResult: (Result<List<CartProductEntity>>) -> Unit,
    ) {
        val result = dao.getPagedProducts(limit, offset)
        onResult(Result.success(result))
    }

    fun getQuantityByProductId(
        productId: Long,
        onResult: (Result<Int?>) -> Unit,
    ) {
        val result = dao.getQuantityByProductId(productId)
        onResult(Result.success(result))
    }

    fun getTotalQuantity(onResult: (Result<Int>) -> Unit) {
        val result = dao.getTotalQuantity()
        onResult(Result.success(result))
    }

    fun updateQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        dao.updateQuantity(productId, quantity)
        onResult(Result.success(Unit))
    }

    fun deleteByProductId(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        dao.deleteByProductId(productId)
        onResult(Result.success(Unit))
    }
}
