@file:Suppress("FunctionName")

package woowacourse.shopping.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingCardAddBox(
    onShoppingCartAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .clip(RoundedCornerShape(50.dp))
                .size(48.dp)
                .background(MaterialTheme.colorScheme.background),
    ) {
        Button(
            onClick = onShoppingCartAddClick,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "+",
            )
        }
    }
}

@Composable
@Preview()
private fun ShoppingCardAddBoxPreview() {
    ShoppingCardAddBox(
        onShoppingCartAddClick = {},
    )
}
