package woowacourse.shopping.fixture

import woowacourse.shopping.data.cart.CartItem
import woowacourse.shopping.data.cart.CartItemRepository
import woowacourse.shopping.mapper.toCartItem
import woowacourse.shopping.product.catalog.ProductUiModel

class FakeCartItemRepository(
    private val size: Int,
) : CartItemRepository {
    private val items =
        MutableList(size) { index ->
            ProductUiModel(
                id = (index + 1).toLong(),
                name = "${index + 1} 아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 1000 * (index + 1),
                quantity = 0,
            )
        }

    override fun getAllCartItemSize(callback: (Int) -> Unit) {
        callback(items.size)
    }

    override fun getAllCartItem(callback: (List<ProductUiModel>) -> Unit) {
        callback(items.toList())
    }

    override fun subListCartItems(
        startIndex: Int,
        endIndex: Int,
        callback: (List<ProductUiModel>) -> Unit,
    ) {
        callback(items.subList(startIndex, endIndex.coerceAtMost(items.size)))
    }

    override fun insertCartItem(
        product: ProductUiModel,
        onComplete: () -> Unit,
    ) {
        items.add(product)
        onComplete()
    }

    override fun updateCartItem(
        product: ProductUiModel,
        onComplete: () -> Unit,
    ) {
        val index = items.indexOfFirst { it.id == product.id }
        if (index != -1) {
            items[index] = product
        }
        onComplete()
    }

    override fun deleteCartItemById(
        productId: Long,
        onComplete: () -> Unit,
    ) {
        items.removeAll { it.id == productId }
        onComplete()
    }

    override fun findCartItem(
        product: ProductUiModel,
        callback: (CartItem?) -> Unit,
    ) {
        val item = items.find { it.id == product.id }
        callback(item?.toCartItem())
    }

    override fun updateOrInsertItem(
        product: ProductUiModel,
        callback: () -> Unit,
    ) {
        val index = items.indexOfFirst { it.id == product.id }
        if (index == -1) {
            items.add(product)
        } else {
            items[index] = product
        }
        callback()
    }
}
