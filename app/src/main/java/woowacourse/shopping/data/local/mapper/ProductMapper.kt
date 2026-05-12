package woowacourse.shopping.data.local.mapper

import woowacourse.shopping.data.local.entity.ProductEntity
import woowacourse.shopping.domain.Product
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun ProductEntity.toDomain(): Product {
    val parsedProductId =
        Uuid.parseOrNull(productId) ?: throw IllegalArgumentException("잘못된 productId 형식 입니다.")

    return Product(
        productId = parsedProductId,
        imageUrl = imageUrl,
        productName = productName,
        price = price,
    )
}

@OptIn(ExperimentalUuidApi::class)
fun Product.toEntity(): ProductEntity =
    ProductEntity(
        productId = productId.toString(),
        imageUrl = imageUrl,
        productName = productName,
        price = price,
    )
