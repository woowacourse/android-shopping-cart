package woowacourse.shopping.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_1

class CatalogProductTest {
    @Test
    fun `수량을 증가시키면 해당 수량만큼 증가된 객체를 반환한다`() {
        // given
        val original = DUMMY_CATALOG_PRODUCT_1

        // when
        val increased = original.increaseQuantity()

        // then
        assertThat(increased.quantity).isEqualTo(6)
        assertThat(increased.product).isEqualTo(original.product)
    }

    @Test
    fun `수량을 감소시키면 해당 수량만큼 감소된 객체를 반환한다`() {
        // given
        val original = DUMMY_CATALOG_PRODUCT_1

        // when
        val decreased = original.decreaseQuantity()

        // then
        assertThat(decreased.quantity).isEqualTo(4)
        assertThat(decreased.product).isEqualTo(original.product)
    }

    @Test
    fun `수량을 감소시킬 때 0 미만으로 떨어지지 않도록 한다`() {
        // given
        val original = DUMMY_CATALOG_PRODUCT_1

        // when
        val decreased = original.decreaseQuantity(10)

        // then
        assertThat(decreased.quantity).isEqualTo(0)
    }

    @Test
    fun `총 가격은 단가와 수량의 곱으로 계산된다`() {
        // given
        val catalogProduct = DUMMY_CATALOG_PRODUCT_1

        // when
        val total = catalogProduct.totalPrice

        // then
        assertThat(total).isEqualTo(catalogProduct.product.price * catalogProduct.quantity)
    }
}
