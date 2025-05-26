package woowacourse.shopping.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.DUMMY_CART_PRODUCTS_1
import woowacourse.shopping.model.DUMMY_CART_PRODUCT_2

class CartProductsTest {
    @Test
    fun `상품 수량을 수정하면 해당 상품을 반영한다`() {
        // given
        val original = DUMMY_CART_PRODUCTS_1
        val targetId = DUMMY_CART_PRODUCT_2.product.id
        val newQuantity = 100

        // when
        val updated = original.updateCartProductQuantity(targetId, newQuantity)

        // then
        val modified = updated.products.first { it.product.id == targetId }
        assertThat(modified.quantity).isEqualTo(newQuantity)

        val originalTarget = original.products.first { it.product.id == targetId }
        assertThat(originalTarget.quantity).isNotEqualTo(newQuantity)

        val otherOriginal = original.products.filter { it.product.id != targetId }
        val otherUpdated = updated.products.filter { it.product.id != targetId }
        assertThat(otherUpdated).containsExactlyElementsIn(otherOriginal)
    }
}
