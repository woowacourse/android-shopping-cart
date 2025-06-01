package woowacourse.shopping.repository

import woowacourse.shopping.data.ShoppingCartDataSource
import woowacourse.shopping.data.ShoppingCartEntity
import woowacourse.shopping.model.products.ShoppingCart

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

    override fun singlePage(
        page: Int,
        size: Int,
    ): List<ShoppingCart> =
        shoppingCartDataSource
            .cartSinglePage(
                page,
                size,
            ).map { it.toDomain() }
}
