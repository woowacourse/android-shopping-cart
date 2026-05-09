@file:Suppress("FunctionName")

package woowacourse.shopping.productlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.WonMoney
import woowacourse.shopping.ui.component.MoreButton
import woowacourse.shopping.ui.component.ProductItem
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@Composable
fun ProductListScreen(
    onNavigateToCartClick: () -> Unit,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    productListViewModel: ProductListViewModel =
        viewModel(
            factory =
                ProductListViewModel.factory(
                    LocalContext.current.applicationContext as ShoppingApplication,
                ),
        ),
) {
    val uiState by productListViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        productListViewModel.loadProducts()
    }

    ProductListContent(
        productUiModels = uiState.productUiModels,
        enableMoreButton = uiState.enableMoreButton,
        onNavigateToCartClick = onNavigateToCartClick,
        onProductClick = onProductClick,
        loadProducts = productListViewModel::loadProducts,
        modifier = modifier,
    )
}

@Composable
fun ProductListContent(
    productUiModels: List<ProductUiModel>,
    enableMoreButton: Boolean,
    onNavigateToCartClick: () -> Unit,
    onProductClick: (String) -> Unit,
    loadProducts: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            ProductListTopBar(
                onNavigateToCartClick = onNavigateToCartClick,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = innerPadding,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp),
        ) {
            items(
                items = productUiModels,
                key = { it.id },
            ) { product ->
                ProductItem(
                    title = product.name,
                    price = product.price,
                    imageUrl = product.imageUrl,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable { onProductClick(product.id) },
                )
            }

            if (enableMoreButton) {
                item(
                    span = { GridItemSpan(maxLineSpan) },
                ) {
                    MoreButton(onClick = loadProducts)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListTopBar(
    onNavigateToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = onNavigateToCartClick) {
                Image(
                    painter = painterResource(R.drawable.shopping_cart_icon),
                    contentDescription = stringResource(R.string.cart_icon_description),
                    modifier = Modifier.size(22.dp),
                )
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
private fun ProductItemPreview() {
    ProductItem(
        title = "동원 스위트콘",
        price = WonMoney(99_800),
        imageUrl = "https://img.dongwonmall.com/dwmall/static_root/model_img/main/153/15327_1_a.jpg?f=webp&q=80",
    )
}

@Composable
@Preview(showBackground = true)
private fun ProductListContentPreview() {
    AndroidShoppingTheme {
        ProductListContent(
            onProductClick = {},
            onNavigateToCartClick = {},
            productUiModels =
                listOf(
                    ProductUiModel(
                        id = "1",
                        name = "암까라 메시",
                        price = WonMoney(1_000000000),
                        imageUrl = "",
                    ),
                ),
            enableMoreButton = true,
            loadProducts = { },
        )
    }
}
