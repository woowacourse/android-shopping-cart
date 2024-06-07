package woowacourse.shopping.data

import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteCartDataSource {

    private val service: CartService by lazy {
        Retrofit.Builder()
            .baseUrl("BASE_URL")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CartService::class.java)
    }

    suspend fun getAllCartProducts(): Result<List<CartProduct>> {
        val response = service.getCartItems()
        return if (response.isSuccessful) {
            Result.success(response.body() ?: emptyList())
        } else {
            Result.failure(RuntimeException("code: ${response.code()}"))
        }
    }
}
