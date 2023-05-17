package woowacourse.shopping.list.item

class CartProductListItem(
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productPrice: Int,
    val count: Int,
    val checked: Boolean
) : ListItem
