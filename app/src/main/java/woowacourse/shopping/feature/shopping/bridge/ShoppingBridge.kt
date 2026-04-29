package woowacourse.shopping.feature.shopping.bridge

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.data.ProductRepository
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.core.uimodel.toUiModel

class ShoppingBridge(
    private val productRepository: ProductRepository = ProductRepository,
) {
    val products: ImmutableList<ProductUiModel> = productRepository.getProducts().map { it.toUiModel() }.toImmutableList()
}
