package woowacourse.shopping.data.remote.datasource

import woowacourse.shopping.data.remote.dto.CartItemResponse

interface CartRemoteDataSource {
    suspend fun getCartItems(): List<CartItemResponse>
}
