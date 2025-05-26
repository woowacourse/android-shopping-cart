package woowacourse.shopping.data

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository

class FakeCartRepository : CartRepository {
    private val carts: MutableList<Cart> = mutableListOf(
        Cart(Product(1L, "맥북", Price(1000), ""), 0),
        Cart(Product(2L, "아이폰", Price(2000), ""), 0),
        Cart(Product(3L, "에어팟", Price(3000), ""), 0),
        Cart(Product(4L, "매직키보드", Price(4000), ""), 0),
        Cart(Product(5L, "에어팟맥스", Price(5000), ""), 0),
        Cart(Product(6L, "에어팟깁스", Price(6000), ""), 0),
    )

    override fun insert(item: Cart, onResult: () -> Unit) {
        carts.add(item)
        onResult()
    }

    override fun getById(id: Long, onResult: (Cart?) -> Unit) {
        val result = carts.find { it.product.id == id } // CartEntity -> Cart
        onResult(result)
    }

    override fun getPagedShopItems(offset: Int, limit: Int, onResult: (List<Cart>) -> Unit) {
        val result = carts.drop(offset).take(limit)
        onResult(result)
    }

    override fun getAll(onResult: (List<Cart>) -> Unit) {
        val result = carts
        onResult(result)
    }

    override fun totalSize(onResult: (Int) -> Unit) {
        onResult(carts.size)
    }

    override fun update(item: Cart, onResult: () -> Unit) {
        val index = carts.indexOfFirst { it.product.id == item.product.id }
        if (index != -1) {
            carts[index] = item
        }
        onResult()
    }

    override fun deleteById(id: Long, onResult: (Unit) -> Unit) {
        carts.removeIf { it.product.id == id }
        onResult(Unit)
    }

    override fun getPaged(offset: Int, limit: Int, onResult: (List<Cart>) -> Unit) {
        val result = carts.drop(offset).take(limit)
        onResult(result)
    }

    override fun hasOnlyPage(limit: Int, onResult: (Boolean) -> Unit) {
        onResult(carts.size <= limit)
    }

    override fun hasNextPage(nextOffset: Int, limit: Int, onResult: (Boolean) -> Unit) {
        onResult(nextOffset < carts.size)
    }
}
