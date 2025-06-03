package woowacourse.shopping.repository

import woowacourse.shopping.data.ShoppingCartDataSource
import woowacourse.shopping.data.ShoppingCartEntity
import woowacourse.shopping.model.products.ShoppingCart
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(
    private val shoppingCartDataSource: ShoppingCartDataSource,
) : ShoppingCartRepository {
    override fun updateCart(
        productId: Int,
        quantity: Int,
    ) {
        thread {
            shoppingCartDataSource.upsertCartItem(
                ShoppingCartEntity(
                    productId,
                    quantity,
                ),
            )
        }
    }

    override fun removeCart(productId: Int) {
        thread {
            shoppingCartDataSource.deleteCartItem(
                productId,
            )
        }
    }

    override fun singlePage(
        page: Int,
        size: Int,
        onResult: (List<ShoppingCart>) -> Unit,
    ) {
        thread {
            shoppingCartDataSource
                .cartSinglePage(
                    page,
                    size,
                ).map { it.toDomain() }
        }
    }
}
