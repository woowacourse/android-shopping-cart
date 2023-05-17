package woowacourse.shopping.productDetail

import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailContract
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @Before
    fun setUp() {
        productRepository = mockk(relaxed = true)
        shoppingCartRepository = mockk(relaxed = true)
        presenter = ProductDetailPresenter(productRepository, shoppingCartRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun 선택한_제품의_아이디로_제품의_정보를_가져오는데_성공했다() {
        // given
        val product = Product(0, "", "test", 999)
        every { productRepository.getProduct(any()) } returns WoowaResult.SUCCESS(product)

        // when
        presenter.getProduct(0)

        // then
        verify { productRepository.getProduct(any()) }
    }
}
