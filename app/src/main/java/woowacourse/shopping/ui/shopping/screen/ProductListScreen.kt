package woowacourse.shopping.ui.shopping.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductWithQuantity
import woowacourse.shopping.domain.Products
import woowacourse.shopping.network.NetworkMonitor
import woowacourse.shopping.repository.cart.InMemoryCartRepository
import woowacourse.shopping.repository.product.InMemoryProductRepository
import woowacourse.shopping.repository.recentviewedproduct.RecentlyViewedProductsRepository
import woowacourse.shopping.ui.productdetail.component.MintButton
import woowacourse.shopping.ui.shopping.component.ProductItem
import woowacourse.shopping.ui.shopping.component.ProductListTopAppBar
import woowacourse.shopping.ui.shopping.component.RecentlyViewedProductsItemsBox
import woowacourse.shopping.ui.shopping.viewmodel.ProductListViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel,
    onCartClick: () -> Unit,
    onProductClick: (Uuid) -> Unit,
) {
    Scaffold(
        topBar = {
            ProductListTopAppBar(
                totalProductQuantity = viewModel.totalProductQuantity,
                onClick = {
                    onCartClick()
                },
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier =
                modifier
                    .padding(innerPadding)
                    .testTag("product_grid"),
        ) {
            if (!viewModel.isOnline) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFFE3E3))
                                .padding(horizontal = 20.dp, vertical = 12.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.network_unavailable),
                            color = Color(0xFFB3261E),
                            fontWeight = FontWeight.W600,
                        )
                    }
                }
            }
            if (viewModel.recentlyViewedProducts.products.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    RecentlyViewedProductsItemsBox(
                        viewModel = viewModel,
                        onClick = onProductClick,
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    HorizontalDivider(
                        thickness = 5.dp,
                        color = Color.LightGray,
                    )
                }
            }
            itemsIndexed(viewModel.visibleProducts()) { index, product ->
                val quantity = viewModel.getProductQuantity(product.productId)

                ProductItem(
                    productWithQuantity = ProductWithQuantity(product, quantity),
                    onClick = { onProductClick(product.productId) },
                    onIncrease = {
                        viewModel.addProduct(
                            product = product,
                            quantityToAdd = 1,
                        )
                    },
                    onDecrease = {
                        viewModel.decreaseProduct(
                            productId = product.productId,
                            quantityToRemove = 1,
                        )
                    },
                    modifier =
                        Modifier
                            .padding(
                                start = if (index % 2 == 0) 20.dp else 0.dp,
                                end = if (index % 2 == 1) 20.dp else 0.dp,
                            ).testTag("product_item_${product.productId}"),
                )
            }
            if (viewModel.products.hasNextPage(currentPageIndex = viewModel.currentPageIndex)) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    MintButton(
                        onClick = { viewModel.increasePageIndex() },
                        text = stringResource(R.string.see_more),
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductListScreenPreview() {
    val packageName = LocalContext.current.packageName
    val allProducts = ProductFixture.productList(packageName)

    val recentViewedProductsRepository =
        remember {
            object : RecentlyViewedProductsRepository {
                override suspend fun saveViewedProduct(productId: Uuid) = Unit

                override fun getRecentlyViewedProducts(): Flow<Products> = flowOf(Products(allProducts.take(5)))

                override suspend fun getLastViewedProduct(): Product? = allProducts.firstOrNull()
            }
        }
    val networkMonitor =
        remember {
            object : NetworkMonitor {
                override fun isOnline(): Flow<Boolean> = flowOf(true)
            }
        }

    val viewModel =
        remember {
            ProductListViewModel(
                recentViewedProductsRepository = recentViewedProductsRepository,
                productRepository = InMemoryProductRepository(packageName),
                cartRepository = InMemoryCartRepository(),
                networkMonitor = networkMonitor,
            )
        }

    ProductListScreen(
        viewModel = viewModel,
        onCartClick = {},
        onProductClick = {},
    )
}
