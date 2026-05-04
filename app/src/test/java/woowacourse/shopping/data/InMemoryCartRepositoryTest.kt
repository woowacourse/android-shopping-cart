package woowacourse.shopping.data

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Money
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductName

class InMemoryCartRepositoryTest {
    private val repository = InMemoryCartRepository()

    @Test
    fun `등록되지 않은 상품을 등록하면 NewAdded를 반환한다`() =
        runTest {
            val uniqueProduct =
                Product(
                    name = ProductName("상품"),
                    price = Money(2000),
                    imageUrl = "ds",
                )

            val result = repository.addItem(product = uniqueProduct)

            assertThat(result).isInstanceOf(AddItemResult.NewAdded::class.java)
        }

    @Test
    fun `이미 등록된 상품을 다시 등록하면 DuplicateItem을 반환한다`() =
        runTest {
            val product =
                Product(
                    name = ProductName("상품2"),
                    price = Money(2000),
                    imageUrl = "ds",
                )
            repository.addItem(product = product)

            val result = repository.addItem(product = product)

            assertThat(result).isEqualTo(AddItemResult.DuplicateItem)
        }
}
