package woowacourse.shopping.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductAndCount
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface CartRepository {
    fun getCartProducts(): List<ProductAndCount>
    @OptIn(ExperimentalUuidApi::class)
    fun addProduct(product: Product)
    @OptIn(ExperimentalUuidApi::class)
    fun deleteProduct(productId: Uuid)
}
