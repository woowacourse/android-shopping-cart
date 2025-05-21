package woowacourse.shopping.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.LoadMoreButtonItemBinding

class LoadButtonViewHolder(
    binding: LoadMoreButtonItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(
            parent: ViewGroup,
            handler: CatalogEventHandler,
        ): LoadButtonViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LoadMoreButtonItemBinding.inflate(inflater, parent, false)
            binding.handler = handler
            return LoadButtonViewHolder(binding)
        }
    }
}
