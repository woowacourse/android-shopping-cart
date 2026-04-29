package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductTitle

private val baseNames = listOf(
    "콜라", "사이다", "주스", "물", "커피",
    "우유", "차", "요거트", "아이스크림", "과자"
)

val DUMMY_PRODUCTS: List<Product> = (1..100).map { i ->
    Product(
        id = "product-$i",
        imageUrl = "https://picsum.photos/id/$i/200/200",
        productTitle = ProductTitle("${baseNames[(i - 1) % baseNames.size]} $i"),
        price = Price(i * 1000),
    )
}
