package woowacourse.shopping.presentation.goods.list

sealed interface GoodsEvent {
    data object LoadMore : GoodsEvent
}
