package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

object DummyShoppingItems : ShoppingItemsRepository {
    val items =
        listOf(
            Product.of(
                name = "[든든] 동원 스위트콘",
                price = 99800L,
                imageUrl = "ff",
            ),
            Product.of(
                name = "[든든] 동원 스위트콘",
                price = 99800L,
                imageUrl = "ff",
            ),
        )

    override fun findProductItem(id: Long): Product? {
        return items.firstOrNull { it.id == id }
    }
}
