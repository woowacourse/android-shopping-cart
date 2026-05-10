@file:Suppress("FunctionName")

package woowacourse.shopping.productlist

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.productdetail.DetailProductActivity
import woowacourse.shopping.productlist.ProductListActivity.Companion.CHANGED_PRODUCT_IDS
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.ui.WonMoney
import woowacourse.shopping.ui.component.MoreButton
import woowacourse.shopping.ui.component.NumberCounter
import woowacourse.shopping.ui.component.ProductImage
import woowacourse.shopping.ui.component.ProductItem
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@Composable
fun ProductListScreen(
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

    val refreshChangedProducts: (Intent?) -> Unit = { data ->
        val productIds =
            data?.getStringArrayListExtra(CHANGED_PRODUCT_IDS)
                ?: emptyList()
        productListViewModel.refreshProducts(productIds)
    }

    val context = LocalContext.current
    val productDetailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@rememberLauncherForActivityResult
        refreshChangedProducts(result.data)
    }

    val shoppingCartLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@rememberLauncherForActivityResult
        refreshChangedProducts(result.data)
    }

    ProductListContent(
        productUiModels = uiState.productUiModels,
        cartItemCount = uiState.cartItemCount,
        enableMoreButton = uiState.enableMoreButton,
        onNavigateToCartClick = {
            shoppingCartLauncher.launch(Intent(context, ShoppingCartActivity::class.java))
        },
        onProductClick = {
            productDetailLauncher.launch(
                Intent(context, DetailProductActivity::class.java)
                    .putExtra(ProductListActivity.EXTRA_PRODUCT_ID, it),
            )
        },
        onIncrementQuantity = productListViewModel::increaseItemQuantity,
        onDecrementQuantity = productListViewModel::decreaseItemQuantity,
        loadProducts = productListViewModel::loadProducts,
        modifier = modifier,
    )
}

@Composable
fun ProductListContent(
    productUiModels: List<ProductUiModel>,
    cartItemCount: Int,
    enableMoreButton: Boolean,
    onNavigateToCartClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onIncrementQuantity: (String) -> Unit,
    onDecrementQuantity: (String) -> Unit,
    loadProducts: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            ProductListTopBar(
                cartItemCount,
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
                    price = product.price.display(),
                    productImage = {
                        ProductImage(
                            imageUrl = product.imageUrl,
                            contentDescription = product.name,
                            bottomHoverContent = {
                                if (product.quantity > 0) {
                                    NumberCounter(
                                        count = product.quantity,
                                        onIncrement = { onIncrementQuantity(product.id) },
                                        onDecrement = { onDecrementQuantity(product.id) },
                                        modifier = Modifier
                                            .height(40.dp)
                                            .padding(horizontal = 20.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color.White)
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.background)
                                            .clickable { onIncrementQuantity(product.id) },
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "+",
                                            color = Color.Black,
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                    }
                                }
                            }
                        )
                    },
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
    cartItemCount: Int,
    onNavigateToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            Row(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { onNavigateToCartClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.shopping_cart_icon),
                    contentDescription = stringResource(R.string.cart_icon_description),
                    modifier = Modifier.size(28.dp),
                )

                if (cartItemCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = cartItemCount.toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
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
private fun ProductListContentPreview() {
    AndroidShoppingTheme {
        ProductListContent(
            onProductClick = {},
            onNavigateToCartClick = {},
            onIncrementQuantity = {},
            onDecrementQuantity = {},
            productUiModels =
                listOf(
                    ProductUiModel(
                        id = "1",
                        name = "암까라 메시",
                        price = WonMoney(1_000000000),
                        imageUrl = "",
                        quantity = 0,
                    ),
                ),
            enableMoreButton = true,
            loadProducts = { },
            cartItemCount = 20,
        )
    }
}
