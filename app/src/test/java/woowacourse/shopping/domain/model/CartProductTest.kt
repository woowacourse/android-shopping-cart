package woowacourse.shopping.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.DUMMY_CART_PRODUCT_1

class CartProductTest {
    @Test
    fun `총 가격은 단가와 수량의 곱으로 계산된다`() {
        // given
        val cartProduct = DUMMY_CART_PRODUCT_1

        // when
        val total = cartProduct.totalPrice

        // then
        assertThat(total).isEqualTo(cartProduct.product.price * cartProduct.quantity)
    }
}
