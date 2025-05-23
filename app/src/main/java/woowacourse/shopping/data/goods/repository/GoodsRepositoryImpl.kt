package woowacourse.shopping.data.goods.repository

import woowacourse.shopping.domain.model.Goods

class GoodsRepositoryImpl(
    private val remoteDataSource: GoodsRemoteDataSource,
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
}
