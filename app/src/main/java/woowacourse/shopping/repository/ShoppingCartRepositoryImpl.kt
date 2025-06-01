package woowacourse.shopping.repository

import woowacourse.shopping.data.ShoppingCartDataSource
import woowacourse.shopping.data.ShoppingCartEntity

class ShoppingCartRepositoryImpl(
    private val shoppingCartDataSource: ShoppingCartDataSource,
) : ShoppingCartRepository {
    override fun addCart(
        productId: Int,
        quantity: Int,
    ) {
        shoppingCartDataSource.upsertCartItem(
            ShoppingCartEntity(
                productId,
                quantity,
            ),
        )
    }

    override fun removeCart(productId: Int) {
        shoppingCartDataSource.deleteCartItem(
            productId,
        )
    }
}
