package woowacourse.shopping.presentation.shopping

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product

class ShoppingViewHolder(private val binding: ItemProductBinding, private val view: View) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        clickListener: ProductClickListener,
    ) {
        binding.product = product
        binding.clickListener = clickListener
    }
}
