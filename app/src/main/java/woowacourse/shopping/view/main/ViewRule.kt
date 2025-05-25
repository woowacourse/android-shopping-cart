package woowacourse.shopping.view.main

import android.view.View

interface ViewRule {
    fun menuBadgeViewRule(text: String): Int {
        return if (text.toInt() > MINIMUM_BADGE_DISPLAY_QUANTITY) View.VISIBLE else View.GONE
    }

    fun onProductAddedButtonViewRule(quantityValue: Int): Int {
        return if (quantityValue < 1) View.VISIBLE else View.GONE
    }

    fun onProductAddedQuantitySelectorViewRule(quantityValue: Int): Int {
        return if (quantityValue < 1) View.GONE else View.VISIBLE
    }

    companion object {
        private const val MINIMUM_BADGE_DISPLAY_QUANTITY = 0
    }
}
