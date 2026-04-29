package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.dto.response.ProductResponseDto

object DummyProductDataSourceImpl: ProductDataSource {
    override fun getProducts(): List<ProductResponseDto> = products

    override fun getProduct(id: String): ProductResponseDto {
        return products.find { it.id == id }?: throw IllegalArgumentException("상품이 존재하지 않습니다.")
    }

    val products = listOf(
        ProductResponseDto(
            id = "product-1",
            imageUrl = "https://picsum.photos/id/1/200/200",
            name = "PET보틀-정사각(420ml)",
            price = 10000
        ),
        ProductResponseDto(
            id = "product-2",
            imageUrl = "https://picsum.photos/id/2/200/200",
            name = "PET보틀-밀크티(370ml)",
            price = 12000
        ),
        ProductResponseDto(
            id = "product-3",
            imageUrl = "https://picsum.photos/id/3/200/200",
            name = "[든든] 동원 스위트콘",
            price = 99800
        ),
        ProductResponseDto(
            id = "product-4",
            imageUrl = "https://picsum.photos/id/4/200/200",
            name = "PET보틀-단지(420ml)",
            price = 10000
        ),
        ProductResponseDto(
            id = "product-5",
            imageUrl = "https://picsum.photos/id/5/200/200",
            name = "PET보틀-원형(500ml)",
            price = 84400
        ),
        ProductResponseDto(
            id = "product-6",
            imageUrl = "https://picsum.photos/id/6/200/200",
            name = "PET보틀-납작(250ml)",
            price = 12000
        )
    )
}
