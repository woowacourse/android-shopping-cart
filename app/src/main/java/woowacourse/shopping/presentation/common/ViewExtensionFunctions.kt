package woowacourse.shopping.presentation.common

import android.view.View

fun View.showIf(condition: Boolean) {
    if (condition) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.hideIf(condition: Boolean) {
    if (condition) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}
