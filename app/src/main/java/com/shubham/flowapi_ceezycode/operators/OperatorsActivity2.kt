package com.shubham.flowapi_ceezycode.operators

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shubham.flowapi_ceezycode.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OperatorsActivity2 : AppCompatActivity() {

    // 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators2)

        // In flows we have 2 types of operators 1] Terminal 2] Non-Terminal

        // Terminal operators: Terminal operators start our flow, I mean the consumption of flow happens because of these terminal
        // operators, the "collect" function is also a terminal operator.

        GlobalScope.launch(Dispatchers.Main) {
            val data: Flow<Int> = producer()

            /*data.collect {
                Log.d("CHEEZYFLOWS", "- 1: $it")
            }*/

            /*val result = producer().first() // this terminal operator returns the first element
            Log.d("CHEEZYFLOWS", result.toString())*/

            val result = producer().toList() // converts the result into "list" form
            Log.d("CHEEZYFLOWS", result.toString())

            // These collect, first and toList are terminal operators
            // Check OperatorsActivity3 for non-terminal operators
        }

    }

    fun producer() = flow<Int> {
        val list = listOf(1, 2, 3, 4, 5)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }


}