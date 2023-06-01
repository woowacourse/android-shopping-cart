package woowacourse.shopping.data.dataSource.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.Response
import woowacourse.shopping.data.remote.NetworkModule
import woowacourse.shopping.data.remote.responseDto.ProductResponseDto
import woowacourse.shopping.domain.util.Error
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS

class ProductRemoteDataSourceImpl : ProductRemoteDataSource {
    override fun getAllProducts(unit: Int, lastIndex: Int): WoowaResult<List<ProductResponseDto>> {
        val requestBody: RequestBody =
            FormBody.Builder().add("unit", "$unit").add("lastIndex", "$lastIndex").build()

        val response = NetworkModule.postService(POST_PRODUCTS_PATH, requestBody).execute()
        Log.d("12312322", "12312344444")
        return getResultByState(response)
    }

    private fun getResultByState(response: Response) = when {
        response.isSuccessful -> {
            SUCCESS(parseToObject(response.body?.string()))
        }

        else -> {
            FAIL(Error.Disconnect)
        }
    }

    private fun parseToObject(responseBody: String?): List<ProductResponseDto> {
        val listType = object : TypeToken<List<ProductResponseDto>>() {}.type
        return Gson().fromJson(responseBody, listType)
    }

    override fun getProduct(productId: Long): Call {
        return NetworkModule.getService("$GET_PRODUCT_PATH/$productId")
    }

    companion object {
        private const val POST_PRODUCTS_PATH = "/products"
        private const val GET_PRODUCT_PATH = "/product"
    }
}
