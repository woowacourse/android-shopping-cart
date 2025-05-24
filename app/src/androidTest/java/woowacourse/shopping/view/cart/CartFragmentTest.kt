package woowacourse.shopping.view.cart

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isNotClickable
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
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
import woowacourse.shopping.presentation.view.cart.CartFragment
import woowacourse.shopping.util.clickOnViewChild
import woowacourse.shopping.util.nthChildOf

class CartFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<CartFragment>

    @BeforeEach
    fun setup() {
        val fakeProductRepository = FakeProductRepository()
        val fakeCartRepository =
            FakeCartRepository(
                initialCartItems = productsFixture.take(15).map { CartItem(it, 1) },
            )
        val fakeRecentProductRepository = FakeRecentProductRepository()

        RepositoryProvider.initProductRepository(fakeProductRepository)
        RepositoryProvider.initCartRepository(fakeCartRepository)
        RepositoryProvider.initRecentProductRepository(fakeRecentProductRepository)

        fragmentScenario =
            launchFragmentInContainer(
                themeResId = R.style.Theme_Shopping,
            ) { CartFragment() }
    }

    @Test
    fun `장바구니에_담긴_상품을_확인할_수_있다`() {
        firstProductInRecyclerView(R.id.cart_item_name).check(matches(withText(productsFixture[0].name)))
    }

    @Test
    fun `다음_페이지_버튼을_누르면_페이지_수가_증가한다`() {
        onView(withId(R.id.btn_right)).perform(click())
        onView(withId(R.id.text_view_page)).check(matches(withText("2")))
    }

    @Test
    fun `이전_페이지_버튼을_누르면_페이지_수가_감소한다`() {
        onView(withId(R.id.btn_right)).perform(click())
        onView(withId(R.id.btn_right)).perform(click())

        onView(withId(R.id.btn_left)).perform(click())

        onView(withId(R.id.text_view_page)).check(matches(withText("2")))
    }

    @Test
    fun `처음_페이지에서는_이전페이지_버튼을_클릭할_수_없다`() {
        onView(withId(R.id.text_view_page)).check(matches(withText("1")))

        onView(withId(R.id.btn_left)).check(matches(isNotClickable()))
    }

    @Test
    fun `다음_페이지에_조회_가능한_상품이_없다면_다음페이지_버튼을_클릭할_수_없다`() {
        onView(withId(R.id.btn_right)).perform(click())
        onView(withId(R.id.btn_right)).perform(click())

        onView(withId(R.id.text_view_page)).check(matches(withText("3")))

        onView(withId(R.id.btn_right)).check(matches(isNotClickable()))
    }

    @Test
    fun `장바구니에_담긴_상품을_제거할_수_있다`() {
        firstProductInRecyclerView(R.id.cart_item_name).check(matches(withText(productsFixture[0].name)))

        onView(withId(R.id.recycler_view_cart))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickOnViewChild(R.id.btn_remove_cart),
                ),
            )

        firstProductInRecyclerView(R.id.cart_item_name).check(matches(withText(productsFixture[1].name)))
    }

    @Test
    fun `특정_상품의_구매_개수를_증가시킬_수_있다`() {
        // When
        onView(withId(R.id.recycler_view_cart))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickOnViewChild(R.id.btn_quantity_plus),
                ),
            )

        // Then
        firstProductInRecyclerView(R.id.textview_quantity).check(matches(withText("2")))
    }

    @Test
    fun `특정_상품의_구매_개수를_감소시킬_수_있다`() {
        // Give
        onView(withId(R.id.recycler_view_cart))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickOnViewChild(R.id.btn_quantity_plus),
                ),
            )
        onView(withId(R.id.recycler_view_cart))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickOnViewChild(R.id.btn_quantity_plus),
                ),
            )

        // When
        onView(withId(R.id.recycler_view_cart))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickOnViewChild(R.id.btn_quantity_minus),
                ),
            )

        // Then
        firstProductInRecyclerView(R.id.textview_quantity).check(matches(withText("2")))
    }

    @Test
    fun `구매_개수가_1개인_특정_상품의_구매_개수를_감소시키면_삭제된다`() {
        // When
        onView(withId(R.id.recycler_view_cart))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickOnViewChild(R.id.btn_quantity_minus),
                ),
            )

        // Then
        firstProductInRecyclerView(R.id.cart_item_name).check(matches(withText(productsFixture[1].name)))
    }

    private fun firstProductInRecyclerView(targetViewId: Int) =
        onView(
            allOf(
                withId(targetViewId),
                isDescendantOfA(nthChildOf(withId(R.id.recycler_view_cart), 0)),
            ),
        )
}
