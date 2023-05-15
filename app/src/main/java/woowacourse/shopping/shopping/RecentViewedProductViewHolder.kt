package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.model.RecentViewedProductUiModel

class RecentViewedProductViewHolder private constructor(
    private val binding: ItemRecentViewedProductBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: RecentViewedProductUiModel) {
        with(binding) {
            Glide.with(root.context)
                .load(product.imageUrl)
                .into(imageRecentProduct)

            textRecentProductName.text = product.name
        }
    }

    companion object {
        fun from(parent: ViewGroup): RecentViewedProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentViewedProductBinding.inflate(layoutInflater, parent, false)

            return RecentViewedProductViewHolder(binding)
        }
    }
}
