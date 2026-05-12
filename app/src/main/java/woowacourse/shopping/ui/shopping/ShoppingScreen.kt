package woowacourse.shopping.ui.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import woowacourse.shopping.ui.component.ShoppingAppBar
import woowacourse.shopping.ui.model.ProductUiModel
import woowacourse.shopping.ui.theme.Green40

@Composable
fun ShoppingScreen(
    uiState: ShoppingUiState,
    onLoad: () -> Unit,
    onProductClick: (String) -> Unit,
    onCartClick: () -> Unit,
    onQuantityChange: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Text(
                        text = "Shopping",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "쇼핑 카트",
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(24.dp)
                                .clickable {
                                    onCartClick()
                                },
                    )
                    if (uiState.cartSize > 0) {
                        Box(
                            modifier =
                                Modifier
                                    .size(24.dp)
                                    .background(Green40, shape = CircleShape),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = uiState.cartSize.toString(),
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.W500,
                            )
                        }
                    }
                },
                modifier = modifier.fillMaxWidth(),
            )
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        if (uiState.isNetworkAvailable) {
            ShoppingContents(
                products = uiState.products,
                recentItems = uiState.recentItems,
                cartQuantities = uiState.cartQuantities,
                modifier = Modifier.padding(innerPadding),
                onLoad = onLoad,
                onProductClick = onProductClick,
                onQuantityChange = onQuantityChange,
                isCanLoadMore = uiState.canLoadMore,
            )
        } else {
            NetworkErrorContent(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
            )
        }
    }
}

@Composable
private fun ShoppingContents(
    products: ImmutableList<ProductUiModel>,
    recentItems: ImmutableList<ProductUiModel>,
    cartQuantities: Map<String, Int>,
    onLoad: () -> Unit,
    isCanLoadMore: Boolean,
    onProductClick: (String) -> Unit,
    onQuantityChange: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (recentItems.isNotEmpty()) {
            RecentItemsSection(
                recentItems = recentItems,
                onProductClick = onProductClick,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp),
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp),
        ) {
            items(
                items = products,
                key = { product -> product.id },
            ) { product ->
                ProductCard(
                    imageUrl = product.imageUrl,
                    productName = product.name,
                    price = product.price,
                    quantity = cartQuantities[product.id] ?: 0,
                    onClick = {
                        onProductClick(product.id)
                    },
                    onQuantityChange = { quantity ->
                        onQuantityChange(product.id, quantity)
                    },
                )
            }
            if (isCanLoadMore) {
                item(
                    span = { GridItemSpan(2) },
                ) {
                    LoadButton(
                        onClick = onLoad,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShoppingScreenPreview() {
    ShoppingScreen(
        uiState = ShoppingUiState(),
        onLoad = {},
        onProductClick = {},
        onCartClick = {},
        onQuantityChange = { _, _ -> },
    )
}
