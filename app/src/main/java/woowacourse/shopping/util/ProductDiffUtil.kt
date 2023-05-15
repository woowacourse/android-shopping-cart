package woowacourse.shopping.util

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.presentation.model.ProductModel

object ProductDiffUtil {
    fun itemCallBack() = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(
            oldItem: ProductModel,
            newItem: ProductModel,
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ProductModel,
            newItem: ProductModel,
        ): Boolean = oldItem == newItem
    }
}
