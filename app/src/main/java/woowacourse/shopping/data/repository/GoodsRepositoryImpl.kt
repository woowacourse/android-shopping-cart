package woowacourse.shopping.data.repository

import android.util.Log
import woowacourse.shopping.data.entity.toGoods
import woowacourse.shopping.data.service.GoodsService
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.repository.GoodsRepository
import kotlin.concurrent.thread

class GoodsRepositoryImpl(
    private val goodsService: GoodsService,
) : GoodsRepository {
    override fun getById(
        id: Int,
        onSuccess: (Goods?) -> Unit,
    ) {
        thread {
            val result = goodsService.getGoodsById(id)
            result.onSuccess { goodsEntity ->
                onSuccess(goodsEntity?.toGoods())
            }.onFailure {
                Log.e(TAG, it.message ?: ERROR_LOAD_FAIL.format("getById"))
            }.getOrThrow()
        }
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
        onSuccess: (List<Goods>) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            val result = goodsService.getPagedGoods(page, count)
            result.onSuccess { goodsEntity ->
                try {
                    val converted = goodsEntity.map { it.toGoods() }
                    onSuccess(converted)
                } catch (e: Exception) {
                    onFailure(e.message)
                }
            }.onFailure {
                onFailure(it.message)
            }
        }
    }

    override fun getGoodsListByIds(
        ids: List<Int>,
        onSuccess: (List<Goods>) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            val result = goodsService.getGoodsListByIds(ids)
            result.onSuccess { goodsEntity ->
                onSuccess(goodsEntity.map { it.toGoods() })
            }.onFailure {
                onFailure(it.message)
            }
        }
    }

    companion object {
        private const val TAG = "GoodsRepositoryImpl"
        private const val ERROR_LOAD_FAIL = "%s: 서버 요청 실패"
    }
}
