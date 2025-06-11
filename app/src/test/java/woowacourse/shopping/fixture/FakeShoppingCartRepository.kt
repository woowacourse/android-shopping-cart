package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class FakeShoppingCartRepository : ShoppingCartRepository {
    private val items = mutableListOf(SUNDAE)

    override fun saveItem(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit,
    ) {
        val existing = items.indexOfFirst { it.goods.id == item.goods.id }
        if (existing != -1) {
            items[existing] = item
        } else {
            items.add(item)
        }
        onResult(Result.success(Unit))
    }

    override fun addOrIncreaseQuantity(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit,
    ) {
        val index = items.indexOfFirst { it.goods.id == item.goods.id }
        if (index != -1) {
            val existing = items[index]
            items[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            items.add(item)
        }
        onResult(Result.success(Unit))
    }

    override fun removeItem(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit,
    ) {
        items.removeIf { it.goods.id == item.goods.id }
        onResult(Result.success(Unit))
    }

    override fun getItem(
        id: Long,
        onResult: (Result<ShoppingCartItem?>) -> Unit,
    ) {
        val item = items.find { it.goods.id == id }
        onResult(Result.success(item))
    }

    override fun getAllItems(onResult: (Result<List<ShoppingCartItem>>) -> Unit) {
        onResult(Result.success(items.toList()))
    }

    override fun getPagedItems(
        page: Int,
        count: Int,
        onResult: (Result<List<ShoppingCartItem>>) -> Unit,
    ) {
        val list = List(5) { SUNDAE }
        onResult(Result.success(list))
    }

    override fun getTotalQuantity(onResult: (Result<Int>) -> Unit) {
        val total = items.sumOf { it.quantity }
        onResult(Result.success(total))
    }
}
