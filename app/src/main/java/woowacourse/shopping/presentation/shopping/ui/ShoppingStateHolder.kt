package woowacourse.shopping.presentation.shopping.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.di.RepositoryProvider.productRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.common.model.toUiModel

class ShoppingStateHolder(
    private val productRepository: ProductRepository = RepositoryProvider.productRepository,
    initialProducts: List<ProductUiModel> = emptyList(),
    initialCanLoadMore: Boolean = true,
    initialOffset: Int = 0,
) {
    var products by mutableStateOf(initialProducts)
    var canLoadMore by mutableStateOf(initialCanLoadMore)
    var isLoading by mutableStateOf(false)
    private var offset = initialOffset
    private val pageSize = 20

    suspend fun initialize() {
        if (offset == 0) loadMore()
    }

    suspend fun loadMore() {
        if (isLoading || !canLoadMore) return
        isLoading = true
        try {
            val loadData = getProductData(offset, pageSize)
            products = products.plus(loadData)
            offset += loadData.size
            canLoadMore = loadData.size == pageSize
        } catch (e: Exception) {
            throw e
        } finally {
            isLoading = false
        }
    }

    private suspend fun getProductData(
        offset: Int,
        limit: Int,
    ): ImmutableList<ProductUiModel> = productRepository.getProducts(offset, limit).map { it.toUiModel() }.toImmutableList()

    companion object {
        fun Saver(productRepository: ProductRepository = RepositoryProvider.productRepository): Saver<ShoppingStateHolder, *> =
            listSaver(
                save = { holder ->
                    listOf(
                        holder.products,
                        holder.offset,
                        holder.canLoadMore,
                    )
                },
                restore = { savedList ->
                    @Suppress("UNCHECKED_CAST")
                    ShoppingStateHolder(
                        productRepository = productRepository,
                        initialProducts = savedList[0] as List<ProductUiModel>,
                        initialOffset = savedList[1] as Int,
                        initialCanLoadMore = savedList[2] as Boolean,
                    )
                },
            )
    }
}
