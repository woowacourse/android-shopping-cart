package woowacourse.shopping.presentation.shopping.product.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentWrapperBinding

class RecentProductWrapperAdapter(private val adapter: RecentProductAdapter) :
    RecyclerView.Adapter<RecentProductViewHolderWrapper>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolderWrapper {
        return RecentProductViewHolderWrapper(
            ItemRecentWrapperBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolderWrapper,
        position: Int,
    ) {
        holder.bind(adapter)
    }

    override fun getItemCount(): Int = 1
}
