package woowacourse.shopping.data.respository.product

import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.OkHttpClient
import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.ProductModel

class ProductRepositoryImpl(private val cartDao: CartDao) :
    ProductRepository {
    private lateinit var url: HttpUrl
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
        OkHttpClient().request(url) { response ->
            val result = parseResponse(response.body?.string())
            products.addAll(
                result.map {
                    it.copy(count = cartDao.getItemsWithProductCount(it.id) ?: 0).toUIModel()
                }
            )
            callBack(products)
        }
    }

    override fun getProductCount(id: Long): Int {
        return cartDao.getItemsWithProductCount(productId = id) ?: 0
    }

    override fun getDataById(id: Long): ProductModel {
        return (ProductsDao().getDataById(id) ?: ProductsDao().getErrorData()).toUIModel()
    }
}
