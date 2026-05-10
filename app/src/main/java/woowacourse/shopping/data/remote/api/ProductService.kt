package woowacourse.shopping.data.remote.api

interface ProductService {
    suspend fun getProducts(): String

    suspend fun getProduct(id: String): String
}
