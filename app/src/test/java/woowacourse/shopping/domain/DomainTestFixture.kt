package woowacourse.shopping.domain

val TEST_PRODUCT =
    Product(
        id = 0L,
        name = "test product",
        price = Price(10000),
        imageUrl = ImageUrl("test.url"),
    )

val TEST_SHOPPING_CART_ITEMS = createShoppingCartItems()

val NEW_ITEM =
    ShoppingCartItem(
        Product(id = 5L, name = "name", price = Price(10000), imageUrl = ImageUrl("test.url")),
    )

val EXIST_ITEM_ID_0 =
    ShoppingCartItem(
        Product(id = 0L, name = "name", price = Price(10000), imageUrl = ImageUrl("test.url")),
    )

fun createShoppingCartItems(): List<ShoppingCartItem> {
    val items = mutableListOf<ShoppingCartItem>()

    for (idx in 0L until 5L) {
        val product =
            Product(id = idx, name = "name", price = Price(10000), imageUrl = ImageUrl("test.url"))
        items.add(ShoppingCartItem(product))
    }

    return items.toList()
}
