package woowacourse.shopping.data.local

import woowacourse.shopping.data.local.entity.CartProduct
import woowacourse.shopping.domain.Repository

class RepositoryImpl(private val localDataSource: LocalDataSource): Repository {
    override fun findProductByPaging(offset: Int, pageSize: Int): Result<List<CartProduct>> = runCatching {
        localDataSource.findProductByPaging(offset, pageSize)
    }

    override fun findCartByPaging(offset: Int, pageSize: Int): Result<List<CartProduct>> = runCatching {
        localDataSource.findCartByPaging(offset, pageSize)
    }
}