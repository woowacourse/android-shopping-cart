package woowacourse.shopping

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.domain.util.CountUpdateType

class PurchaseProductTest {
    @Test
    fun `구매할 개수가 1 미만일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트",
                price = 1
                ),
            count = 0
            )
        }
    }

    @Test
    fun `구매할 상품의 개수를 변경할 수 있다`() {
        val purchaseProduct = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트",
                price = 1
            ),
        )

        val updatedPurchaseProduct = purchaseProduct.updateCount(CountUpdateType.INCREASE)

        assert(updatedPurchaseProduct.count == 2)
    }

    @Test
    fun `구매할 개수에 따른 총 금액을 계산할 수 있다`() {
        val purchaseProduct = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트",
                price = 1
            ),
            count = 2
        )

        val totalPrice = purchaseProduct.totalPrice()

        assert(totalPrice == 2)
    }
}
