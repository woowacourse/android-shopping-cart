package woowacourse.shopping.data.mock

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import woowacourse.shopping.data.remote.dto.ProductResponseDto
import woowacourse.shopping.domain.product.Product

object MockProductResponseFactory {

    private val json = Json {prettyPrint = true}

    fun productsJson(products: List<Product>): String =
        json.encodeToString(
            products.map { it.toResponseDto() },
        )
    fun productJson(product: Product): String =
        json.encodeToString(
            product.toResponseDto(),
        )

    private fun Product.toResponseDto(): ProductResponseDto =
        ProductResponseDto(
            id = id,
            imageUrl = imageUrl.value,
            name = name.value,
            price = price.value,
        )
}
