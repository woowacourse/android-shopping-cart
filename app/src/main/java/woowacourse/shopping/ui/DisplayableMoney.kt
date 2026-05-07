package woowacourse.shopping.ui

import android.icu.text.DecimalFormat

interface DisplayableMoney {
    fun display(): String
}

@JvmInline
value class WonMoney(
    private val amount: Int,
) : DisplayableMoney {
    override fun display(): String = DecimalFormat("#,###원").format(amount)
}
