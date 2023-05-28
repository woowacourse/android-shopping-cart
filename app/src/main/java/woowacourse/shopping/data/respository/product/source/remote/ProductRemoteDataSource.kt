package woowacourse.shopping.data.respository.product.source.remote

import woowacourse.shopping.data.model.ProductEntity

interface ProductRemoteDataSource {
    fun requestDatas(startPosition: Int): List<ProductEntity>
    fun requestData(productId: Long): ProductEntity
}
