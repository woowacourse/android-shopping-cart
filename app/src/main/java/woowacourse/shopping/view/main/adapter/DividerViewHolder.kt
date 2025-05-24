package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemDividerBinding
import woowacourse.shopping.view.core.base.BaseViewHolder

class DividerViewHolder(
    parent: ViewGroup,
) : BaseViewHolder<ItemDividerBinding>(
        ItemDividerBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )
