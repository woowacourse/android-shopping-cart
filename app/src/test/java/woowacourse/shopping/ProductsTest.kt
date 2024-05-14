package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.data.Products
import woowacourse.shopping.domain.Product

class ProductsTest {
    private lateinit var products: Products

    @BeforeEach
    fun setUp() {
        products = Products()
    }

    @Test
    fun `페이지당 상품의 수가 허용 범위 이내이다`() {
        // when
        val actualData = products.getProducts(0, 20).size

        // then
        assertTrue { actualData in 1..20 }
    }

    @Test
    fun `올바른 페이지 상품들을 가져온다`() {
        // when
        val actualData = products.getProducts(0, 5)

        // then
        assertThat(actualData).isEqualTo(
            listOf(
                Product(
                    1,
                    "사과",
                    "https://github.com/kmkim2689/woowacourse-practice2/assets/101035437/628e5826-37c5-4a6e-bd69-69d4fa088f8a",
                    1000,
                ),
                Product(
                    2,
                    "바나나",
                    "https://github.com/kmkim2689/woowacourse-practice2/assets/101035437/c95ca5cf-8302-4f66-bc86-05692f3d1a2e",
                    2000,
                ),
                Product(
                    3,
                    "딸기",
                    "https://github.com/kmkim2689/woowacourse-practice2/assets/101035437/628e5826-37c5-4a6e-bd69-69d4fa088f8a",
                    3000,
                ),
                Product(
                    4,
                    "수박",
                    "https://github.com/kmkim2689/woowacourse-practice2/assets/101035437/c95ca5cf-8302-4f66-bc86-05692f3d1a2e",
                    4000,
                ),
                Product(
                    5,
                    "포도",
                    "https://github.com/kmkim2689/woowacourse-practice2/assets/101035437/628e5826-37c5-4a6e-bd69-69d4fa088f8a",
                    5000,
                ),
            ),
        )
    }

    @Test
    fun `상품 아이디에 부합하는 상품을 가져올 수 있다`() {
        // when
        val actualData = products.getProductById(1)

        // then
        assertThat(actualData).isEqualTo(
            Product(
                1,
                "사과",
                "https://github.com/kmkim2689/woowacourse-practice2/assets/101035437/628e5826-37c5-4a6e-bd69-69d4fa088f8a",
                1000,
            ),
        )
    }

    @Test
    fun `유효하지 않은 상품 아이디로 상품 정보를 가져오면 예외를 발생시킨다`() {
        assertThrows<NoSuchElementException> { products.getProductById(0) }
    }
}
