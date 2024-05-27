package woowacourse.shopping.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.data.util.convertJsonToList
import woowacourse.shopping.data.util.convertJsonToObject
import woowacourse.shopping.data.util.convertToJson

class CartRemoteDataSource(
    private val baseUrl: String = BASE_URL.dropLast(1),
) : CartDataSource {
    private val client = OkHttpClient()

    override fun fetchCartItems(page: Int): List<CartedProduct> {
        val request =
            Request.Builder()
                .url("$baseUrl/cart-items?page=$page&page-size=$PAGE_SIZE")
                .build()
        val result = client.newCall(request).execute().body?.string()
        return convertJsonToList(
            result ?: "",
            CartedProduct::class.java,
        )
    }

    override fun addCartItem(cartItem: CartItem) {
        val request =
            Request.Builder()
                .post(convertToJson(cartItem).toRequestBody())
                .url("$baseUrl/cart-item")
                .build()
        client.newCall(request).execute()
    }

    override fun fetchTotalCount(): Int {
        val request =
            Request.Builder()
                .url("$baseUrl/cart-item/count")
                .build()
        val result = client.newCall(request).execute().body?.string()
        return convertJsonToObject(
            result ?: "",
            Int::class.java,
        )
    }

    override fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    ) {
        val request =
            Request.Builder()
                .patch("{}".toRequestBody())
                .url("$baseUrl/cart-item?id=$cartItemId&quantity=$quantity")
                .build()
        client.newCall(request).execute()
    }

    override fun removeCartItem(cartItem: CartItem) {
        val request =
            Request.Builder()
                .delete(convertToJson(cartItem).toRequestBody())
                .url("$baseUrl/cart-item")
                .build()
        client.newCall(request).execute()
    }

    override fun removeAll() {
        val request =
            Request.Builder()
                .delete()
                .url("$baseUrl/cart-item/all")
                .build()
        client.newCall(request).execute()
    }

    companion object {
        private const val BASE_URL = "http://localhost:12345/"
        private const val PAGE_SIZE = 5
    }
}
