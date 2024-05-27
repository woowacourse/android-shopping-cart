package woowacourse.shopping.data.local

import woowacourse.shopping.data.local.mapper.toDomain
import woowacourse.shopping.data.local.mapper.toEntity
import woowacourse.shopping.data.remote.RemoteDataSource
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Recent
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.Repository

class RepositoryImpl(private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource) : Repository {
    override fun findProductByPaging(
        offset: Int,
        pageSize: Int,
    ): Result<List<CartProduct>> =
        runCatching {
            localDataSource.findProductByPaging(offset, pageSize).map { it.toDomain() }
        }

    override fun findProductByPagingWithMock(
        offset: Int,
        pageSize: Int,
    ): Result<List<CartProduct>> =
        runCatching {
            remoteDataSource.findProductByPagingWithMock(offset, pageSize).map { it.toDomain() }
        }

    override fun findCartByPaging(
        offset: Int,
        pageSize: Int,
    ): Result<List<CartProduct>> =
        runCatching {
            localDataSource.findCartByPaging(offset, pageSize).map { it.toDomain() }
        }

    override fun findByLimit(limit: Int): Result<List<RecentProduct>> =
        runCatching {
            localDataSource.findByLimit(limit).map { it.toDomain() }
        }

    override fun findOne(): Result<RecentProduct?> =
        runCatching {
            localDataSource.findOne()?.toDomain()
        }

    override fun findProductById(id: Long): Result<CartProduct?> =
        runCatching {
            localDataSource.findProductById(id)?.toDomain()
        }

    override fun saveCart(cart: Cart): Result<Long> =
        runCatching {
            localDataSource.saveCart(cart.toEntity())
        }

    override fun saveRecent(recent: Recent): Result<Long> =
        runCatching {
            localDataSource.saveRecent(recent.toEntity())
        }

    override fun deleteCart(id: Long): Result<Long> =
        runCatching {
            localDataSource.deleteCart(id)
        }

    override fun getMaxCartCount(): Result<Int> =
        runCatching {
            localDataSource.getMaxCartCount()
        }
}
