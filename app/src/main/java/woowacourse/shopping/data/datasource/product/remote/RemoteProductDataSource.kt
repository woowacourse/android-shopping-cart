package woowacourse.shopping.data.datasource.product.remote

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.datasource.product.ProductDataSource
import woowacourse.shopping.data.mockserver.ShoppingCartMockServer
import woowacourse.shopping.data.model.DataProduct
import kotlin.concurrent.thread

class RemoteProductDataSource(private val shoppingCartMockServer: ShoppingCartMockServer) :
    ProductDataSource.Remote {

    init {
        shoppingCartMockServer.start()
    }

    override fun getPartially(size: Int, lastId: Int): List<DataProduct> {
        shoppingCartMockServer.join()
        val url = "${shoppingCartMockServer.BASE_URL}/product?lastId=${lastId}size=$size"
        val okHttpClient = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val gson = Gson()
        var test: List<DataProduct> = listOf()

        thread {
            test = gson.fromJson(
                okHttpClient.newCall(request).execute().body?.string(),
                Array<DataProduct>::class.java
            ).toList()
        }.join()

        return test
    }
}
