package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.domain.PurchaseProducts

class CartTest {
    @Test
    fun `PurchaseProduct를 추가할 수 있다`() {
        val cart = Cart()

        val newPurchaseProduct = PurchaseProduct(
            Product(imageUri = "image", name = "TwoHander", price = 10000)
        )

        val newCart = cart.add(newPurchaseProduct)

        assertTrue(newCart.purchaseProducts.purchaseProducts.contains(newPurchaseProduct))
    }

    @Test
    fun `ID를 통해 특정 PurchaseProduct의 count를 변경할 수 있다`() {
        val newPurchaseProduct = PurchaseProduct(
            Product(imageUri = "image", name = "TwoHander", price = 10000)
        )

        val targetId = newPurchaseProduct.uuid()

        val cart = Cart(
            purchaseProducts = PurchaseProducts(
                purchaseProducts = listOf(newPurchaseProduct)
            )
        )

        val updatedCart = cart.updateCountWithId(targetId, 1)

        assert(
            updatedCart.findById(targetId)?.count == 2
        )
    }

    @Test
    fun `ID를 통해 특정 PurchaseProduct를 제거할 수 있다`() {
        val newPurchaseProduct = PurchaseProduct(
            Product(imageUri = "image", name = "TwoHander", price = 10000)
        )

        val targetId = newPurchaseProduct.uuid()

        val cart = Cart(
            purchaseProducts = PurchaseProducts(
                purchaseProducts = listOf(newPurchaseProduct)
            )
        )

        val updatedCart = cart.removeWithId(targetId)

        assert(updatedCart.purchaseProducts.purchaseProducts.contains(newPurchaseProduct).not())
    }

    @Test
    fun `ID를 통해 특정 PurchaseProduct의 총 가격을 알 수 있다`() {
        val newPurchaseProduct = PurchaseProduct(
            Product(imageUri = "image", name = "TwoHander", price = 10000),
            count = 10
        )

        val targetId = newPurchaseProduct.uuid()

        val cart = Cart(
            purchaseProducts = PurchaseProducts(
                purchaseProducts = listOf(newPurchaseProduct)
            )
        )

        assert(cart.totalPriceOfSpecificPurchaseProduct(targetId) == 100000)
    }

    @Test
    fun `Cart에 담긴 PurchaseProduct들의 count 총합을 알 수 있다`() {
        val newPurchaseProduct = PurchaseProduct(
            Product(imageUri = "image", name = "TwoHander", price = 10000),
            count = 10
        )

        val cart = Cart(
            purchaseProducts = PurchaseProducts(
                purchaseProducts = listOf(newPurchaseProduct, newPurchaseProduct, newPurchaseProduct)
            )
        )

        assert(cart.totalCountOfPurchaseProducts() == 30)
    }

    @Test
    fun `동일한 ID를 갖는 PurchaseProduct가 추가되면 기존에 담겨있던 객체의 count가 증가한다`() {
        val purchaseProduct = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품",
                price = 1000
            ),
        )

        val targetId = purchaseProduct.uuid()

        val cart = Cart(
            purchaseProducts = PurchaseProducts(
                purchaseProducts = listOf(purchaseProduct)
            )
        )

        val updatedCart = cart.add(purchaseProduct)

        assert(updatedCart.findById(targetId)?.count == 2)
    }

    @Test
    fun `특정 ID를 갖는 PurchaseProduct가 담겨있는지 알 수 있다`() {
        val purchaseProduct = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품",
                price = 1000
            ),
        )

        val targetId = purchaseProduct.uuid()

        val cart = Cart(
            purchaseProducts = PurchaseProducts(
                purchaseProducts = listOf(purchaseProduct)
            )
        )

        assert(cart.isContain(targetId))
    }

    @Test
    fun `특정 ID를 갖는 PurchaseProduct의 count를 알 수 있다`() {
        val purchaseProduct = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품",
                price = 1000
            ),
        )

        val targetId = purchaseProduct.uuid()

        val cart = Cart(
            purchaseProducts = PurchaseProducts(
                purchaseProducts = listOf(purchaseProduct)
            )
        )

        assert(cart.totalCountOfSpecificPurchaseProduct(targetId) == 1)
    }
}
