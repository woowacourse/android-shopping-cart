package woowacourse.shopping.presentation.common

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.presentation.model.CartProductInfoModel

class CartProductDiffItemCallback : DiffUtil.ItemCallback<CartProductInfoModel>() {
    override fun areItemsTheSame(
        oldItem: CartProductInfoModel,
        newItem: CartProductInfoModel,
    ): Boolean = (oldItem.productModel.id == newItem.productModel.id)

    override fun areContentsTheSame(
        oldItem: CartProductInfoModel,
        newItem: CartProductInfoModel,
    ): Boolean = oldItem == newItem
}
