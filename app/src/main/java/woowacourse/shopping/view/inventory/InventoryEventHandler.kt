package woowacourse.shopping.view.inventory

import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem

interface InventoryEventHandler {
    fun onSelectProduct(productId: Int)

    fun onIncreaseQuantity(product: ProductItem)

    fun onDecreaseQuantity(product: ProductItem)

    fun onShowMore()
}
