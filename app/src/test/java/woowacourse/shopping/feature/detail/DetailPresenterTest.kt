package woowacourse.shopping.feature.detail

import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation

internal class DetailPresenterTest {
    private lateinit var view: DetailContract.View
    private lateinit var presenter: DetailContract.Presenter
    private lateinit var cartRepository: CartRepository

    @Before
    fun init() {
        view = mockk(relaxed = true)
        cartRepository = mockk()
        presenter = DetailPresenter(view, cartRepository, mockProduct)
    }

    @Test
    fun `장바구니에 상품을 추가한다`() {
        val slot = slot<Product>()
        every { cartRepository.addProduct(capture(slot), 1) } just Runs

        presenter.addCart()

        val actual = mockProduct.toDomain()
        val expected = slot.captured

        assert(actual == expected)
        verify { view.showCartScreen() }
    }

    private val mockProduct = productsDatasource[0].toPresentation()
}
