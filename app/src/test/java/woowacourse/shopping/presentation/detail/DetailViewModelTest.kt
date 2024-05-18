package woowacourse.shopping.presentation.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.presentation.dummy.DummyProducts

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var detailViewModel: DetailViewModel

    @BeforeEach
    fun setUp() {
        detailViewModel =
            DetailViewModel(FakeProductRepository(DummyProducts().products), FakeCartRepository(mutableListOf()))
    }

    @Test
    fun `상품 아이디로 상품 상세 정보를 불러온다`() {
        detailViewModel.loadProductInformation(1)

        val productInformation = detailViewModel.productInformation.value

        assertThat(productInformation).isEqualTo(
            Product(1, "Product 1", "", 1000),
        )
    }
}
