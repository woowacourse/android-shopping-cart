package woowacourse.shopping.presentation.shoppingcart

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.kotest.matchers.shouldBe
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.shopping.ShoppingDao
import woowacourse.shopping.data.shopping.ShoppingDatabase
import woowacourse.shopping.data.shopping.ShoppingRepositoryImpl
import woowacourse.shopping.domain.repository.ShoppingRepository

class ShoppingCartActivityTest {
    private lateinit var scenario: ActivityScenario<ShoppingCartActivity>
    private lateinit var intent: Intent
    private lateinit var db: ShoppingDatabase
    private lateinit var dao: ShoppingDao
    private lateinit var shoppingRepository: ShoppingRepository

    // Before
    private fun setUp(count: Int) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val application = context as ShoppingApplication
        db =
            Room
                .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = db.shoppingDao()
        shoppingRepository = ShoppingRepositoryImpl(dao)
        application.initShoppingRepository(shoppingRepository as ShoppingRepositoryImpl)

        addItems(count)

        intent = Intent(context, ShoppingCartActivity::class.java)
        scenario = ActivityScenario.launch(intent)
    }

    // After
    private fun tearDown(count: Int) {
        removeItems(count)

        scenario.close()
        db.close()
    }

    @Test
    fun `데이터베이스에_저장된_상품_목록이_보인다`() {
        setUp(1)

        onView(withText("[병천아우내] 모듬순대"))
            .check(matches(isDisplayed()))

        onView(withText("11,900원"))
            .check(matches(isDisplayed()))

        tearDown(1)
    }

    @Test
    fun `x_버튼을_누르면_해당_상품이_사라진다`() {
        // given
        setUp(1)
        onView(withId(R.id.rv_selected_goods_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        // when
        onView(withId(R.id.btn_delete))
            .perform(click())

        // then
        onView(withText("[병천아우내] 모듬순대"))
            .check(doesNotExist())

        tearDown(1)
    }

    @Test
    fun `상품이_5개_이하면_페이지_정보가_보이지_않는다`() {
        // given
        setUp(4)

        // then
        onView(withId(R.id.cl_page_info))
            .check(matches(not(isDisplayed())))

        tearDown(4)
    }

    @Test
    fun `상품이_5개_초과면_페이지_정보가_보인다`() {
        // given
        setUp(6)

        // then
        onView(withId(R.id.cl_page_info))
            .check(matches(isDisplayed()))

        tearDown(6)
    }

    @Test
    fun `다음_페이지_버튼을_누르면_페이지가_증가한다`() {
        // given
        setUp(10)

        onView(withId(R.id.tv_page))
            .check(matches(withText("1")))

        // when
        clickButton(R.id.btn_next_page)

        // then
        onView(withId(R.id.tv_page))
            .check(matches(withText("2")))

        tearDown(10)
    }

    @Test
    fun `이전_페이지_버튼을_누르면_페이지가_감소한다`() {
        // given
        setUp(10)
        clickButton(R.id.btn_next_page)

        onView(withId(R.id.tv_page))
            .check(matches(withText("2")))

        // when
        clickButton(R.id.btn_previous_page)

        // then
        onView(withId(R.id.tv_page))
            .check(matches(withText("1")))

        tearDown(10)
    }

    @Test
    fun `plus_버튼을_누르면_상품_개수가_증가한다`() {
        // given
        setUp(1)

        // when
        onView(
            allOf(
                withId(R.id.tv_plus),
                isDescendantOfA(nthChildOf(withId(R.id.rv_selected_goods_list), 0)),
            ),
        ).perform(click())

        // then
        onView(
            allOf(
                withId(R.id.tv_count),
                isDescendantOfA(nthChildOf(withId(R.id.rv_selected_goods_list), 0)),
            ),
        ).check(matches(withText("2")))

        tearDown(1)
    }

    @Test
    fun `minus_버튼을_누르면_상품_개수가_감소한다`() {
        // given
        setUp(1)

        onView(
            allOf(
                withId(R.id.tv_plus),
                isDescendantOfA(nthChildOf(withId(R.id.rv_selected_goods_list), 0)),
            ),
        ).perform(click())

        // when
        onView(
            allOf(
                withId(R.id.tv_minus),
                isDescendantOfA(nthChildOf(withId(R.id.rv_selected_goods_list), 0)),
            ),
        ).perform(click())

        // then
        onView(
            allOf(
                withId(R.id.tv_count),
                isDescendantOfA(nthChildOf(withId(R.id.rv_selected_goods_list), 0)),
            ),
        ).check(matches(withText("1")))

        tearDown(1)
    }

    @Test
    fun `상품의_개수가_0이면_목록에서_삭제한다`() {
        // given
        setUp(1)

        // when
        onView(
            allOf(
                withId(R.id.tv_minus),
                isDescendantOfA(nthChildOf(withId(R.id.rv_selected_goods_list), 0)),
            ),
        ).perform(click())

        // then
        onView(withId(R.id.rv_selected_goods_list)).check { view, _ ->
            val recyclerView = view as RecyclerView
            val itemCount = recyclerView.adapter?.itemCount

            itemCount shouldBe 0
        }

        tearDown(1)
    }

    private fun addItems(count: Int) {
        repeat(count) {
            shoppingRepository.increaseItemQuantity(it + 1)
        }
    }

    private fun removeItems(count: Int) {
        repeat(count) {
            shoppingRepository.decreaseItemQuantity(it + 1)
        }
    }

    private fun clickButton(button: Int) {
        onView(withId(button))
            .perform(click())
    }

    private fun nthChildOf(
        parentMatcher: Matcher<View>,
        childPosition: Int,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Nth child of parent matcher")
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && parent.getChildAt(
                    childPosition,
                ) == view
            }
        }
    }
}
