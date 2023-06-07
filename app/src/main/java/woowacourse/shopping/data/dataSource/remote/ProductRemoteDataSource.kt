package woowacourse.shopping.data.dataSource.remote

import woowacourse.shopping.data.remote.responseDto.ProductResponseDto
import woowacourse.shopping.domain.util.WoowaResult

interface ProductRemoteDataSource {

    fun getAllProducts(unit: Int, lastIndex: Int): WoowaResult<List<ProductResponseDto>>

    fun getProduct(productId: Long): WoowaResult<ProductResponseDto>
}
