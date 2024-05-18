package woowacourse.shopping.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class BaseViewModelFactory<BVM : BaseViewModel>(
    private val creator: () -> BVM,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        if (modelClass.isAssignableFrom(creator().javaClass)) {
            @Suppress("UNCHECKED_CAST")
            return creator() as T
        }
        throw IllegalArgumentException("알 수 없는 BaseViewModel 클래스 : ${modelClass.name}")
    }
}
