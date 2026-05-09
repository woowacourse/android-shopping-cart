package woowacourse.shopping.ui

import android.icu.text.DecimalFormat

interface DisplayText {
    fun display(): String
}

@JvmInline
value class WonMoney(
    private val amount: Int,
) : DisplayText {
    override fun display(): String = DecimalFormat("#,###원").format(amount)
}
