package woowacourse.shopping.data.product.storage

import woowacourse.shopping.data.product.remote.dto.ProductResponseDto

interface ProductRemoteDataStorage {
    fun load(
        lastProductId: Long?,
        size: Int,
    ): List<ProductResponseDto>
}
