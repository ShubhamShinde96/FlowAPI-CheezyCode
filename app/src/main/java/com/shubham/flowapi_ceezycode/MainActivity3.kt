package com.shubham.flowapi_ceezycode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity3 : AppCompatActivity() {

    // 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        // Consuming

        val job = GlobalScope.launch {
            val data: Flow<Int> = producer() // press alt + enter and select "Specify type explicitly - val data: Flow<Int>
            data.collect {
                Log.d("CHEEZYFLOWS", it.toString())
            }
            // Now try commenting above collect consumer, you will get to know below "emit" log will not be printed
            // that's because if there's no consumer then producer will not waste resources by producing the data.
        }

        GlobalScope.launch {

            delay(3500)
            job.cancel()  // to check how to cancel a flow? or a producer
        }
    }


    // to build flow we have a top level function called "flow", like below, and we have to pass it type
    fun producer() = flow<Int> {
        // so inside this lambda we need to write logic of how our items will be produced.

        // by default flows create us CoroutineScope so we can call suspending functions inside it, like below delay and emit

        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        list.forEach {
            delay(1000) // to mimic API
            emit(it) // to produce value/to pass value inside stream: we'll emit it from here.
            Log.d("CHEEZYFLOWS", "emit")
        }
    }

    // so after running as you can see that we're getting each element one by one after 1 second delay
    // so basically we have created a stream of integers, and we're sending those integers into pipe and consuming them.


    // How to cancel a flow?
    // we don't have any method like cancel() to cancel the flow.
    // Actually the logic is simple, if there will be no consumer, flow will automatically stop producing.
    // as we're using coroutines, and coroutines have structured concurrency, if we cancel coroutine of collect
    // consumer then consumer will also gets destroyed and eventually because there will be no consumer so producer
    // will stop producing.


}