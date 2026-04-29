package woowacourse.shopping.feature.products

import androidx.compose.runtime.Composable
import androidx.compose.runtime.retain.retain
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.feature.products.model.ShoppingProductInfo
import woowacourse.shopping.feature.products.model.toUiModel

class ProductsStateHolder(productRepository: ProductRepository) {
    val products: ImmutableList<ShoppingProductInfo> = productRepository.getProducts().items
        .map { it.toUiModel() }
        .toImmutableList()
}

@Composable
fun retainProductsStateHolder(): ProductsStateHolder = retain {
    ProductsStateHolder(ProductRepositoryImpl)
}
