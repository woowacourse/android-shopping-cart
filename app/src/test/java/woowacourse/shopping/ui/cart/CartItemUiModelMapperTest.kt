@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.ui.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.repository.ProductRepositoryFixture

class CartItemUiModelMapperTest {
    private val product1 = ProductRepositoryFixture.products[0]
    private val product2 = ProductRepositoryFixture.products[1]

    @Test
    fun `장바구니 상품과 상품 맵을 UI 모델 목록으로 변환한다`() {
        val cartItems =
            listOf(
                CartItem(productId = product1.id, quantity = 2),
                CartItem(productId = product2.id, quantity = 1),
            )

        val actual =
            CartItemUiModelMapper.toUiModels(
                cartItems = cartItems,
                productsById =
                    mapOf(
                        product1.id to product1,
                        product2.id to product2,
                    ),
            )

        assertEquals(
            listOf(
                CartItemUiModel(
                    productId = product1.id,
                    name = product1.name,
                    imageUrl = product1.imageUrl,
                    price = product1.price.value,
                    quantity = 2,
                ),
                CartItemUiModel(
                    productId = product2.id,
                    name = product2.name,
                    imageUrl = product2.imageUrl,
                    price = product2.price.value,
                    quantity = 1,
                ),
            ),
            actual,
        )
    }

    @Test
    fun `상품 정보가 없는 장바구니 항목은 결과에서 제외한다`() {
        val cartItems =
            listOf(
                CartItem(productId = product1.id, quantity = 2),
                CartItem(productId = product2.id, quantity = 1),
            )

        val actual =
            CartItemUiModelMapper.toUiModels(
                cartItems = cartItems,
                productsById = mapOf(product1.id to product1),
            )

        assertEquals(
            listOf(
                CartItemUiModel(
                    productId = product1.id,
                    name = product1.name,
                    imageUrl = product1.imageUrl,
                    price = product1.price.value,
                    quantity = 2,
                ),
            ),
            actual,
        )
    }
}
