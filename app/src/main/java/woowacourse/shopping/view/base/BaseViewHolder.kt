package woowacourse.shopping.view.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<BINDING : ViewBinding>(
    protected val binding: BINDING,
) : RecyclerView.ViewHolder(binding.root)
