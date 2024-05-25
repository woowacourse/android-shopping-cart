package woowacourse.shopping.data.local

import woowacourse.shopping.data.local.entity.CartProductEntity
import woowacourse.shopping.data.local.mapper.toDomain
import woowacourse.shopping.data.local.mapper.toEntity
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Repository

class RepositoryImpl(private val localDataSource: LocalDataSource): Repository {
    override fun findProductByPaging(offset: Int, pageSize: Int): Result<List<CartProduct>> = runCatching {
        localDataSource.findProductByPaging(offset, pageSize).map { it.toDomain() }
    }

    override fun findCartByPaging(offset: Int, pageSize: Int): Result<List<CartProduct>> = runCatching {
        localDataSource.findCartByPaging(offset, pageSize).map { it.toDomain() }
    }

    override fun saveCart(cart: Cart): Result<Long> = runCatching {
        localDataSource.saveCart(cart.toEntity())
    }

    override fun deleteCart(cartId: Long): Result<Long> = runCatching {
        localDataSource.deleteCart(cartId)
    }
}