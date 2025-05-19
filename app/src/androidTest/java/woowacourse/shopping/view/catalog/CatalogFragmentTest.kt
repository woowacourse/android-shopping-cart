package woowacourse.shopping.view.catalog

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.fixture.dummyProductsFixture
import woowacourse.shopping.presentation.view.catalog.CatalogFragment
import woowacourse.shopping.util.nthChildOf

class CatalogFragmentTest {
    private val fakeRepository =
        object : ProductRepository {
            override fun loadProducts(
                lastItemId: Long,
                loadSize: Int,
                callback: (List<Product>, Boolean) -> Unit,
            ) {
                val products = dummyProductsFixture.filter { it.id > lastItemId }.take(loadSize)
                val lastId = products.lastOrNull()?.id ?: return callback(products, false)

                val hasMore = products.any { it.id > lastId }

                callback(products, hasMore)
            }
        }

    @Before
    fun setup() {
        launchFragmentInContainer { CatalogFragment() }
        RepositoryProvider.initProductRepository(fakeRepository)
    }

    @Test
    fun `상품_목록을_확인할_수_있다`() {
        onView(
            allOf(
                withId(R.id.text_view_product_name),
                isDescendantOfA(nthChildOf(withId(R.id.recycler_view_products), 0)),
            ),
        ).check(matches(withText(dummyProductsFixture[0].name)))
    }

    @Test
    fun `더보기할_상품이_없는_경우에는_버튼이_표시되지_않는다`() {
        val recyclerView = onView(withId(R.id.recycler_view_products))

        recyclerView.perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
        onView(withId(R.id.btn_load_more)).perform(click())

        onView(withId(R.id.btn_load_more)).check(doesNotExist())
    }

    @Test
    fun `더보기_버튼을_누르면_새로운_상품이_로드된다`() {
        val recyclerView = onView(withId(R.id.recycler_view_products))

        recyclerView.perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
        onView(withId(R.id.btn_load_more)).perform(click())

        onView(
            allOf(
                withId(R.id.text_view_product_name),
                isDescendantOfA(nthChildOf(withId(R.id.recycler_view_products), 0)),
            ),
        ).check(matches(withText(dummyProductsFixture[21].name)))
    }
}
