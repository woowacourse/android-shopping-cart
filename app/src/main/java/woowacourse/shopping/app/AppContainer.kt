package woowacourse.shopping.app

import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

object AppContainer {
    val productRepository: ProductRepository =
        ProductRepositoryImpl(
            products = Products(ProductFixture.productList),
        )
    val cartRepository: CartRepository =
        CartRepositoryImpl(
            cart = Cart(),
        )
}
