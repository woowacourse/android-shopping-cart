package woowacourse.shopping.data.datasource.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.data.service.ProductService
import woowacourse.shopping.domain.model.Product

class ProductRemoteDataSource(
    private val service: ProductService,
) {
    private val gson = Gson()

    fun getProductById(
        id: Long,
        onResult: (Result<Product?>) -> Unit,
    ) {
        val response = service.getProductById(id)
        if (response.isSuccessful) {
            response.body?.string()?.let {
                val product = gson.fromJson(it, Product::class.java)
                onResult(Result.success(product))
            } ?: onResult(Result.success(null))
        } else {
            onResult(Result.failure(Exception("HTTP ${response.code}: ${response.message}")))
        }
    }

    fun getProductsByIds(
        ids: List<Long>,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        val response = service.getProductsByIds(ids)
        if (response.isSuccessful) {
            response.body?.string()?.let {
                val type = object : TypeToken<List<Product>>() {}.type
                val products = gson.fromJson<List<Product>>(it, type)
                onResult(Result.success(products))
            } ?: onResult(Result.success(emptyList()))
        } else {
            onResult(Result.failure(Exception("HTTP ${response.code}: ${response.message}")))
        }
    }

    fun getPagedProducts(
        limit: Int,
        offset: Int,
        onResult: (Result<PagedResult<Product>>) -> Unit,
    ) {
        val response = service.getPagedProducts(limit, offset)
        if (response.isSuccessful) {
            response.body?.string()?.let {
                val type = object : TypeToken<PagedResult<Product>>() {}.type
                val pagedResult = gson.fromJson<PagedResult<Product>>(it, type)
                onResult(Result.success(pagedResult))
            } ?: onResult(Result.success(PagedResult(emptyList(), false)))
        } else {
            onResult(Result.failure(Exception("HTTP ${response.code}: ${response.message}")))
        }
    }
}
