package woowacourse.shopping.data.product

import ProductService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import woowacourse.shopping.data.product.ProductMockWebServer.PORT
import woowacourse.shopping.data.product.ProductMockWebServer.startServer

object ProductServiceHelper : ProductRemoteDataSource {
    init {
        startServer()
    }

    private val retrofitService = Retrofit.Builder()
        .baseUrl("http://localhost:$PORT/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductService::class.java)

    override fun getProductById(id: Int): Call<ProductDataModel> {
        return retrofitService.getProductById(id)
    }

    override fun getProductsWithRange(start: Int, range: Int): Call<List<ProductDataModel>> {
        return retrofitService.getProductsWithRange(start, range)
    }
}
