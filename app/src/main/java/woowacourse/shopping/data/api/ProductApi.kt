package woowacourse.shopping.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import woowacourse.shopping.data.model.response.ProductResponse

interface ProductApi {
    @GET("products")
    fun getProducts(
        @Query("lastId") lastId: Int,
        @Query("count") count: Int,
    ): Call<List<ProductResponse>>
}
