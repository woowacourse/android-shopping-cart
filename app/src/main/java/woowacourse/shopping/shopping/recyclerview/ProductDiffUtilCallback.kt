package woowacourse.shopping.shopping.recyclerview

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.common.model.CartProductModel

class ProductDiffUtilCallback(
    private val cartProducts: List<CartProductModel>,
    private val newCartProducts: List<CartProductModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = cartProducts.size

    override fun getNewListSize(): Int = newCartProducts.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        cartProducts[oldItemPosition] == newCartProducts[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        cartProducts[oldItemPosition] == newCartProducts[newItemPosition]
}
