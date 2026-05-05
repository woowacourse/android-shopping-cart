package woowacourse.shopping.data

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.Money
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductName

class InMemoryCartRepositoryTest {
    private val repository = InMemoryCartRepository()

    @Test
    fun `등록되지 않은 상품을 등록하면 장바구니에 추가된다`() =
        runTest {
            val uniqueProduct =
                Product(
                    name = ProductName("상품"),
                    price = Money(2000),
                    imageUrl = "ds",
                )
            assertThat(repository.getTotalCartSize()).isEqualTo(0)
            repository.addItem(product = uniqueProduct)
            assertThat(repository.getTotalCartSize()).isEqualTo(1)
        }

    @Test
    fun `이미 등록된 상품을 다시 등록하면 장바구니에 추가되지 않는다`() =
        runTest {
            val uniqueProduct =
                Product(
                    name = ProductName("상품"),
                    price = Money(2000),
                    imageUrl = "ds",
                )
            assertThat(repository.getTotalCartSize()).isEqualTo(0)
            repository.addItem(product = uniqueProduct)
            assertThat(repository.getTotalCartSize()).isEqualTo(1)
            repository.addItem(product = uniqueProduct)
            assertThat(repository.getTotalCartSize()).isEqualTo(1)
        }

    @Test
    fun `장바구니에 등록된 상품을 삭제하면 장바구니 목록에서 삭제된다`() =
        runTest {
            val uniqueProduct =
                Product(
                    name = ProductName("상품"),
                    price = Money(2000),
                    imageUrl = "ds",
                )
            repository.addItem(product = uniqueProduct)
            assertThat(repository.getTotalCartSize()).isEqualTo(1)
            repository.deleteItem(uniqueProduct.id)
            assertThat(repository.getTotalCartSize()).isEqualTo(0)
        }

    @Test
    fun `장바구니에 등록되지 상품을 삭제하면 장바구니 목록은 그대로 유지된다`() =
        runTest {
            val uniqueProduct =
                Product(
                    name = ProductName("상품"),
                    price = Money(2000),
                    imageUrl = "ds",
                )
            repository.addItem(product = uniqueProduct)
            assertThat(repository.getTotalCartSize()).isEqualTo(1)
            repository.deleteItem("존재하지 않는 아이디")
            assertThat(repository.getTotalCartSize()).isEqualTo(1)
        }
}
