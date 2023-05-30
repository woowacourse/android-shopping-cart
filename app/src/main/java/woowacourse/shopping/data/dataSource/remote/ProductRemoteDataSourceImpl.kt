package woowacourse.shopping.data.dataSource.remote

import okhttp3.Call
import okhttp3.FormBody
import okhttp3.RequestBody
import woowacourse.shopping.data.remote.NetworkModule

class ProductRemoteDataSourceImpl : ProductRemoteDataSource {
    override fun getAllProducts(unit: Int, lastIndex: Int): Call {
        val requestBody: RequestBody = FormBody.Builder()
            .add("productId", "$unit")
            .add("lastIndex", "$lastIndex")
            .build()

        return NetworkModule.postService(POST_PRODUCTS_PATH, requestBody)
    }

    override fun getProduct(productId: Long): Call {
        return NetworkModule.getService("$GET_PRODUCT_PATH/$productId")
    }

    companion object {
        private const val POST_PRODUCTS_PATH = "/products"
        private const val GET_PRODUCT_PATH = "/product"
    }
}
