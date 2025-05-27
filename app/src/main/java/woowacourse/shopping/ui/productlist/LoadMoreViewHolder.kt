package woowacourse.shopping.ui.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.LoadMoreItemBinding

class LoadMoreViewHolder private constructor(
    binding: LoadMoreItemBinding,
    loadMoreClickListener: LoadMoreClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.loadMoreClickListener = loadMoreClickListener
    }

    companion object {
        fun create(
            parent: ViewGroup,
            loadMoreClickListener: LoadMoreClickListener,
        ): LoadMoreViewHolder {
            return LoadMoreViewHolder(
                binding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.load_more_item,
                        parent,
                        false,
                    ),
                loadMoreClickListener = loadMoreClickListener,
            )
        }
    }
}
