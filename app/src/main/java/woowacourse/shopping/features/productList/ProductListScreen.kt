package woowacourse.shopping.features.productList

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.features.constant.Format.formatPrice
import woowacourse.shopping.features.constant.ShoppingColor.APP_BAR_COLOR

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = viewModel(),
    onCartClick: () -> Unit,
    onProductClick: (ProductUiModel) -> Unit,
    loadProducts: () -> Unit,
    onAddCartClick: (ProductUiModel) -> Unit,
    isExistProductToCart: (ProductUiModel) -> Boolean,
    onDecrementClick: (ProductUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier,
    ) {
        ProductListTopAppBar(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            totalCartItemsCount = uiState.totalCartItemsCount,
            onClick = onCartClick,
        )

        ProductCardGrid(
            products = uiState.productList,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            onProductClick = { onProductClick(it) },
            isExistProductToCart = { isExistProductToCart(it) },
            onDecrementClick = { onDecrementClick(it) },
            onMoreClick = {
                loadProducts()
            },
            onAddCartClick = {
                onAddCartClick(it)
            },
            isLastPage = uiState.isLastPage,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListTopAppBar(
    totalCartItemsCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Shopping",
                fontSize = 20.sp,
            )
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onClick,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = "장바구니 아이콘",
                    )
                }
                Box(
                    modifier =
                        Modifier
                            .padding(end = 16.dp)
                            .clip(CircleShape)
                            .size(24.dp)
                            .background(Color.Green, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(totalCartItemsCount.toString())
                }
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color(APP_BAR_COLOR),
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
            ),
        windowInsets = WindowInsets(0, 0, 0, 0),
    )
}

@Composable
private fun ProductCardGrid(
    products: List<ProductUiModel>,
    isLastPage: Boolean,
    onProductClick: (ProductUiModel) -> Unit,
    onMoreClick: () -> Unit,
    onAddCartClick: (ProductUiModel) -> Unit,
    isExistProductToCart: (ProductUiModel) -> Boolean,
    onDecrementClick: (ProductUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = products,
            key = { item -> item.id },
        ) { item ->
            ProductCard(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = item.imageUrl,
                productName = item.name,
                price = item.price,
                quantity = item.quantity,
                isExistProductToCart = isExistProductToCart(item),
                onDecrementClick = {
                    onDecrementClick(item)
                },
                onAddCartClick = {
                    onAddCartClick(item)
                },
                onClick = {
                    onProductClick(item)
                },
            )
        }
        if (!isLastPage) {
            item(
                span = { GridItemSpan(2) },
            ) {
                MoreButton(
                    onClick = { onMoreClick() },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 20.dp)
                            .background(
                                color = Color(0xFF555555),
                                shape = RoundedCornerShape(size = 45.dp),
                            ),
                )
            }
        }
    }
}

@Composable
private fun ProductCard(
    productName: String,
    price: Int,
    imageUrl: String,
    quantity: Int,
    onClick: () -> Unit,
    onAddCartClick: () -> Unit,
    onDecrementClick: () -> Unit,
    isExistProductToCart: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier.clickable {
                onClick()
            },
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "상품 이미지",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(154.dp),
                contentScale = ContentScale.Crop,
            )
            if (isExistProductToCart) {
                Row(
                    modifier =
                        Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
                            .background(color = Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    IconButton(
                        onClick = onDecrementClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "개수 감소 버튼",
                        )
                    }
                    Text(
                        text = quantity.toString(),
                        fontSize = 22.sp,
                    )
                    IconButton(
                        onClick = onAddCartClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "개수 증가 버튼",
                        )
                    }
                }
            } else {
                Box(
                    modifier =
                        Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(color = Color.White)
                            .size(48.dp),
                ) {
                    IconButton(
                        onClick = onAddCartClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "장바구니 추가 버튼",
                        )
                    }
                }
            }
        }
        ProductInfoColumn(
            modifier =
                Modifier
                    .padding(start = 6.dp, end = 9.dp, top = 8.dp, bottom = 12.dp),
            productName = productName,
            price = price,
        )
    }
}

@Composable
private fun ProductInfoColumn(
    productName: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            productName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            formatPrice(price),
            fontSize = 16.sp,
            color = Color.Gray,
        )
    }
}

@Composable
private fun MoreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text("더보기")
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    ProductListScreen(
        onCartClick = {},
        loadProducts = {},
        onProductClick = {},
        onAddCartClick = {},
        isExistProductToCart = { false },
        onDecrementClick = {},
    )
}
