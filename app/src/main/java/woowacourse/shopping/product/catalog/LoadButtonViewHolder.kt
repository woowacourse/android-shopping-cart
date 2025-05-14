package woowacourse.shopping.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.LoadMoreButtonItemBinding

class LoadButtonViewHolder(
    private val binding: LoadMoreButtonItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
    }

    companion object {
        fun from(parent: ViewGroup): LoadButtonViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LoadMoreButtonItemBinding.inflate(inflater, parent, false)
            return LoadButtonViewHolder(binding)
        }
    }
}
