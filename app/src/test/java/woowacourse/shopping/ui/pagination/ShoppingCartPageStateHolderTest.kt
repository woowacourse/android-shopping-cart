package woowacourse.shopping.ui.pagination

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ShoppingCartItem

class ShoppingCartPageStateHolderTest {
    private val product = Product(1, "동원 스위트콘", Price(99_800), "")

    @Test
    fun `상품이 6개있다면 다음 페이지로 이동 가능하다`() {
        // given
        val shoppingCartPageStateHolder =
            ShoppingCartPageStateHolder(
                List(6) { index ->
                    ShoppingCartItem(index.toLong(), product)
                },
            )
        shoppingCartPageStateHolder.canMoveToNextPage() shouldBe true
        shoppingCartPageStateHolder.canMoveToPreviousPage() shouldBe false

        // when
        shoppingCartPageStateHolder.nextPage()

        // then
        shoppingCartPageStateHolder.getItems().size shouldBe 1
        shoppingCartPageStateHolder.canMoveToNextPage() shouldBe false
        shoppingCartPageStateHolder.canMoveToPreviousPage() shouldBe true
    }

    @Test
    fun `상품이 6개인 페이지에서 마지막 페이지에서 돌아가면 5개의 상품이 존재한다`() {
        // given
        val shoppingCartPageStateHolder =
            ShoppingCartPageStateHolder(
                List(6) { index ->
                    ShoppingCartItem(index.toLong(), product)
                },
            )
        while (shoppingCartPageStateHolder.canMoveToNextPage()) {
            shoppingCartPageStateHolder.nextPage()
        }

        // when
        shoppingCartPageStateHolder.beforePage()

        // then
        shoppingCartPageStateHolder.getItems().size shouldBe 5
    }
}
