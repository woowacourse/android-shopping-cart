package woowacourse.shopping.data.dataSource.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.RequestBody
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
        val result = parseToObject<List<ProductResponseDto>>(response.body?.string())

        return when {
            response.isSuccessful -> SUCCESS(result)
            else -> FAIL(Error.Disconnect)
        }
    }

    override fun getProduct(productId: Long): WoowaResult<ProductResponseDto> {
        val response = NetworkModule.getService("$GET_PRODUCT_PATH/${productId.toInt()}").execute()
        val result = parseToObject<ProductResponseDto>(response.body?.string())

        return when {
            response.isSuccessful -> SUCCESS(result)
            else -> FAIL(Error.Disconnect)
        }
    }

    private inline fun <reified T> parseToObject(responseBody: String?): T {
        val listType = object : TypeToken<T>() {}.type
        return Gson().fromJson(responseBody, listType)
    }

    companion object {
        private const val POST_PRODUCTS_PATH = "/products"
        private const val GET_PRODUCT_PATH = "/product"
    }
}
