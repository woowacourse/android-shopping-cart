package woowacourse.shopping.data.goods.repository

import android.os.Handler
import android.os.Looper
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.goods.RecentSeenGoodsEntity
import woowacourse.shopping.domain.model.Goods
import kotlin.concurrent.thread

class GoodsLocalDataSourceImpl(
    private val shoppingDatabase: ShoppingDatabase,
) : GoodsLocalDataSource {
    override fun fetchRecentGoodsIds(onComplete: (List<String>) -> Unit) {
        thread {
            val ids = shoppingDatabase.recentSeenGoodsDao().getRecentGoodsIds()
            Handler(Looper.getMainLooper()).post {
                onComplete(ids)
            }
        }
    }

    override fun loggingRecentGoods(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        shoppingDatabase
            .recentSeenGoodsDao()
            .insert(RecentSeenGoodsEntity(goodsId = goods.id.toString()))
        Handler(Looper.getMainLooper()).post {
            onComplete()
        }
    }
}
