package woowacourse.shopping.feature.goodsdetails

import androidx.annotation.StringRes

data class GoodsDetailsAlertMessage(
    @StringRes
    val resourceId: Int,
    val quantity: Int,
)
