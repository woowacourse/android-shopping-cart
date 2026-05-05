package woowacourse.shopping.ui.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.ui.cart.CartPageLoader
import woowacourse.shopping.ui.model.CartItemUiModel
import woowacourse.shopping.ui.model.ProductUiModel

class CartPagerLoaderTest {
    private val pager = CartPageLoader(pageSize = 5)

    @Test
    fun `페이지 사이즈에 맞춰 아이템을 반환한다`() {
        val result = pager.getCartPage(
            page = 0,
            items = createCartItems(10)
        )

        assertThat(result.items.map { it.product.id }).containsExactly("1", "2", "3", "4", "5")
        assertThat(result.page).isEqualTo(0)
        assertThat(result.isCanMoveNext).isTrue()
    }

    @Test
    fun `남은 아이템이 한 페이지의 적정 아이템 개수보다 적을 경우, 남은 아이템만 반환한다`() {
        val result = pager.getCartPage(
            page = 2,
            items = createCartItems(14)
        )

        assertThat(result.items.map { it.product.id }).containsExactly("11", "12", "13", "14")
        assertThat(result.page).isEqualTo(2)
        assertThat(result.isCanMoveNext).isFalse()
    }

    @Test
    fun `마지막 페이지에서는 다음 페이지 이동이 불가능하다`() {
        val result = pager.getCartPage(
            page = 2,
            items = createCartItems(14)
        )

        assertThat(result.isCanMoveNext).isFalse()
    }

    private fun createCartItems(size: Int): List<CartItemUiModel> =
        (1..size).map {
            CartItemUiModel(
                product = ProductUiModel(
                    id = it.toString()
                ),
                quantity = 1,
                totalPrice = 1000
            )
        }
}