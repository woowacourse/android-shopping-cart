import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import woowacourse.shopping.data.product.ProductDataModel

interface ProductService {
    @GET("products")
    fun getProductsWithRange(
        @Query("start") start: Int,
        @Query("range") range: Int,
    ): Call<List<ProductDataModel>>

    @GET("product")
    fun getProductById(
        @Query("id") id: Int,
    ): Call<ProductDataModel>
}
