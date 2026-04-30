package woowacourse.shopping.feature.shopping.bridge

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.repository.MockRepository
import woowacourse.shopping.core.repository.ProductRepository
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.core.uimodel.toUiModel

class ShoppingBridge(
    private val productRepository: ProductRepository = MockRepository(),
) {
    suspend fun getProductData(
        offset: Int,
        limit: Int,
    ): ImmutableList<ProductUiModel> = productRepository.getProducts(offset, limit).map { it.toUiModel() }.toImmutableList()
}
