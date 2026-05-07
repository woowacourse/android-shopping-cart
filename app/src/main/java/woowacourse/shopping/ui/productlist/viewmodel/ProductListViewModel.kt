package woowacourse.shopping.ui.productlist.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import woowacourse.shopping.ui.state.ProductListUiState

class ProductListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
}
