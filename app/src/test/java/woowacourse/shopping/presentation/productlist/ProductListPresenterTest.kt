package woowacourse.shopping.presentation.productlist

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.UnCheckableCartProductModel

class ProductListPresenterTest {

    private lateinit var presenter: ProductListContract.Presenter
    private lateinit var view: ProductListContract.View
    private lateinit var recentProductIdRepository: RecentProductIdRepository
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        recentProductIdRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        presenter =
            ProductListPresenter(view, cartRepository, recentProductIdRepository)
    }

    @Test
    fun `상품 Id 1을 저장하면 recentProductIdRepository 에 1이 저장된다`() {
        // given
        val productIdSlot = slot<Long>()
        every { recentProductIdRepository.addRecentProductId(capture(productIdSlot)) } just runs

        // when
        presenter.saveRecentProductId(1)

        // then
        val actual = productIdSlot.captured
        assertThat(actual).isEqualTo(1)
    }

    @Test
    fun `전체 상품을 불러온다`() {
        // given 상품 목록 저장소는 10 개의 상품을 return 한다
        val productsSlot = slot<List<CartProductModel>>()
        every { view.setProductModels(capture(productsSlot)) } just runs
        every { cartRepository.getProductsByRange(any(), any()) } returns (1L..10L).map {
            UnCheckableCartProductModel(
                it,
                ProductModel(it, "test.com", "햄버거", 10000),
                1,
            )
        }

        // when 상품 목록을 load 한다
        presenter.loadProducts()

        // then view 의 setProductsModels 인자로 ProductModel 10개를 전달한다.
        val actual = productsSlot.captured
        val expected = (1L..10L).map {
            UnCheckableCartProductModel(
                it,
                ProductModel(it, "test.com", "햄버거", 10000),
                1,
            )
        }
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `최근 본 상품을 불러온다`() {
        // given 최근 본 상품 저장소 는 10 개의 상품을 return 한다
        val recentProductsSlot = slot<List<ProductModel>>()
        every { view.setRecentProductModels(capture(recentProductsSlot)) } just runs
        every { recentProductIdRepository.getRecentProductIds(10) } returns (1L..10L).toList()
        every { cartRepository.findProductById(any()) } returns
            Product(1L, "test.com", "햄버거", Price(10000))

        // when products 를 load 한다
        presenter.loadRecentProducts()

        // then view 로 ProductModel 10개를 전달한다.
        val actual = recentProductsSlot.captured
        val expected = (1..10).map {
            ProductModel(1L, "test.com", "햄버거", 10000)
        }
        assertThat(actual).isEqualTo(expected)
    }
}
