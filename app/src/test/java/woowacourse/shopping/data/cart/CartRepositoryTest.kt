package woowacourse.shopping.data.cart

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.PRODUCT_1
import woowacourse.shopping.PRODUCT_2

class CartRepositoryTest {
    private lateinit var cartRepository: CartRepository

    @BeforeEach
    fun setUp() {
        cartRepository = CartRepositoryImpl
    }

    @Test
    fun `선택한 상품을 저장할 수 있다`() {
        // given
        cartRepository.products.clear()
        cartRepository.add(PRODUCT_1)
        cartRepository.add(PRODUCT_2)

        // when
        val result = cartRepository.products

        // then
        result shouldBe listOf(PRODUCT_1, PRODUCT_2)
    }

    @Test
    fun `선택한 상품을 삭제할 수 있다`() {
        // given
        cartRepository.products.clear()
        cartRepository.add(PRODUCT_1)
        cartRepository.add(PRODUCT_2)
        cartRepository.remove(PRODUCT_1)

        // when
        val result = cartRepository.products

        // then
        result shouldBe listOf(PRODUCT_2)
    }
}
