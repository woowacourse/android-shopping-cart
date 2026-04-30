package woowacourse.shopping.domain.model.product

data class ProductItems(
    private val _items: List<Product>,
) {
    val items: List<Product>
        get() = _items.distinctBy { it.id }
}
