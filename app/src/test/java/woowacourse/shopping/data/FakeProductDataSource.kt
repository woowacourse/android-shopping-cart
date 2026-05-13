package woowacourse.shopping.data

import woowacourse.shopping.data.remote.ProductRemoteDataSource
import woowacourse.shopping.data.remote.dto.ProductDto
import woowacourse.shopping.data.remote.dto.ProductsResponseDto

class FakeProductDataSource : ProductRemoteDataSource {
    private val products = listOf(
        ProductDto(
            id = "1",
            name = "bolt",
            price = 1800000,
            imageUrl = "",
        ),
        ProductDto(
            id = "2",
            name = "bolt",
            price = 1800000,
            imageUrl = "",
        ),
        ProductDto(
            id = "3",
            name = "bolt",
            price = 1800000,
            imageUrl = "",
        ),
        ProductDto(
            id = "4",
            name = "bolt",
            price = 1800000,
            imageUrl = "",
        ),
    )

    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): ProductsResponseDto {
        val from = page * pageSize
        val pageItems = products.drop(from).take(pageSize)
        val isLast = from + pageSize >= products.size

        return ProductsResponseDto(
            products = pageItems,
            last = isLast,
        )
    }

    override suspend fun getProductById(id: String): ProductDto =
        products.find { it.id == id }
            ?: throw IllegalArgumentException("존재하지 않는 상품 id입니다: $id")

    override suspend fun getProductsByIds(ids: List<String>): List<ProductDto> = products.filter { it.id in ids }
}
