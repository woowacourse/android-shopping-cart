package woowacourse.shopping.data.respository.product

import org.json.JSONArray
import woowacourse.shopping.data.model.ProductEntity

fun parseResponse(responseBody: String?): List<ProductEntity> {
    return responseBody?.let {
        val productsJsonArray = JSONArray(it)
        val products = mutableListOf<ProductEntity>()
        for (i in 0 until productsJsonArray.length()) {
            val productJson = productsJsonArray.getJSONObject(i)
            val product = productJson.toProduct()
            products.add(product)
        }
        products
    } ?: emptyList()
}
