package woowacourse.shopping.ui.products

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.data.GALAXY_BOOK
import woowacourse.shopping.model.data.GRAM
import woowacourse.shopping.model.data.IPHONE
import woowacourse.shopping.model.data.MAC_BOOK
import woowacourse.shopping.model.data.ProductsImpl

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductContentsViewModelTest {
    private lateinit var viewModel: ProductContentsViewModel

    @BeforeEach
    fun setUp() {
        ProductsImpl.deleteAll()
        repeat(100) {
            ProductsImpl.save(MAC_BOOK.copy(name = "맥북$it"))
            ProductsImpl.save(IPHONE.copy(name = "아이폰$it"))
            ProductsImpl.save(GALAXY_BOOK.copy(name = "갤럭시북$it"))
            ProductsImpl.save(GRAM.copy(name = "그램$it"))
        }
        viewModel = ProductContentsViewModel(ProductsImpl)
    }

    @Test
    fun `상품은 한 화면에 20개까지만 보여져야 한다`() {
        // when
        viewModel.loadProducts()
        // then
        assertThat(viewModel.products.getOrAwaitValue().size).isEqualTo(20)
    }

    @Test
    fun `첫번째 상품은 맥북0이어야 한다`() {
        // when
        viewModel.loadProducts()
        // then
        assertThat(viewModel.products.getOrAwaitValue().find { it.id == 0L }?.name)
            .isEqualTo("맥북0")
    }

    @Test
    fun `두번째 상품은 아이폰0이어야 한다`() {
        // when
        viewModel.loadProducts()
        // then
        assertThat(viewModel.products.getOrAwaitValue().find { it.id == 1L }?.name)
            .isEqualTo("아이폰0")
    }
}
