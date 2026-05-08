package woowacourse.shopping.features.cart

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName
import woowacourse.shopping.features.constant.Format.formatPrice
import woowacourse.shopping.features.constant.ShoppingColor.APP_BAR_COLOR
import woowacourse.shopping.features.constant.ShoppingColor.CART_PAGE_BUTTON_ACTIVE_COLOR
import woowacourse.shopping.features.constant.ShoppingColor.CART_PAGE_BUTTON_INACTIVE_COLOR
import woowacourse.shopping.features.generalComponent.QuantityControlRow

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    totalPages: Int,
    currentPage: Int,
    hasPrevious: Boolean,
    hasNext: Boolean,
    isMinusEnabled: (CartItem) -> Boolean,
    goToPreviousPage: () -> Unit,
    goToNextPage: () -> Unit,
    removeCartItem: (CartItem) -> Unit,
    increaseCartItem: (CartItem) -> Unit,
    decreaseCartItem: (CartItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        CartTopAppBar(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            onClick = {
                activity?.finish()
            },
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 4.dp),
        ) {
            items(cartItems) { cartItem ->
                CartItemCard(
                    modifier =
                        Modifier
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                    cartItemName = cartItem.product.name.value,
                    cartItemImageUrl = cartItem.product.imageUrl.value,
                    cartItemQuantity = cartItem.quantity.value,
                    cartItemPrice = cartItem.getCartItemTotalPrice(),
                    isMinusEnabled = {
                        isMinusEnabled(cartItem)
                    },
                    onRemoveClick = {
                        removeCartItem(cartItem)
                    },
                    onIncreaseClick = {
                        increaseCartItem(cartItem)
                    },
                    onDecreaseClick = {
                        decreaseCartItem(cartItem)
                    },
                )
            }
        }

        if (totalPages > 1) {
            PageNavigator(
                currentPage = currentPage,
                hasPrevious = hasPrevious,
                hasNext = hasNext,
                onPreviousClick = { goToPreviousPage() },
                onNextClick = { goToNextPage() },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartTopAppBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Cart",
                fontSize = 20.sp,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로 가기",
                    tint = Color.White,
                )
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
private fun CartItemCard(
    cartItemName: String,
    cartItemImageUrl: String,
    cartItemQuantity: Int,
    cartItemPrice: Int,
    isMinusEnabled: () -> Boolean,
    onRemoveClick: () -> Unit,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, Color(0xFFD0D0D0)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    text = cartItemName,
                    modifier = Modifier.weight(1f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A4A4A),
                )
                IconButton(
                    onClick = onRemoveClick,
                    modifier = Modifier.size(28.dp),
                ) {
                    Icon(
                        imageVector = Default.Close,
                        contentDescription = "삭제",
                        tint = Color(0xFFB0B0B0),
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                ProductImage(
                    imageUrl = cartItemImageUrl,
                    modifier = Modifier.size(width = 72.dp, height = 64.dp),
                )
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End,
                ) {
                    QuantityControlRow(
                        quantity = cartItemQuantity,
                        minusEnabled = isMinusEnabled(),
                        onIncrementClick = onIncreaseClick,
                        onDecrementClick = onDecreaseClick,
                    )
                    Text(
                        text = formatPrice(cartItemPrice),
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 4.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun PageNavigator(
    currentPage: Int,
    hasPrevious: Boolean,
    hasNext: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Button(
            onClick = onPreviousClick,
            enabled = hasPrevious,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(CART_PAGE_BUTTON_ACTIVE_COLOR),
                    disabledContainerColor = Color(CART_PAGE_BUTTON_INACTIVE_COLOR),
                ),
        ) {
            Text(
                text = "<",
                color = Color.White,
            )
        }
        Text(
            text = "${currentPage + 1}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
        Button(
            onClick = onNextClick,
            enabled = hasNext,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(CART_PAGE_BUTTON_ACTIVE_COLOR),
                    disabledContainerColor = Color(CART_PAGE_BUTTON_INACTIVE_COLOR),
                ),
        ) {
            Text(
                text = ">",
                color = Color.White,
            )
        }
    }
}

@Composable
private fun ProductImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "상품 이미지",
        modifier =
            modifier
                .background(Color(0xFFF1F1F1))
                .border(1.dp, Color(0xFFE4E4E4)),
    )
}

@Preview(showBackground = true)
@Composable
private fun CartScreenPreview() {
    CartScreen(
        cartItems =
            listOf(
                CartItem(
                    Product(
                        name = ProductName("우아한두유"),
                        price = Price(3000),
                        imageUrl = ImageUrl("https://velog.io"),
                    ),
                    quantity = CartItemQuantity(1),
                ),
            ),
        totalPages = 0,
        currentPage = 0,
        hasPrevious = false,
        hasNext = false,
        goToPreviousPage = {},
        goToNextPage = {},
        removeCartItem = {},
        increaseCartItem = {},
        decreaseCartItem = {},
        isMinusEnabled = { true },
    )
}
