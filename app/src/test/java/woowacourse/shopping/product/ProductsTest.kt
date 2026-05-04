package woowacourse.shopping.product

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName
import woowacourse.shopping.domain.product.Products

class ProductsTest {
    private val product1 =
        Product(
            id = "1",
            name = ProductName("우아한두유"),
            price = Price(3000),
            imageUrl = ImageUrl("https://velog.io"),
        )

    private val product2 =
        Product(
            id = "2",
            name = ProductName("우아한물"),
            price = Price(1000),
            imageUrl = ImageUrl("https://naver.com"),
        )

    private val product3 =
        Product(
            id = "3",
            name = ProductName("우아한우유"),
            price = Price(2000),
            imageUrl = ImageUrl("https://google.com"),
        )

    private val product4 =
        Product(
            id = "4",
            name = ProductName("우아한스무디"),
            price = Price(1000),
            imageUrl = ImageUrl("https://daum.net"),
        )

    private val product5 =
        Product(
            id = "5",
            name = ProductName("우아한커피"),
            price = Price(4000),
            imageUrl = ImageUrl("https://kakao.com"),
        )

    private val productsValue = listOf(product1, product2, product3, product4, product5)

    @Test
    fun `getPage는 첫 페이지를 pageSize만큼 잘라서 반환한다`() {
        val products = Products(productsValue)

        val page = products.getPage(page = 0, pageSize = 2)

        assertEquals(listOf(product1, product2), page)
    }

    @Test
    fun `getPage는 중간 페이지를 pageSize만큼 잘라서 반환한다`() {
        val products = Products(productsValue)

        val page = products.getPage(page = 1, pageSize = 2)

        assertEquals(listOf(product3, product4), page)
    }

    @Test
    fun `getPage는 마지막 페이지가 pageSize보다 적게 차있어도 정상 반환한다`() {
        val products = Products(productsValue)

        val page = products.getPage(page = 2, pageSize = 2)

        assertEquals(listOf(product5), page)
    }

    @Test
    fun `getPage는 정확히 pageSize로 떨어질 때 다음 페이지에서 빈 리스트를 반환한다`() {
        val products = Products(productsValue)

        val nextPage = products.getPage(page = 5, pageSize = 1)

        assertTrue(nextPage.isEmpty())
    }

    @Test
    fun `getPage는 빈 Products에서 호출하면 빈 리스트를 반환한다`() {
        val products = Products(emptyList())

        val page = products.getPage(page = 0, pageSize = 10)

        assertTrue(page.isEmpty())
    }

    @Test
    fun `getPage는 범위를 넘는 page를 요청하면 빈 리스트를 반환한다`() {
        val products = Products(productsValue)

        val page = products.getPage(page = 99, pageSize = 2)

        assertTrue(page.isEmpty())
    }

    @Test
    fun `getPage의 page가 음수면 예외가 발생한다`() {
        val products = Products(productsValue)

        assertThrows(IllegalArgumentException::class.java) {
            products.getPage(page = -1, pageSize = 2)
        }
    }

    @Test
    fun `getPage의 pageSize가 0이면 예외가 발생한다`() {
        val products = Products(productsValue)

        assertThrows(IllegalArgumentException::class.java) {
            products.getPage(page = 0, pageSize = 0)
        }
    }

    @Test
    fun `getPage의 pageSize가 음수면 예외가 발생한다`() {
        val products = Products(productsValue)

        assertThrows(IllegalArgumentException::class.java) {
            products.getPage(page = 0, pageSize = -1)
        }
    }
}
