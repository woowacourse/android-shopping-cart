package woowacourse.shopping.data.remote.api

import android.util.Log.e
import androidx.core.os.requestProfiling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import woowacourse.shopping.data.remote.dto.ProductResponseDto
import woowacourse.shopping.domain.exception.ProductException
import java.io.IOException

class OkHttpProductApi(
    private val client: OkHttpClient,
    private val baseUrlProvider: () -> String,
    private val json: Json = Json{ignoreUnknownKeys = true}
) : ProductApi {
    override suspend fun getProducts(): List<ProductResponseDto> =
        request("products"){ body ->
            json.decodeFromString(body)
        }

    override suspend fun getProduct(id: String): ProductResponseDto? =
        try{
            request("products/$id"){body ->
                json.decodeFromString(body)
            }
        }catch(e: ProductException.NotFound){
            null
        }


    private suspend fun <T> request(
        path: String,
        parse:(String) -> T,
    ):T = withContext(Dispatchers.IO) {
        val response = execute(path)
        parse(response)
    }

    private fun execute(path: String):String{
        val request = Request.Builder()
            .url("${baseUrlProvider()}$path")
            .get()
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                when {
                    response.code == 404 -> {
                        throw ProductException.NotFound(path)
                    }

                    !response.isSuccessful -> {
                        throw ProductException.ServerError(
                            response.code,
                            response.message,
                        )
                    }
                }
                response.body?.string()
                    ?: throw ProductException.EmptyBody
            }
        } catch (e: IOException) {
            throw ProductException.NetworkError(e)
        }
    }
}
