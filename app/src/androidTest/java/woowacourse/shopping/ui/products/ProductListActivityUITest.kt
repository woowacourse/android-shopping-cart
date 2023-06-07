package woowacourse.shopping.ui.products

import android.database.sqlite.SQLiteDatabase
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.database.ProductDBHelper
import woowacourse.shopping.ui.productdetail.ProductDetailActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class ProductListActivityUITest {
    private lateinit var database: SQLiteDatabase
    private lateinit var listActivityScenario: ActivityScenario<ProductListActivity>

    @Before
    fun setup() {
        database = ProductDBHelper(ApplicationProvider.getApplicationContext()).writableDatabase
        database.delete("recently_viewed_product", null, null)

        listActivityScenario = ActivityScenario.launch(ProductListActivity::class.java)
    }

    @After
    fun tearDown() {
        listActivityScenario.close()
        database.close()
    }

    @Test
    fun 앱_첫_실행_후_전체_상품_리사이클러뷰가_보인다() {
        해당_항목이_보인다(R.id.rv_main_product)
    }

    @Test
    fun 앱_첫_실행_후_최근_본_상품_리사이클러뷰가_보이지_않는다() {
        해당_항목이_보이지_않는다(R.id.rv_recently_viewed)
    }

    @Test
    fun 앱_첫_실행_후_상품_한_개를_보면_최근_본_상품_리사이클러뷰가_보인다() {
        해당_항목이_보이지_않는다(R.id.rv_recently_viewed)

        상품_리스트의_해당_아이템을_클릭하면(1)
        상세_페이지에서_뒤로가기를_누르면()

        해당_항목이_보인다(R.id.rv_recently_viewed)
    }

    @Test
    fun 상품_목록_스크롤을_올리면_최근_본_상품_리사이클러뷰가_보이지_않는다() {
        상품_리스트의_해당_아이템을_클릭하면(1)
        상세_페이지에서_뒤로가기를_누르면()

        해당_항목이_보인다(R.id.rv_recently_viewed)

        화면의_스크롤을_내리면()

        해당_항목이_보이지_않는다(R.id.rv_recently_viewed)
    }

    private fun 해당_항목이_보인다(viewId: Int) {
        onView(withId(viewId))
            .check(matches(isDisplayed()))
    }

    private fun 해당_항목이_보이지_않는다(viewId: Int) {
        onView(withId(viewId))
            .check(matches(not(isDisplayed())))
    }

    private fun 상품_리스트의_해당_아이템을_클릭하면(상품_인덱스: Int) {
        onView(withId(R.id.rv_main_product))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    상품_인덱스,
                    click(),
                ),
            )
        listActivityScenario.close()
    }

    private fun 상세_페이지에서_뒤로가기를_누르면() {
        ActivityScenario.launch(ProductDetailActivity::class.java).use {
            pressBack()
        }

        listActivityScenario = ActivityScenario.launch(ProductListActivity::class.java)
    }

    private fun 화면의_스크롤을_내리면() {
        onView(withId(R.id.layout_recently_viewed))
            .perform(swipeUp())
    }
}
