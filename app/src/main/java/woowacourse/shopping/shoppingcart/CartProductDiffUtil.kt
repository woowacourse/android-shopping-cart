package woowacourse.shopping.shoppingcart

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.model.CartProductUiModel

class CartProductDiffUtil(
    private val oldCartProducts: List<CartProductUiModel>,
    private val newCartProducts: List<CartProductUiModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldCartProducts.size

    override fun getNewListSize(): Int = newCartProducts.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCartProducts[oldItemPosition] == newCartProducts[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCartProducts[oldItemPosition] == newCartProducts[newItemPosition]
}
