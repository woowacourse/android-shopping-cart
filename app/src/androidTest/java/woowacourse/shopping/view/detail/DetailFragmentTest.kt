package woowacourse.shopping.view.detail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.R
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.fixture.dummyProductsFixture
import woowacourse.shopping.presentation.view.detail.DetailFragment

class DetailFragmentTest {
    private val fakeRepository =
        object : ProductRepository {
            override fun findProductById(id: Long): Result<Product> = Result.success(dummyProductsFixture[0])

            override fun findProductsByIds(ids: List<Long>): Result<List<Product>> = Result.success(emptyList())

            override fun loadProducts(
                offset: Int,
                loadSize: Int,
            ): Result<PageableItem<Product>> = Result.success(PageableItem(emptyList(), false))
        }

    @BeforeEach
    fun setup() {
        RepositoryProvider.initProductRepository(fakeRepository)

        launchFragmentInContainer(
            DetailFragment.newBundle(dummyProductsFixture[0].id),
            themeResId = R.style.Theme_Shopping,
        ) { DetailFragment() }
    }

    @Test
    fun `상품의_이름_가격_이미지를_확인할_수_있다`() {
        onView(withId(R.id.text_view_detail_product_name)).check(
            matches(
                withText(
                    dummyProductsFixture[0].name,
                ),
            ),
        )

        val expectedPrice = "%,d원".format(dummyProductsFixture[0].price.value)

        onView(withId(R.id.text_view_detail_price)).check(matches(withText(expectedPrice)))
        onView(withId(R.id.image_view_detail_product)).check(matches(isDisplayed()))
    }
}
