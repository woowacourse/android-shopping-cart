package woowacourse.shopping.common.utils

fun convertDpToPixel(dp: Int, density: Float): Int = (dp * density).toInt()
