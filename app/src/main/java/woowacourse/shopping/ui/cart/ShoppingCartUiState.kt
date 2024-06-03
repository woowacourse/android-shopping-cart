package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.model.Product

abstract class ShoppingCartUiState {
    abstract val page: LiveData<Int>
    abstract val itemsInCurrentPage: LiveData<List<Product>>
    abstract val isLastPage: LiveData<Boolean>

    abstract fun currentPage(): Int

    abstract fun nextPage(): Int

    abstract fun previousPage(): Int

    abstract fun postPage(page: Int)

    abstract fun postItemsInCurrentPage(items: List<Product>)

    abstract fun postIsLastPage(isLastPage: Boolean)
}

data class DefaultShoppingCartUiState(
    override val page: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
    override val itemsInCurrentPage: MutableLiveData<List<Product>> = MutableLiveData(emptyList()),
    override val isLastPage: MutableLiveData<Boolean> = MutableLiveData(false),
) : ShoppingCartUiState() {
    override fun currentPage(): Int = page.value ?: throw IllegalStateException("currentPage is null")

    override fun nextPage(): Int {
        if (isLastPage.value == true) throw IllegalStateException("마지막 페이지입니다.")

        page.value = page.value?.plus(PAGE_MOVE_COUNT)
        return page.value ?: throw IllegalStateException("currentPage is null")
    }

    override fun previousPage(): Int {
        if (page.value == FIRST_PAGE) throw IllegalStateException("첫 페이지입니다.")

        page.value = page.value?.minus(PAGE_MOVE_COUNT)
        return page.value ?: throw IllegalStateException("currentPage is null")
    }

    override fun postPage(page: Int) {
        this.page.postValue(page)
    }

    override fun postItemsInCurrentPage(items: List<Product>) {
        this.itemsInCurrentPage.postValue(items)
    }

    override fun postIsLastPage(isLastPage: Boolean) {
        this.isLastPage.postValue(isLastPage)
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
    }
}