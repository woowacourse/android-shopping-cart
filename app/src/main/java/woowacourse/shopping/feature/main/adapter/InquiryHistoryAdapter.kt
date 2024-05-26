package woowacourse.shopping.feature.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemInquiryHistoryBinding
import woowacourse.shopping.model.InquiryHistory

class InquiryHistoryAdapter : RecyclerView.Adapter<InquiryHistoryViewHolder>() {
    private val inquiryHistories: MutableList<InquiryHistory> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): InquiryHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInquiryHistoryBinding.inflate(inflater, parent, false)
        return InquiryHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = inquiryHistories.size

    override fun onBindViewHolder(
        holder: InquiryHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(inquiryHistories[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateInquiryHistories(newInquiryHistories: List<InquiryHistory>) {
        inquiryHistories.clear()
        inquiryHistories.addAll(newInquiryHistories)
        notifyDataSetChanged()
    }
}
