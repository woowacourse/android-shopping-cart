package woowacourse.shopping.ui.screens.product

import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository

class ProductStateHolder(
    productManager: ProductRepository = ProductRepositoryImpl(),
) {
    val products = productManager.getProducts()
}
