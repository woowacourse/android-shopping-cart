package woowacourse.shopping.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory<T : ViewModel>(
    private val creator: () -> T,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress(UNCHECKED_CAST)
        return creator() as T
    }

    companion object {
        private const val UNCHECKED_CAST = "UNCHECKED_CAST"
    }
}
