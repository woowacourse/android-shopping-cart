package woowacourse.shopping.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.ui.state.ProductUiModel

@Composable
fun CartScreen(
    cartItems: List<ProductUiModel>,
    onCloseClick: () -> Unit,
    onDelete: (String) -> Unit,
    page: Int,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        containerColor = Color.White,
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CartAppBar(onCloseClick = onCloseClick)
        },
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            CartItemList(
                cartItems,
                modifier = Modifier.weight(1f),
                onDelete = onDelete,
            )
            Spacer(modifier = Modifier.height(40.dp))
            PageNavigator(
                page = page,
                onLeftClick = onLeftClick,
                onRightClick = onRightClick,
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun PageNavigator(
    page: Int,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .height(height = 42.dp)
            .clip(RoundedCornerShape(4.dp)),
    ) {
        PageButton(
            text = "<",
            onClick = onLeftClick,
            isEnable = false,
        )

        Text(
            text = page.toString(),
            fontSize = 22.sp,
        )

        PageButton(
            text = ">",
            onClick = onRightClick,
            isEnable = true,
        )
    }
}

@Composable
private fun PageButton(
    text: String,
    onClick: () -> Unit,
    isEnable: Boolean,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        enabled = isEnable,
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff04c09e),
            contentColor = Color.White,
            disabledContainerColor = Color(0xffaaaaaa),
            disabledContentColor = Color.White,
        ),
        modifier = modifier.size(height = 42.dp, width = 42.dp),
        contentPadding = PaddingValues(10.dp),
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
        )
    }
}

@Composable
private fun CartItemList(
    cartItems: List<ProductUiModel>,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(
            key = { it.id },
            items = cartItems,
        ) {
            SingleCartItem(
                imageUrl = it.imageUrl,
                title = it.title,
                price = it.price,
                onDelete = {
                    onDelete(it.id)
                },
            )
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        cartItems = emptyList(),
        onCloseClick = {},
        onDelete = {},
        onLeftClick = {},
        onRightClick = {},
        page = 0,
    )
}
