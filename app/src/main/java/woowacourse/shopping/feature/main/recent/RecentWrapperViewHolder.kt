package woowacourse.shopping.feature.main.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentWrapperBinding

class RecentWrapperViewHolder private constructor(
    private val binding: ItemRecentWrapperBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(adapter: RecentAdapter, lastScrollX: Int, onScrolled: (Int) -> Unit) {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.doOnPreDraw {
            binding.recyclerview.scrollBy(lastScrollX, 0)
        }
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                onScrolled(recyclerView.computeHorizontalScrollOffset())
            }
        })
    }

    companion object {
        fun create(parent: ViewGroup): RecentWrapperViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentWrapperBinding.inflate(layoutInflater, parent, false)
            return RecentWrapperViewHolder(binding)
        }
    }
}
