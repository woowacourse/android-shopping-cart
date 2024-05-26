package woowacourse.shopping.feature.main.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemInquiryHistoryBinding
import woowacourse.shopping.model.InquiryHistory

class InquiryHistoryViewHolder(
    private val binding: ItemInquiryHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(inquiryHistory: InquiryHistory) {
        binding.inquiryHistory = inquiryHistory
    }
}
