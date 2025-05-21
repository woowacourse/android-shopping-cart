package woowacourse.shopping.view.catalog

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.R
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.fixture.FakeShoppingRepository
import woowacourse.shopping.fixture.dummyProductsFixture
import woowacourse.shopping.presentation.view.catalog.CatalogFragment

class CatalogFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<CatalogFragment>

    private lateinit var fakeRepository: ShoppingRepository

    @BeforeEach
    fun setUp() {
        fakeRepository =
            FakeShoppingRepository(
                dummyProductsFixture,
                mutableMapOf(),
            )

        RepositoryProvider.initShoppingRepository(fakeRepository)

        fragmentScenario =
            launchFragmentInContainer<CatalogFragment>(themeResId = R.style.Theme_Shopping)
    }

    @Test
    fun `더보기할_상품이_없는_경우에는_버튼이_표시되지_않는다`() {
        val recyclerView = onView(withId(R.id.recycler_view_products))

        recyclerView.perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
        Thread.sleep(1000)
        onView(withId(R.id.btn_load_more)).perform(click())

        Thread.sleep(1000)

        recyclerView.perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
        Thread.sleep(1000)
        onView(withId(R.id.btn_load_more)).check(doesNotExist())
        Thread.sleep(1000)
    }

    @Test
    fun `더보기_버튼을_누르면_새로운_상품이_로드된다`() {
        val initialCount = getRecyclerViewItemCount(R.id.recycler_view_products)
        onView(withId(R.id.recycler_view_products)).perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
        onView(withId(R.id.btn_load_more)).perform(click())

        val newCount = getRecyclerViewItemCount(R.id.recycler_view_products)

        assertThat(newCount).isGreaterThan(initialCount)
    }

    private fun getRecyclerViewItemCount(recyclerViewId: Int): Int {
        var itemCount = 0
        onView(withId(recyclerViewId)).check { view, _ ->
            val recyclerView = view as RecyclerView
            itemCount = recyclerView.adapter?.itemCount ?: 0
        }
        return itemCount
    }
}
