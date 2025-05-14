package woowacourse.shopping.presentation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.presentation.UiState
import woowacourse.shopping.presentation.model.ProductUiModel

class DetailViewModel : ViewModel() {
    private val _saveState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val saveState: LiveData<UiState<Unit>> = _saveState

    fun addProduct(product: ProductUiModel) {
        _saveState.value = UiState.Success(Unit)
    }
}
