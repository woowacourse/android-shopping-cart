package woowacourse.shopping.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartProductTest {
    @Test
    internal fun `선택된 제품 개수를 증가시킨다`() {
        // given
        val product = Product(1, "상품", Price(1000), "")
        val cartProduct = CartProduct(product = product, isChecked = false)
        val expected =
            CartProduct(product = product, selectedCount = ProductCount(2), isChecked = false)

        // when
        val actual = cartProduct.plusCount(2)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `선택된 제품 개수를 감소시킨다`() {
        // given
        val product = Product(1, "상품", Price(1000), "")
        val cartProduct = CartProduct(
            product = product,
            selectedCount = ProductCount(3),
            isChecked = false
        )
        val expected = CartProduct(
            product = product,
            selectedCount = ProductCount(1),
            isChecked = false
        )

        // when
        val actual = cartProduct.minusCount(2)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `제품을 선택한다`() {
        // given
        val product = Product(1, "상품", Price(1000), "")
        val cartProduct = CartProduct(
            product = product,
            selectedCount = ProductCount(2),
            isChecked = false
        )
        val expected = CartProduct(
            product = product,
            selectedCount = ProductCount(2),
            isChecked = true
        )

        // when
        val actual = cartProduct.select()

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `제품을 선택을 해제한다`() {
        // given
        val product = Product(1, "상품", Price(1000), "")
        val cartProduct = CartProduct(
            product = product,
            selectedCount = ProductCount(2),
            isChecked = true
        )
        val expected = CartProduct(
            product = product,
            selectedCount = ProductCount(2),
            isChecked = false
        )

        // when
        val actual = cartProduct.unselect()

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `체크된 제품의 개수와 가격을 곱하여 총 합을 반환한다`() {
        // given
        val product = Product(1, "상품", Price(1000), "")
        val cartProduct = CartProduct(
            product = product,
            selectedCount = ProductCount(2),
            isChecked = true
        )
        val expected = 2000

        // when
        val actual = cartProduct.getTotalPrice(onlyChecked = true)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `체크되지 않은 제품은 총 가격에 포함하지 않는다`() {
        // given
        val product = Product(1, "상품", Price(1000), "")
        val cartProduct = CartProduct(
            product = product,
            selectedCount = ProductCount(2),
            isChecked = false
        )
        val expected = 0

        // when
        val actual = cartProduct.getTotalPrice(onlyChecked = true)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
