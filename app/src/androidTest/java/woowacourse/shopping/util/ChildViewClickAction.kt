package woowacourse.shopping.util

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction

fun clickOnViewChild(viewId: Int): ViewAction =
    object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "뷰 ID($viewId)를 클릭"

        override fun perform(
            uiController: UiController?,
            view: View,
        ) {
            val v = view.findViewById<View>(viewId)
            v.performClick()
        }
    }
