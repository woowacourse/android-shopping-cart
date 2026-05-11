package woowacourse.shopping.ui.screens.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.R
import woowacourse.shopping.ui.component.topbar.MainTopBar
import woowacourse.shopping.ui.model.UiRecentProduct

@Composable
fun ProductScreen(
    viewModel: ProductViewModel,
    onCartClick: () -> Unit,
    onProductClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val products = uiState.products

    Scaffold(
        topBar = {
            MainTopBar(
                title = "Shopping",
                cartProductCount = uiState.totalCartCount,
                onCartClick = onCartClick,
            )
        },
        modifier = Modifier.statusBarsPadding(),
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                RecentProductGroup(
                    products = uiState.recentProducts,
                    onClick = onProductClick,
                    modifier = Modifier.padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = 28.dp,
                    ),
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                HorizontalDivider(
                    thickness = 7.dp,
                    color = Color(0xFFEBEBEB),
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }

            itemsIndexed(
                items = products,
                key = { _, product -> product.id },
            ) { index, product ->
                val isEven = index % 2 == 0
                ProductCard(
                    imageUrl = product.imageUrl,
                    name = product.name,
                    price = product.price,
                    onClick = {
                        onProductClick(product.id)
                    },
                    cartQuantity = product.cartQuantity,
                    onPlusClick = { viewModel.plusCartCount(product.id) },
                    onMinusClick = { viewModel.minusCartCount(product.id) },
                    modifier = Modifier.padding(
                        start = if (isEven) 20.dp else 0.dp,
                        end = if (isEven) 0.dp else 20.dp,
                    ),
                )
            }

            if (uiState.hasNext) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.getProducts() },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_down),
                            contentDescription = "상품 더보기",
                            modifier = Modifier
                                .padding(12.dp)
                                .size(24.dp)
                                .align(Alignment.Center),
                            tint = Color(0xFF555555),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RecentProductGroup(
    products: List<UiRecentProduct>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "최근 본 상품",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = products,
                key = { it.id },
            ) {
                RecentProductCard(
                    imageUrl = it.imageUrl,
                    title = it.name,
                    onClick = { onClick(it.id) },
                    modifier = Modifier.width(98.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductScreenPreview() {
    ProductScreen(
        viewModel = viewModel(factory = ProductViewModel.Factory),
        onCartClick = { },
        onProductClick = { },
    )
}
