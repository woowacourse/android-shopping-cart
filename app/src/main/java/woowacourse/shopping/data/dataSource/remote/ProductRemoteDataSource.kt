package woowacourse.shopping.data.dataSource.remote

import okhttp3.Call

interface ProductRemoteDataSource {

    fun getAllProducts(unit: Int, lastIndex: Int): Call

    fun getProduct(productId: Long): Call
}
