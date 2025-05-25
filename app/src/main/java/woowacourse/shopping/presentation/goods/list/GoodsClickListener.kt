package woowacourse.shopping.presentation.goods.list

interface GoodsClickListener {
    fun onGoodsClick(selectedGoodsId: Int)

    fun onPlusClick(goodsId: Int)
}
