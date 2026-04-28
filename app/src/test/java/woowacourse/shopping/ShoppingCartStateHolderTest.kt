package woowacourse.shopping

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ShoppingCartStateHolderTest {
    private val product = Product(1, "동원 스위트콘", 99_800, "")

    @Test
    fun `장바구니에 상품을 추가할 수 있다`() {
        val shoppingCartStateHolder = ShoppingCartStateHolder()
        shoppingCartStateHolder.add(product)
        shoppingCartStateHolder.getAllProducts()[0].product shouldBe product
    }

    @Test
    fun `장바구니에 존재하는 상품을 표시한다 `() {
        val shoppingCartStateHolder = ShoppingCartStateHolder(product)
        shoppingCartStateHolder.getAllProducts()[0].product shouldBe product
    }
}
