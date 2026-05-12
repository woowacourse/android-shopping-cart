package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PurchaseProductsTest {
    @Test
    fun `PurchaseProduct를 추가할 수 있다`() {
        val purchaseProducts = PurchaseProducts()
        val newPurchaseProduct =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품",
                        price = 1000,
                    ),
            )
        val newPurchaseProducts = purchaseProducts.add(newPurchaseProduct)

        assertTrue(newPurchaseProducts.purchaseProducts.contains(newPurchaseProduct))
    }

    @Test
    fun `특정 PurchaseProduct의 count를 변경할 수 있다`() {
        val newPurchaseProduct =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품",
                        price = 1000,
                    ),
            )

        val productsId = newPurchaseProduct.id()

        val purchaseProducts =
            PurchaseProducts(
                purchaseProducts = listOf(newPurchaseProduct),
            )

        val updatedPurchaseProducts =
            purchaseProducts.updateCountWithUuid(
                id = productsId,
                updateAmount = 1,
            )

        assertEquals(2, updatedPurchaseProducts.purchaseProducts.find { it.isSameID(productsId) }?.count)
    }

    @Test
    fun `특정 PurchaseProduct를 제거할 수 있다`() {
        val purchaseProduct1 =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품1",
                        price = 1000,
                    ),
            )

        val purchaseProduct2 =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품2",
                        price = 2000,
                    ),
            )

        val product1Id = purchaseProduct1.id()

        val purchaseProducts =
            PurchaseProducts(
                purchaseProducts = listOf(purchaseProduct1, purchaseProduct2),
            )

        val updatedPurchaseProducts = purchaseProducts.removeProduct(product1Id)

        assertTrue(
            updatedPurchaseProducts.purchaseProducts.contains(purchaseProduct2) &&
                updatedPurchaseProducts.purchaseProducts.contains(purchaseProduct1).not(),
        )
    }

    @Test
    fun `특정 PurchaseProduct의 총 가격을 알 수 있다`() {
        val newPurchaseProduct =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품",
                        price = 1000,
                    ),
                count = 3,
            )

        val productsId = newPurchaseProduct.id()

        val purchaseProducts =
            PurchaseProducts(
                purchaseProducts = listOf(newPurchaseProduct),
            )

        assertEquals(3000, purchaseProducts.totalPriceOfSpecificPurchaseProduct(productsId))
    }

    @Test
    fun `PurchaseProduct의 count의 총합을 알 수 있다`() {
        val newPurchaseProduct1 =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품1",
                        price = 1000,
                    ),
                count = 3,
            )
        val newPurchaseProduct2 =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품2",
                        price = 2000,
                    ),
                count = 4,
            )
        val newPurchaseProduct3 =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품3",
                        price = 3000,
                    ),
                count = 6,
            )

        val purchaseProducts =
            PurchaseProducts(
                listOf(
                    newPurchaseProduct1,
                    newPurchaseProduct2,
                    newPurchaseProduct3,
                ),
            )

        assertEquals(13, purchaseProducts.totalCount())
    }

    @Test
    fun `동일한 ID를 갖는 PurchaseProduct가 추가되면 기존에 담겨있던 객체의 count가 증가한다`() {
        val newPurchaseProduct =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품",
                        price = 1000,
                    ),
            )

        val productId = newPurchaseProduct.id()

        val purchaseProducts =
            PurchaseProducts(
                purchaseProducts = listOf(newPurchaseProduct),
            )

        val updatedPurchaseProducts = purchaseProducts.add(newPurchaseProduct)

        assertEquals(2, updatedPurchaseProducts.findById(productId)?.count)
    }

    @Test
    fun `특정 ID를 갖는 PurchaseProduct가 담겨있는지 알 수 있다`() {
        val newPurchaseProduct =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품",
                        price = 1000,
                    ),
            )

        val productId = newPurchaseProduct.id()

        val purchaseProducts =
            PurchaseProducts(
                purchaseProducts = listOf(newPurchaseProduct),
            )

        assertTrue(purchaseProducts.isContain(productId))
    }

    @Test
    fun `특정 ID를 갖는 PurchaseProduct의 count를 알 수 있다`() {
        val newPurchaseProduct =
            PurchaseProduct(
                product =
                    Product(
                        imageUri = "uri",
                        name = "테스트 상품",
                        price = 1000,
                    ),
            )

        val productId = newPurchaseProduct.id()

        val purchaseProducts =
            PurchaseProducts(
                purchaseProducts = listOf(newPurchaseProduct),
            )

        assertEquals(1, purchaseProducts.totalCountOfSpecificPurchaseProduct(productId))
    }
}
