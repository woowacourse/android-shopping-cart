package woowacourse.shopping.presentation.ui

import android.content.Context
import woowacourse.shopping.R
import java.text.DecimalFormat

fun getPriceText(
    context: Context,
    price: Long,
): String {
    val priceString = DecimalFormat("#,###").format(price)
    return context.getString(R.string.won, priceString)
}
