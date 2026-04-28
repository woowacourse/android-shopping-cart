@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.model

import org.junit.jupiter.api.Test

class ProductsTest {
    val product1 = Product(
        name = "새우깡",
        price = Money(3100),
        imageResource = 1
    )

    val product2 = Product(
        name = "아이셔",
        price = Money(1300),
        imageResource = 2
    )

    @Test
    fun `유효한 상품 데이터 리스트가 주어지면 Products 객체가 정상적으로 생성된다`() {
        Products(listOf(product1, product2))
    }

    @Test
    fun `생성된 Products 객체에서 내부의 전체 상품 데이터를 누락이나 변형 없이 정상적으로 반환한다`() {
        val expected = listOf(product1, product2)
        val actual = Products(listOf(product1, product2)).toList()
        assert(expected == actual)
    }
}
