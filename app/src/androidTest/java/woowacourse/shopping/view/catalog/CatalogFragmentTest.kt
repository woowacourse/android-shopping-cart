package woowacourse.shopping.view.catalog

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.R
import woowacourse.shopping.di.provider.RepositoryProvider
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.fixture.FakeCartRepository
import woowacourse.shopping.fixture.FakeProductRepository
import woowacourse.shopping.fixture.FakeRecentProductRepository
import woowacourse.shopping.fixture.productsFixture
import woowacourse.shopping.presentation.view.catalog.CatalogFragment
import woowacourse.shopping.util.clickOnViewChild
import woowacourse.shopping.util.nthChildOf

class CatalogFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<CatalogFragment>

    @BeforeEach
    fun setUp() {
        val fakeProductRepository = FakeProductRepository()
        val fakeCartRepository =
            FakeCartRepository(
                initialCartItems = productsFixture.take(1).map { CartItem(it, 1) },
            )
        val fakeRecentProductRepository =
            FakeRecentProductRepository(
                initialRecentProductIds = productsFixture.take(1).map { it.id },
            )

        RepositoryProvider.initProductRepository(fakeProductRepository)
        RepositoryProvider.initCartRepository(fakeCartRepository)
        RepositoryProvider.initRecentProductRepository(fakeRecentProductRepository)

        fragmentScenario =
            launchFragmentInContainer<CatalogFragment>(themeResId = R.style.Theme_Shopping)
    }

    @Test
    fun `더보기할_상품이_없는_경우에는_버튼이_표시되지_않는다`() {
        val recyclerView = onView(withId(R.id.recycler_view_products))

        recyclerView.perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
        onView(withId(R.id.btn_load_more)).perform(click())

        recyclerView.perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
        onView(withId(R.id.btn_load_more)).check(doesNotExist())
    }

    @Test
    fun `더보기_버튼을_누르면_새로운_상품이_로드된다`() {
        val initialCount = getRecyclerViewItemCount(R.id.recycler_view_products)
        onView(withId(R.id.recycler_view_products)).perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
        onView(withId(R.id.btn_load_more)).perform(click())

        val newCount = getRecyclerViewItemCount(R.id.recycler_view_products)

        assertThat(newCount).isGreaterThan(initialCount)
    }

    @Test
    fun `특정_상품의_구매_개수를_증가시킬_수_있다`() {
        // When
        increaseProductQuantity()

        // Then
        Thread.sleep(100)
        nthProductInRecyclerView(R.id.textview_quantity).check(matches(withText("2")))
    }

    @Test
    fun `특정_상품의_구매_개수를_감소시킬_수_있다`() {
        // Give
        increaseProductQuantity()
        increaseProductQuantity()

        // When
        decreaseProductQuantity()

        // Then
        Thread.sleep(100)
        nthProductInRecyclerView(R.id.textview_quantity).check(matches(withText("2")))
    }

    @Test
    fun `상품의_구매_수량이_1이상이면_수량_선택기가_노출된다`() {
        // When
        increaseProductQuantity(2)

        // Then
        nthProductInRecyclerView(R.id.view_quantity_selector).check(matches(isDisplayed()))
    }

    @Test
    fun `상품의_구매_수량이_0이면_수량_선택기가_보이지_않는다`() {
        // When
        decreaseProductQuantity()

        // Then
        Thread.sleep(100)
        nthProductInRecyclerView(R.id.view_quantity_selector).check(matches(not(isDisplayed())))
    }

    @Test
    fun `최근_본_상품_목록이_보여진다`() {
        // Then
        nthProductInRecyclerView(R.id.text_view_recent_product_name, 0).check(matches(withText(productsFixture[0].name)))
    }

    private fun nthProductInRecyclerView(
        targetViewId: Int,
        position: Int = 1,
    ) = onView(
        allOf(
            withId(targetViewId),
            isDescendantOfA(nthChildOf(withId(R.id.recycler_view_products), position)),
        ),
    )

    private fun decreaseProductQuantity(position: Int = 1) {
        onView(withId(R.id.recycler_view_products)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                clickOnViewChild(R.id.btn_quantity_minus),
            ),
        )
    }

    private fun increaseProductQuantity(position: Int = 1) {
        onView(withId(R.id.recycler_view_products)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                clickOnViewChild(R.id.btn_quantity_plus),
            ),
        )
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
