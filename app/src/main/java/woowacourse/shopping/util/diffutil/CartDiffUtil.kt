package woowacourse.shopping.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.model.UiCartProduct

object CartDiffUtil : DiffUtil.ItemCallback<UiCartProduct>() {
    override fun areItemsTheSame(
        oldItem: UiCartProduct,
        newItem: UiCartProduct,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: UiCartProduct,
        newItem: UiCartProduct,
    ): Boolean = oldItem == newItem
}
