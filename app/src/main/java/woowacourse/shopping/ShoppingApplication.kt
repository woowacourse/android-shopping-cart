package woowacourse.shopping

import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.domain.repository.product.ProductRepository
import woowacourse.shopping.presentation.cart.CartViewModelFactory
import woowacourse.shopping.presentation.home.HomeViewModelFactory

interface ShoppingApplication {
    val productRepository: ProductRepository
    val cartRepository: CartRepository
    val cartViewModelFactory: CartViewModelFactory
    val homeViewModelFactory: HomeViewModelFactory
}
