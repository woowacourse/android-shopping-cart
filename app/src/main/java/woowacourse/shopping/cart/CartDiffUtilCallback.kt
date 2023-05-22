package woowacourse.shopping.cart

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.common.model.CheckableCartProductModel

class CartDiffUtilCallback(
    private val checkableCartProducts: List<CheckableCartProductModel>,
    private val newCheckableCartProducts: List<CheckableCartProductModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = checkableCartProducts.size

    override fun getNewListSize(): Int = newCheckableCartProducts.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        checkableCartProducts[oldItemPosition] == newCheckableCartProducts[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        checkableCartProducts[oldItemPosition] == newCheckableCartProducts[newItemPosition]
}
