package woowacourse.shopping.ui.screens.product

import woowacourse.shopping.domain.repository.ProductRepository

class ProductStateHolder(
    productManager: ProductRepository,
) {
    val products = productManager.getProducts()
}
