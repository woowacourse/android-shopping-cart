package woowacourse.shopping.ui.fashionlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.product.Product

class RecentProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<RecentProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemRecentProductBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_recent_product, parent, false)
        return RecentProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(products[position])
    }
}
