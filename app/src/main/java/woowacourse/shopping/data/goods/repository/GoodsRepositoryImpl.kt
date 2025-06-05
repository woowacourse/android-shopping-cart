package woowacourse.shopping.data.goods.repository

import woowacourse.shopping.domain.model.Goods

class GoodsRepositoryImpl(
    private val remoteDataSource: GoodsRemoteDataSource,
    private val localDataSource: GoodsLocalDataSource,
) : GoodsRepository {
    override fun fetchGoodsSize(onComplete: (Int) -> Unit) {
        remoteDataSource.fetchGoodsSize(onComplete)
    }

    override fun fetchPageGoods(
        limit: Int,
        offset: Int,
        onComplete: (List<Goods>) -> Unit,
    ) {
        remoteDataSource.fetchPageGoods(limit, offset, onComplete)
    }

    override fun fetchGoodsById(
        id: Int,
        onComplete: (Goods?) -> Unit,
    ) {
        remoteDataSource.fetchGoodsById(id, onComplete)
    }

    override fun fetchRecentGoodsIds(onComplete: (List<String>) -> Unit) {
        localDataSource.fetchRecentGoodsIds(onComplete)
    }

    override fun fetchRecentGoods(onComplete: (List<Goods>) -> Unit) {
        fetchRecentGoodsIds { recentIds ->
            if (recentIds.isEmpty()) {
                onComplete(emptyList())
                return@fetchRecentGoodsIds
            }

            remoteDataSource.fetchGoodsByIds(recentIds.map { it.toInt() }) {
                onComplete(it ?: emptyList())
            }
        }
    }

    override fun fetchMostRecentGoods(onComplete: (Goods?) -> Unit) {
        fetchRecentGoodsIds { recentIds ->
            if (recentIds.isEmpty()) {
                onComplete(null)
                return@fetchRecentGoodsIds
            }
            val id = recentIds[0].toInt()
            fetchGoodsById(id) { onComplete(it) }
        }
    }

    override fun loggingRecentGoods(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        localDataSource.loggingRecentGoods(goods, onComplete)
    }
}
