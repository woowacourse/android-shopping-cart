package woowacourse.shopping.data

import retrofit2.Response
import retrofit2.http.GET

interface CartService {
    @GET("cart-items")
    suspend fun getCartItems(): Response<List<CartProduct>>
}
