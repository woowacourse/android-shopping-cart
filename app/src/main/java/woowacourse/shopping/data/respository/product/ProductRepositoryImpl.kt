package woowacourse.shopping.data.respository.product

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.ProductModel

class ProductRepositoryImpl(private val cartDao: CartDao) :
    ProductRepository {
    private val okHttpClient = OkHttpClient()
    private val requestBuilder = Request.Builder()
    override fun getData(startPosition: Int, count: Int): List<ProductModel> {
        val products: MutableList<ProductModel> = mutableListOf()
        val thread = Thread {
            val url =
                MockWebServer().url("http://localhost:12345/products?startPos=$startPosition&count=$count")
            val request = requestBuilder.url(url).build()
            val response = okHttpClient.newCall(request).execute()
            val result = parseResponse(response.body?.string())
            products.addAll(
                result.map {
                    it.copy(count = cartDao.getItemsWithProductCount(it.id) ?: 0).toUIModel()
                }
            )
        }
        thread.start()
        thread.join()
        return products
    }

    override fun getProductCount(id: Long): Int {
        return cartDao.getItemsWithProductCount(productId = id) ?: 0
    }

    override fun getDataById(id: Long): ProductModel {
        return (ProductsDao().getDataById(id) ?: ProductsDao().getErrorData()).toUIModel()
    }
}
