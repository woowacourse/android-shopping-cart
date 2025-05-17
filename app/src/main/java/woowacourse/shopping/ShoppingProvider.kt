package woowacourse.shopping

import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository

object ShoppingProvider {
    private var _shoppingCartRepository: ShoppingCartRepository? = null

    val shoppingCartRepository: ShoppingCartRepository get() = _shoppingCartRepository ?: throw IllegalArgumentException()

    fun initProductRepository(repository: ShoppingCartRepository) {
        _shoppingCartRepository = repository
    }
}
