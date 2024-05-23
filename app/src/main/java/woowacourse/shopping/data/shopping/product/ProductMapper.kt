package woowacourse.shopping.data.shopping.product

import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.remote.model.ProductPageResponse
import woowacourse.shopping.remote.model.ProductResponse

fun ProductResponse.toProduct(): Product {
    return Product(
        id = id,
        price = price,
        name = name,
        imageUrl = imageUrl,
    )
}

fun ProductPageResponse.toDataModel(): ProductPageData {
    return ProductPageData(
        pageNumber = pageNumber,
        content = content.map { it.toProduct() },
        totalPages = totalPages,
        pageSize = pageSize,
        totalElements = totalElements,
    )
}
