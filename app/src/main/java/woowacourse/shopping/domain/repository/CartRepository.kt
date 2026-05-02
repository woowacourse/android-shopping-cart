package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface CartRepository {
    fun getItems(): Cart

    fun getPagingItems(page: Int): Cart

    fun addProduct(product: Product)

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProduct(productId: Uuid)
}
