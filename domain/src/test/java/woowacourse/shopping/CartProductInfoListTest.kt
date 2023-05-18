package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartProductInfoListTest {
    private val testProduct = Product(1, "", "", Price(1000))

    @Test
    fun `Count가 1,2,3 인 CartProductInfo를 들고있다면, count 는 6이다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList()
        // when
        val actual = cartProductInfoList.count
        // then
        val expected = 6
        assertThat(actual).isEqualTo(expected)
    }

    private fun makeTestCartProductInfoList(): CartProductInfoList = CartProductInfoList(
        listOf(
            CartProductInfo(testProduct, 1),
            CartProductInfo(testProduct, 2),
            CartProductInfo(testProduct, 3),
        ),
    )
}
