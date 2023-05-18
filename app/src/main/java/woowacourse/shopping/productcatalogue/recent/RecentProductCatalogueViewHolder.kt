package woowacourse.shopping.productcatalogue.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentProductCatalogueBinding

class RecentProductCatalogueViewHolder(
    binding: RecentProductCatalogueBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun getView(parent: ViewGroup): RecentProductCatalogueBinding {
            val inflater = LayoutInflater.from(parent.context)
            return RecentProductCatalogueBinding.inflate(inflater, parent, false)
        }
    }
}
