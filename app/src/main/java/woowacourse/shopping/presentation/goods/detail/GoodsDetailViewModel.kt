package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingcart.ShoppingDataBase
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toDomainModel

class GoodsDetailViewModel : ViewModel() {
    private val _goodsUiModel: MutableLiveData<GoodsUiModel> = MutableLiveData()

    val goodsUiModel: LiveData<GoodsUiModel>
        get() = _goodsUiModel

    fun setGoods(goodsUiModel: GoodsUiModel) {
        if (_goodsUiModel.value == null) _goodsUiModel.value = goodsUiModel
    }

    fun addToShoppingCart() {
        _goodsUiModel.value?.let { ShoppingDataBase.addItem(it.toDomainModel()) }
    }
}
