package woowacourse.shopping.view.main.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductViewHodler(
    private val bind: ItemProductBinding,
) : RecyclerView.ViewHolder(bind.root) {
    fun bind(item: Product) {
        bind.model = item
    }
}
