package woowacourse.shopping.feature.main.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemInquiryHistoryBinding
import woowacourse.shopping.model.InquiryHistory

class InquiryHistoryViewHolder(
    private val binding: ItemInquiryHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        inquiryHistory: InquiryHistory,
        onClickInquiryHistory: onClickInquiryHistory,
    ) {
        binding.inquiryHistory = inquiryHistory
        binding.root.setOnClickListener {
            onClickInquiryHistory(inquiryHistory.product.id)
        }
    }
}

typealias onClickInquiryHistory = (productId: Long) -> Unit
