package woowacourse.shopping.view.productdetail

import com.shopping.domain.Product
import com.shopping.domain.RecentProduct
import com.shopping.repository.ProductRepository
import com.shopping.repository.RecentProductsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.model.uimodel.mapper.toUIModel

class ProductDetailPresenterTest {
    private lateinit var view: ProductDetailContract.View
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductsRepository: RecentProductsRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        recentProductsRepository = mockk(relaxed = true)
        presenter = ProductDetailPresenter(view, recentProductsRepository, productRepository)
    }

    @Test
    fun `최근_본_상품을_확인_할_수_있다`() {
        // given
        val recentProduct = recentProduct(1)
        every { recentProductsRepository.getFirst() } returns recentProduct

        // when
        presenter.setRecentProductView(product(2).toUIModel())

        // then
        verify { view.showLatestProduct() }
    }

    @Test
    fun `최근_본_상품을_타고_들어온_상세_화면에서는_최근_본_상품이_보이지_않는다`() {
        // given
        val recentProduct = recentProduct(1)
        every { recentProductsRepository.getFirst() } returns recentProduct

        // when
        presenter.setRecentProductView(product(1).toUIModel())

        // then
        verify { view.hideLatestProduct() }
    }

    @Test
    fun `상세화면에_들어오면_최근_본_상품_목록에_해당_상품이_추가된다`() {
        // given
        val product = product(1)

        // when
        presenter.setRecentProductView(product.toUIModel())

        // then
        verify { recentProductsRepository.insert(RecentProduct(product)) }
    }

    private fun recentProduct(id: Int) =
        RecentProduct(Product(id, "", "", 1000))

    private fun product(id: Int) =
        Product(id, "", "", 1000)
}
