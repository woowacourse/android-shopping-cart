package woowacourse.shopping.data.product

import retrofit2.Call

interface ProductRemoteDataSource {

    fun getProductById(id: Int): Call<ProductDataModel>
    fun getProductsWithRange(startIndex: Int, size: Int): Call<List<ProductDataModel>>
}
