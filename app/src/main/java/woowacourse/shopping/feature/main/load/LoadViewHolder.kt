package woowacourse.shopping.feature.main.load

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreProductBinding

class LoadViewHolder private constructor(
    val binding: ItemLoadMoreProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(onClick: () -> Unit) {
        binding.loadMore.setOnClickListener { onClick.invoke() }
    }

    fun hide() {
        binding.loadMore.visibility = View.GONE
    }

    companion object {
        fun create(parent: ViewGroup): LoadViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemLoadMoreProductBinding.inflate(layoutInflater, parent, false)
            return LoadViewHolder(binding)
        }
    }
}
