package woowacourse.shopping.view.inventory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.inventory.item.RecentProduct
import woowacourse.shopping.view.inventory.viewholder.RecentItemViewHolder
import java.time.LocalDateTime

class RecentListAdapter : RecyclerView.Adapter<RecentItemViewHolder>() {
    private val items =
        listOf(
            RecentProduct(
                0,
                "[병천아우내] 모듬순대",
                "",
                LocalDateTime.now(),
            ),
            RecentProduct(
                1,
                "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
                "",
                LocalDateTime.now(),
            ),
            RecentProduct(
                2,
                "[애슐리] 크런치즈엣지 포테이토피자 495g",
                "",
                LocalDateTime.now(),
            ),
            RecentProduct(
                3,
                "치밥하기 좋은 순살 바베큐치킨",
                "",
                LocalDateTime.now(),
            ),
            RecentProduct(
                4,
                "[이연복의 목란] 짜장면 2인분",
                "",
                LocalDateTime.now(),
            ),
            RecentProduct(
                5,
                "[콜린스 다이닝] 마르게리타 미트볼",
                "",
                LocalDateTime.now(),
            ),
            RecentProduct(
                6,
                "[투다리] 푸짐한 김치어묵 우동전골",
                "",
                LocalDateTime.now(),
            ),
            RecentProduct(
                7,
                "[투다리] 한우대창나베",
                "",
                LocalDateTime.now(),
            ),
            RecentProduct(
                8,
                "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
                "",
                LocalDateTime.now(),
            ),
            RecentProduct(
                9,
                "[투다리] 오리지널 알탕",
                "",
                LocalDateTime.now(),
            ),
        )

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentItemViewHolder {
        return RecentItemViewHolder(parent)
    }

    override fun onBindViewHolder(
        holder: RecentItemViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
