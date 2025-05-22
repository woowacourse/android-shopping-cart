package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ext.subList
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

class ShoppingCartRepositoryImpl : ShoppingCartRepository {
    override fun findAll(pageRequest: PageRequest): Page<ShoppingCartItemUiModel> {
        val items =
            DummyShoppingCart.items
                .distinct()
                .subList(pageRequest)

        return pageRequest.toPage(items, totalSize())
    }

    override fun findAll(): List<ShoppingCartItemUiModel> {
        return DummyShoppingCart.items
    }

    override fun totalSize(): Int = DummyShoppingCart.items.size

    override fun remove(item: ShoppingCartItemUiModel) {
        DummyShoppingCart.items.remove(item)
    }

    override fun save(item: ShoppingCartItemUiModel) {
        DummyShoppingCart.items.find { it.productUiModel.id == item.productUiModel.id }?.let {
            DummyShoppingCart.items.remove(it)
            DummyShoppingCart.items.add(0, it.copy(quantity = it.quantity + item.quantity))
        } ?: run {
            DummyShoppingCart.items.add(0, item)
        }
    }

    override fun update(item: ShoppingCartItemUiModel) {
        DummyShoppingCart.items.find { it.productUiModel.id == item.productUiModel.id }?.let {
            DummyShoppingCart.items.remove(it)
            if (item.quantity > 0) DummyShoppingCart.items.add(item)
        } ?: run {
            if (item.quantity > 0) DummyShoppingCart.items.add(0, item)
        }
    }

    override fun totalQuantity(): Int {
        return DummyShoppingCart.items.sumOf { it.quantity }
    }
}
