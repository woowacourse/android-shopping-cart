package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.model.ProductUiModel

class RecentViewedViewHolder(
    private val binding: ItemRecentViewedProductBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductUiModel) {
        with(binding) {
            Glide.with(root.context)
                .load(product.imageUrl)
                .into(imageRecentProduct)

            textRecentProductName.text = product.name
        }
    }

    companion object {
        fun from(parent: ViewGroup): RecentViewedViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentViewedProductBinding.inflate(layoutInflater, parent, false)

            return RecentViewedViewHolder(binding)
        }
    }
}
