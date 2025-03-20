package com.mohit.numeriq.ui.screen

import net.objecthunter.exp4j.ExpressionBuilder
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.numeriq.ui.CalculatorButton
import net.objecthunter.exp4j.operator.Operator
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@Composable
fun CalculatorScreen(modifier: Modifier) {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val themeColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    val transformedColor = textColor.copy(alpha = 0.7f)


    val expressionScrollState = rememberScrollState()
    val resultScrollState = rememberScrollState()

    LaunchedEffect(expression) {
        expressionScrollState.scrollTo(expressionScrollState.maxValue) // Scroll to end
    }
    LaunchedEffect(result) {
        resultScrollState.scrollTo(resultScrollState.maxValue) // Scroll to end
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(themeColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Display Area (Fixed Height)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp) // Fixed height for display area
                .background(themeColor)
                .padding(8.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(expressionScrollState), // Enable right scrolling
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = expression,
                        style = TextStyle(
                            fontSize = if (expression.length > 10) 28.sp else 36.sp, // Adjust size dynamically
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            fontFamily = FontFamily.Monospace
                        ),
                        maxLines = 1, // No wrapping
                        textAlign = androidx.compose.ui.text.style.TextAlign.End // Align text to right
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(resultScrollState), // Enable right scrolling
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = result,
                        style = TextStyle(
                            fontSize = if (result.length > 10) 42.sp else 50.sp, // Adjust size dynamically
                            fontWeight = FontWeight.Bold,
                            color = transformedColor,
                            fontFamily = FontFamily.Monospace
                        ),
                        maxLines = 1, // No wrapping
                        textAlign = androidx.compose.ui.text.style.TextAlign.End // Align text to right
                    )
                }
            }
        }

        // Buttons Grid
        val buttons = listOf(
            listOf("C", "⌫", "%", "/"),
            listOf("7", "8", "9", "x"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "=", "")
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->
                        if (label.isNotEmpty()) {
                            CalculatorButton(
                                label = label,
                                onClick = {
                                    when (label) {
                                        "=" -> result = evaluateExpression(expression)
                                        "C" -> {
                                            expression = ""
                                            result = "0"
                                        }
                                        "⌫" -> expression = expression.dropLast(1)
                                        else -> expression += label
                                    }
                                },
                                modifier = Modifier
                                    .size(85.dp)
                                    .padding(3.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.size(80.dp))
                        }
                    }
                }
            }
        }
    }
}


fun evaluateExpression(expression: String): String {
    return try {
        // Replace 'x' with '*' for multiplication
        val formattedExpression = expression.replace("x", "*")

        // Define a custom modulus operator (%)
        val modOperator = object : Operator("%", 2, true, Operator.PRECEDENCE_MODULO) {
            override fun apply(vararg args: Double): Double {
                return args[0] % args[1]
            }
        }

        // Build and evaluate the expression with the modulus operator
        val result = ExpressionBuilder(formattedExpression)
            .operator(modOperator) // Add custom % operator
            .build()
            .evaluate()

        // Convert result to BigDecimal for precise formatting
        val bigDecimalResult = BigDecimal(result, MathContext.DECIMAL128)

        // Format the result to avoid unnecessary decimals
        if (bigDecimalResult.stripTrailingZeros().scale() <= 0) {
            bigDecimalResult.toBigInteger().toString() // Remove decimals if not needed
        } else {
            bigDecimalResult.setScale(5, RoundingMode.HALF_UP).toString() // Keep 5 decimal places
        }
    } catch (e: Exception) {
        "Error"
    }
}