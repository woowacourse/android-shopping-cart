package woowacourse.shopping.domain.model.product

data class Products(
    val productItems: List<Product> = emptyList(),
) {
    operator fun plus(products: Products): Products = Products(productItems = productItems + products.productItems)
}
