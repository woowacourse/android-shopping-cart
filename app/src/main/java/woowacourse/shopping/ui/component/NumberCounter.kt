@file:Suppress("FunctionName")

package woowacourse.shopping.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NumberCounter(
    count: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NumberCounterButton(
            text = "-",
            onClick = onDecrement,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = count.toString(),
            color = Color(0xFF555555),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )

        NumberCounterButton(
            text = "+",
            onClick = onIncrement,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun NumberCounterButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxHeight()
                .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = Color(0xFF555555),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun NumberCounterPreview() {
    NumberCounter(count = 1, onIncrement = {}, onDecrement = {})
}
