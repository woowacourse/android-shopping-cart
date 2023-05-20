package woowacourse.shopping.ui.shopping.viewHolder

import woowacourse.shopping.utils.CustomViewOnClickListener

interface ProductsOnClickListener : CustomViewOnClickListener {
    fun onClick(id: Long)
}
