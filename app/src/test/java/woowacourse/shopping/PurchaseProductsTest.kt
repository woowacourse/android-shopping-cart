package woowacourse.shopping

import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.domain.PurchaseProducts
import woowacourse.shopping.domain.util.CountUpdateType

class PurchaseProductsTest {
    @Test
    fun `PurchaseProduct를 추가할 수 있다` () {
        val purchaseProducts = PurchaseProducts()
        val newPurchaseProduct = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품",
                price = 1000
            ),
        )
        val newPurchaseProducts = purchaseProducts.add(newPurchaseProduct)

        assert(newPurchaseProducts.purchaseProducts.contains(newPurchaseProduct))
    }

    @Test
    fun `특정 PurchaseProduct의 count를 변경할 수 있다`() {
        val newPurchaseProduct = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품",
                price = 1000
            ),
        )

        val productsId = newPurchaseProduct.uuid

        val purchaseProducts = PurchaseProducts(
            purchaseProducts = listOf(newPurchaseProduct)
        )

        val updatedPurchaseProducts = purchaseProducts.updateCountWithUuid(
            uuid = productsId,
            updateType = CountUpdateType.INCREASE
        )

        assert(updatedPurchaseProducts.purchaseProducts.find { it.isSameUUID(productsId) }?.count == 2)
    }

    @Test
    fun `특정 PurchaseProduct를 제거할 수 있다`() {
        val purchaseProduct1 = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품1",
                price = 1000
            ),
        )

        val purchaseProduct2 = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품2",
                price = 2000
            ),
        )

        val product1Id = purchaseProduct1.uuid

        val purchaseProducts = PurchaseProducts(
            purchaseProducts = listOf(purchaseProduct1, purchaseProduct2)
        )

        val updatedPurchaseProducts = purchaseProducts.removeProduct(product1Id)

        assert(
            updatedPurchaseProducts.purchaseProducts.contains(purchaseProduct2) &&
            updatedPurchaseProducts.purchaseProducts.contains(purchaseProduct1).not()
        )
    }

    @Test
    fun `특정 PurchaseProduct의 총 가격을 알 수 있다`() {
        val newPurchaseProduct = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품",
                price = 1000
            ),
            count = 3
        )

        val productsId = newPurchaseProduct.uuid

        val purchaseProducts = PurchaseProducts(
            purchaseProducts = listOf(newPurchaseProduct)
        )

        assert(purchaseProducts.priceOfSpecificPurchaseProduct(productsId) == 3000)
    }

    @Test
    fun `PurchaseProduct의 count의 총합을 알 수 있다`() {
        val newPurchaseProduct1 = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품1",
                price = 1000
            ),
            count = 3
        )
        val newPurchaseProduct2 = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품2",
                price = 2000
            ),
            count = 4
        )
        val newPurchaseProduct3 = PurchaseProduct(
            product = Product(
                imageUri = "uri",
                name = "테스트 상품3",
                price = 3000
            ),
            count = 6
        )

        val purchaseProducts = PurchaseProducts(
            listOf(
                newPurchaseProduct1, newPurchaseProduct2, newPurchaseProduct3
            )
        )

        assert(purchaseProducts.totalCount() == 13)
    }
}