package woowacourse.shopping.data.remote

import woowacourse.shopping.data.local.entity.CartProductEntity

interface RemoteDataSource {
    fun findProductByPagingWithMock(
        offset: Int,
        pageSize: Int,
    ): List<CartProductEntity>
}
