package woowacourse.shopping.data.product

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import woowacourse.shopping.Product
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.repository.ProductRepository

class ProductRepositoryImpl constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
) : ProductRepository {

    override fun findProductById(id: Int, onSuccess: (Product?) -> Unit) {
        productRemoteDataSource.getProductById(id).enqueue(object : Callback<ProductDataModel> {
            override fun onResponse(
                call: Call<ProductDataModel>,
                response: Response<ProductDataModel>,
            ) {
                val product = response.body()
                onSuccess(product?.toDomain())
            }
            override fun onFailure(call: Call<ProductDataModel>, t: Throwable) {
                Log.d("HttpError", t.message.toString())
            }
        })
    }

    override fun getProductsWithRange(
        startIndex: Int,
        size: Int,
        onSuccess: (List<Product>) -> Unit,
    ) {
        productRemoteDataSource.getProductsWithRange(startIndex, size)
            .enqueue(
                object : Callback<List<ProductDataModel>> {
                    override fun onResponse(
                        call: Call<List<ProductDataModel>>,
                        response: Response<List<ProductDataModel>>,
                    ) {
                        val productDataModels = response.body()
                        if (productDataModels != null) onSuccess(productDataModels.map { it.toDomain() })
                    }

                    override fun onFailure(call: Call<List<ProductDataModel>>, t: Throwable) {
                        Log.d("HttpError", t.message.toString())
                    }
                },
            )
    }
}
