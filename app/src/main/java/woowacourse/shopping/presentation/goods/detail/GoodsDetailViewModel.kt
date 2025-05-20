package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingcart.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toDomainModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData

class GoodsDetailViewModel(
    goods: GoodsUiModel,
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val _goodsUiModel: MutableLiveData<GoodsUiModel> = MutableLiveData(goods)
    val goodsUiModel: LiveData<GoodsUiModel>
        get() = _goodsUiModel

    private val _onGoodsAdded: MutableSingleLiveData<Unit> = MutableSingleLiveData()
    val onGoodsAdded: SingleLiveData<Unit>
        get() = _onGoodsAdded

    fun addToShoppingCart() {
        _goodsUiModel.value?.let { repository.addGoods(it.toDomainModel()) }
        _onGoodsAdded.setValue(Unit)
    }
}
