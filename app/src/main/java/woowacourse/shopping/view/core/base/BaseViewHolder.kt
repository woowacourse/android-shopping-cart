package woowacourse.shopping.view.core.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<out BINDING : ViewBinding>(
    protected val binding: BINDING,
) : RecyclerView.ViewHolder(binding.root)
