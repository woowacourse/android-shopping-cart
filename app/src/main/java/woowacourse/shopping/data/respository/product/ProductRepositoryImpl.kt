package woowacourse.shopping.data.respository.product

import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.ProductModel
import java.io.IOException

class ProductRepositoryImpl(private val cartDao: CartDao) :
    ProductRepository {
    private lateinit var url: HttpUrl
    private val okHttpClient = OkHttpClient()
    private val requestBuilder = Request.Builder()
    override fun getData(
        startPosition: Int,
        count: Int,
        callBack: (List<ProductModel>) -> Unit
    ) {
        val products: MutableList<ProductModel> = mutableListOf()
        val thread = Thread {
            url =
                MockWebServer().url("http://localhost:12345/products?startPos=$startPosition&count=$count")
        }
        thread.start()
        thread.join()
        val request = requestBuilder.url(url).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = Unit

            override fun onResponse(call: Call, response: Response) {
                val result = parseResponse(response.body?.string())
                products.addAll(
                    result.map {
                        it.copy(count = cartDao.getItemsWithProductCount(it.id) ?: 0).toUIModel()
                    }
                )
                callBack(products)
            }
        })
    }

    override fun getProductCount(id: Long): Int {
        return cartDao.getItemsWithProductCount(productId = id) ?: 0
    }

    override fun getDataById(id: Long): ProductModel {
        return (ProductsDao().getDataById(id) ?: ProductsDao().getErrorData()).toUIModel()
    }
}
