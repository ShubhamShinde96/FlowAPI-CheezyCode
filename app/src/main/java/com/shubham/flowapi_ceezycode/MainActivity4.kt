package com.shubham.flowapi_ceezycode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity4 : AppCompatActivity() {

    // 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)


        GlobalScope.launch {
            val data: Flow<Int> = producer()
            data.collect {
                Log.d("CHEEZYFLOWS", "- 1: $it")
            }
        }

        GlobalScope.launch {
            val data: Flow<Int> = producer()
            delay(2500) // comment or uncomment this
            // as you can see if we add delay here, that means this consumer is going to collect late, so
            // will the initial data can be provided here? and the answer is yes, all data will be provided to
            // this consumer.
            // just like Netflix/OTT you can watch any content any time, you are consumer, provider will provide
            // you the data whenever you want to consume
            // once produced data will not be lost. Every flow is independent.
            // in hot streams vice versa will happen, unless there is any kind of buffering implemented.
            data.collect {
                Log.d("CHEEZYFLOWS", "- 2: $it")
            }
        }

        // as you can see from logs, there can be multiple consumers of flow

    }


    fun producer() = flow<Int> {

        val list = listOf(1, 2, 3, 4, 5)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }


    // when consumer joins at any point of time, it will get data from start, until end.

}