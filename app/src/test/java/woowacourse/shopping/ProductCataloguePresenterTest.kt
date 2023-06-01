package woowacourse.shopping

import com.shopping.domain.CartProduct
import com.shopping.domain.CartRepository
import com.shopping.domain.ProductRepository
import com.shopping.domain.RecentRepository
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.productcatalogue.ProductCatalogueContract
import woowacourse.shopping.productcatalogue.ProductCataloguePresenter
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductCataloguePresenterTest {

    private lateinit var view: ProductCatalogueContract.View
    private lateinit var presenter: ProductCatalogueContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentRepository
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk()
        productRepository = mockk()
        recentProductRepository = mockk()
        cartRepository = mockk()
        presenter = ProductCataloguePresenter(
            view,
            productRepository,
            recentProductRepository,
            cartRepository
        )
    }

    @Test
    fun `최근 목록 상품 데이터를 가져와서 뷰에게 넘겨준다`() {
        // given
        val slot = slot<List<RecentProductUIModel>>()
        every {
            recentProductRepository.getAll()
        } returns listOf(RecentProductUIModel(0L, ProductUIModel.dummy).toDomain())

        justRun { view.setRecentProductList(capture(slot)) }

        // when
        presenter.fetchRecentProduct()

        // then
        verify { view.setRecentProductList(slot.captured) }
        assertEquals(listOf(RecentProductUIModel(0L, ProductUIModel.dummy)), slot.captured)
    }

    @Test
    fun `더보기가 클릭 되었을 때 데이터를 가져오고 뷰에게 데이터가 변화함을 알린다`() {
        // given
        val unitSize = 20
        val page = 1

        justRun { productRepository.getUnitData(20, 1, any(), any()) }
        justRun { view.attachNewProducts(any()) }
        justRun { view.setGridLayoutManager(any()) }

        // when
        presenter.fetchMoreProducts(unitSize, page)

        // then
        verify { productRepository.getUnitData(unitSize, page, any(), any()) }
        verify { view.setGridLayoutManager(any()) }
    }

    @Test
    fun `장바구니 아이콘 옆 개수를 최신화 한다`() {
        // given
        every { cartRepository.getSize() } returns 3
        justRun { view.setCartCountCircle(3) }
        // when
        presenter.fetchCartCount()

        // then
        verify(exactly = 1) { view.setCartCountCircle(3) }
    }

    @Test
    fun `장바구니 내 상품의 개수를 감소할때 감소된 개수가 0이라면 장바구니 레포지토리에서 삭제한다`() {
        // given
        val cartProduct = CartProductUIModel(
            isPicked = true,
            count = 1,
            product = ProductUIModel.dummy
        )
        val count = 1
        justRun { cartRepository.remove(cartProduct.product.id) }

        // when
        presenter.decreaseCartProductCount(cartProduct, count)

        // then
        verify { cartRepository.remove(cartProduct.product.id) }
        verify(inverse = true) { cartRepository.updateProductCount(any(), any()) }
    }

    @Test
    fun `장바구니 내 상품의 개수를 감소할 때 감소된 개수가 0이하가 아니라면 개수를 감소한다`() {
        // given
        val cartProduct = CartProductUIModel(
            isPicked = true,
            count = 2,
            product = ProductUIModel.dummy
        )
        val count = 2
        justRun { cartRepository.updateProductCount(cartProduct.product.id, 1) }

        // when
        presenter.decreaseCartProductCount(cartProduct, count)

        // then
        verify { cartRepository.updateProductCount(cartProduct.product.id, 1) }
        verify(inverse = true) { cartRepository.remove(cartProduct.product.id) }
    }

    @Test
    fun `장바구니 상품의 개수를 증가시킬 때 처음 증가 된거라면 장바구니 레포지토리에 상품을 추가시킨다`() {
        // given
        val cartProduct = CartProductUIModel(
            isPicked = true,
            count = 0,
            product = ProductUIModel.dummy
        )
        val count = 0
        justRun { cartRepository.insert(CartProduct(true, 1, cartProduct.product.toDomain())) }

        // when
        presenter.increaseCartProductCount(cartProduct, count)

        // then
        verify { cartRepository.insert(CartProduct(true, 1, cartProduct.product.toDomain())) }
        verify(inverse = true) { cartRepository.updateProductCount(any(), any()) }
    }

    @Test
    fun `장바구니 상품의 개수를 증가시킬 때 처음 증가 된게 아니라면 장바구니 레포지토리의 상품 개수를 수정한다`() {
        // given
        val cartProduct = CartProductUIModel(
            isPicked = true,
            count = 4,
            product = ProductUIModel.dummy
        )
        val count = 4
        justRun { cartRepository.updateProductCount(cartProduct.product.id, 5) }

        // when
        presenter.increaseCartProductCount(cartProduct, count)

        // then
        verify(inverse = true) { cartRepository.insert(any()) }
        verify { cartRepository.updateProductCount(cartProduct.product.id, 5) }
    }

    @Test
    fun `상품들의 크기를 가져와 뷰에게 넘겨준다`() {
        // given
        val productsSize = 20
        justRun { view.setGridLayoutManager(productsSize) }

        // when
        presenter.fetchProductsSizeForUpdateLayoutManager()

        // then
        verify { view.setGridLayoutManager(productsSize) }
    }

    @Test
    fun `상품의 개수를 가져온다`() {
        // given
        val product = ProductUIModel.dummy
        every { cartRepository.getProductCount(any()) } returns 2
        // when
        val actual = presenter.getProductCount(product)

        // then
        assertEquals(actual, 2)
    }
}
