package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculadoraApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraApp() {
    Calculadora(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}

//Unit: usado para uma função de nada de void
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun entradaNumerica(valuePassado: String,
                    onValuechangePassado: (String) -> Unit,
                    labelPassado: String){

    OutlinedTextField(
        value = valuePassado,
        onValueChange = onValuechangePassado,
        label = { Text(text = labelPassado) },
        modifier = Modifier.width(120.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )    
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun botaoOperacao(onClikPassado: () -> Unit, textobotao: String){
    ElevatedButton(onClick = onClikPassado) {
        Text(text = textobotao, fontSize = 32.sp)}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculadora(modifier: Modifier = Modifier) {
    var numero1 by remember { mutableStateOf("") }
    var numero2 by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("Resultado") }

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.width(350.dp)
        ) {
            //it?:
            entradaNumerica(
                valuePassado = numero1,
                onValuechangePassado = {numero1 = it },
                labelPassado = "Número 1"
            )

            entradaNumerica(
                valuePassado = numero2,
                onValuechangePassado = {numero2 = it },
                labelPassado = "Número 2"
            )

            OutlinedTextField(
                value = numero2,
                onValueChange = {numero2 = it},
                label = { Text(text = "Número 2") },
                modifier = Modifier.width(120.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
        }
        Row(
            modifier = Modifier
                .width(350.dp)
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            botaoOperacao(
                onClikPassado = {
                    resultado = calcularResultado(numero1, numero2, "a") },
                Text(text = " + ", fontSize = 32.sp)//CORRIGIR
            }
            ElevatedButton(onClick = {
                resultado = calcularResultado(numero1, numero2, "a")
            }) {
                Text(text = " + ", fontSize = 32.sp)
            }
            ElevatedButton(onClick = {
                resultado = calcularResultado(numero1, numero2, "s")
            }) {
                Text(text = " - ", fontSize = 32.sp)
            }
            ElevatedButton(onClick = {
                resultado = calcularResultado(numero1, numero2, "m")
            }) {
                Text(text = " × ", fontSize = 32.sp)
            }
            ElevatedButton(onClick = {
                resultado = calcularResultado(numero1, numero2, "d")
            }) {
                Text(text = " ÷ ", fontSize = 32.sp)
            }
        }
        Text(
            text = "$resultado",
            fontSize = 32.sp,
            modifier = Modifier
                .width(350.dp)
                .padding(top = 20.dp),
            textAlign = TextAlign.Center
        )
    }
}

fun calcularResultado(numero1: String, numero2: String, operacao: String): String {
    if (numero1.trim().isEmpty() || numero2.trim().isEmpty()) {
        return "Forneça os dois números"
    }

    if (numero2 == "0" && operacao == "d") {
        return "Divisão por zero"
    }

    val num1 = numero1.trim().toDouble()
    val num2 = numero2.trim().toDouble()
    var resultado = ""
    return when (operacao) {
        "a" ->(num1 + num2).toString()
        "s" ->(num1 - num2).toString()
        "m" ->(num1 * num2).toString()
        else  ->(num1 / num2).toString()
    }
}