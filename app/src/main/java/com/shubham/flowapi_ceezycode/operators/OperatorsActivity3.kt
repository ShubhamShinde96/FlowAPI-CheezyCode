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

class OperatorsActivity3 : AppCompatActivity() {

    // 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators3)

        // In flows we have 2 types of operators 1] Terminal 2] Non-Terminal

        // Non-Terminal operators:

        GlobalScope.launch(Dispatchers.Main) {
            val data: Flow<Int> = producer()

            val result = producer().map {
                //  Using map operator you can convert the data from one format to other format, ex: Int to String
                //  or maybe you can perform some operation, like we're multiplying the value below
                it * 2

            } .filter {

                // as per the name, it filters the items coming though flow
                // you have to pass filtering criteria to it
                // so bacially filtering criteria means is you have to write a condition which will return boolean
                // on the basis of that boolean value "filter" will include or discard the items.

                it < 8  // only less than 8 values included, otherwise other data will not be consumed.
            }.collect{
                Log.d("CHEEZYFLOWS", it.toString())
            } // if we comment this collect terminal operator from here, then this flow will not start
            // it will not be consumed, as to start a flow we need a terminal operator

            // So here we're chaining the operators, like above, first of all "map" operator will execute and then
            // after that "filter" operator will execute.
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


























