@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.ui.shopping

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.shopping.repository.ProductRepositoryFixture

class ShoppingProductUiStateMapperTest {
    private val product1 = ProductRepositoryFixture.products[0]
    private val product2 = ProductRepositoryFixture.products[1]

    @Test
    fun `상품 목록과 수량 맵을 화면 상태 목록으로 변환한다`() {
        val actual =
            ShoppingProductUiStateMapper.toUiStates(
                products = listOf(product1, product2),
                quantityByProductId =
                    mapOf(
                        product1.id to 2,
                        product2.id to 0,
                    ),
            )

        assertEquals(
            listOf(
                ShoppingProductUiState(product = product1, quantity = 2),
                ShoppingProductUiState(product = product2, quantity = 0),
            ),
            actual,
        )
    }

    @Test
    fun `수량 정보가 없으면 기본값 0으로 변환한다`() {
        val actual =
            ShoppingProductUiStateMapper.toUiStates(
                products = listOf(product1),
                quantityByProductId = emptyMap(),
            )

        assertEquals(
            listOf(
                ShoppingProductUiState(product = product1, quantity = 0),
            ),
            actual,
        )
    }
}
