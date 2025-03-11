package com.titin.deepseek

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}

@Composable
fun CalculatorScreen() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Input fields
        TextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("Primer número") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("Segundo número") },
            modifier = Modifier.fillMaxWidth()
        )

        // Operation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            operations.forEach { operation ->
                Button(
                    onClick = {
                        result = calculateResult(number1, number2, operation)
                    }
                ) {
                    Text(operation.symbol)
                }
            }
        }

        // Result
        if (result.isNotEmpty()) {
            Text(
                text = "Resultado: $result",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

private data class Operation(val symbol: String, val operate: (Double, Double) -> Double)

private val operations = listOf(
    Operation("+") { a, b -> a + b },
    Operation("-") { a, b -> a - b },
    Operation("×") { a, b -> a * b },
    Operation("÷") { a, b -> a / b }
)

private fun calculateResult(num1: String, num2: String, operation: Operation): String {
    return try {
        val a = num1.toDouble()
        val b = num2.toDouble()

        if (operation.symbol == "÷" && b == 0.0) {
            "Error: División por cero"
        } else {
            operation.operate(a, b).toString()
        }
    } catch (e: NumberFormatException) {
        "Error: Números inválidos"
    }
}