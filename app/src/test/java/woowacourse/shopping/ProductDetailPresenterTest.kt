package woowacourse.shopping

import com.shopping.domain.CartProduct
import com.shopping.domain.Product
import com.shopping.domain.RecentProduct
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.datas.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter
import woowacourse.shopping.uimodel.ProductUIModel

class ProductDetailPresenterTest {

    private lateinit var view: ProductDetailContract.View
    private lateinit var product: Product
    private lateinit var recentRepository: RecentRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var presenter: ProductDetailContract.Presenter

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        product = ProductUIModel.dummy.toDomain()
        recentRepository = mockk()
        cartRepository = mockk()
        presenter =
            ProductDetailPresenter(view, product.toUIModel(), recentRepository, cartRepository)
    }

    @Test
    fun `최근 목록 저장소에 데이터를 추가한다`() {
        val currentTime = 1000L

        // given
        every { recentRepository.insert(RecentProduct(currentTime, product)) } just runs

        // when
        presenter.insertRecentRepository(currentTime)

        // then
        verify { recentRepository.insert(RecentProduct(currentTime, product)) }
    }

    @Test
    fun `가장 최근 본 상품을 가져온다`() {
        // given
        val dummyRecentProduct = RecentProduct(10000L, product)
        every { recentRepository.getLatestProduct() } returns dummyRecentProduct

        // when
        presenter.getMostRecentProduct()

        // then
        verify(exactly = 1) { view.showRecentProduct(any()) }
    }

    @Test
    fun `장바구니에 추가하는 버튼이 클릭 될 때 데이터를 레포지토리에 저장하고 장바구니 페이지를 보여준다`() {
        // given
        every { cartRepository.insert(CartProduct(true, 1, product)) } just runs
        every { view.showCartPage() } just runs

        // when
        presenter.addToCart()

        // then
        verify { cartRepository.insert(CartProduct(true, 1, product)) }
        verify { view.showCartPage() }
    }
}
