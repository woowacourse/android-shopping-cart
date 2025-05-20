package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.mapper.toProductDomain
import woowacourse.shopping.databinding.ItemSelectedProductBinding
import woowacourse.shopping.domain.ShoppingProduct

class SelectedProductViewHolder(
    private val binding: ItemSelectedProductBinding,
    eventListener: OnRemoveProductListener,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: ShoppingProduct

    init {
        binding.onRemoveClick =
            OnClickListener { eventListener.onClickCancel(currentItem) }
    }

    fun bind(shoppingProduct: ShoppingProduct) {
        currentItem = shoppingProduct
        binding.product = shoppingProduct.productId.toProductDomain()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: OnRemoveProductListener,
        ): SelectedProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSelectedProductBinding.inflate(inflater, parent, false)
            return SelectedProductViewHolder(binding, eventListener)
        }
    }
}
