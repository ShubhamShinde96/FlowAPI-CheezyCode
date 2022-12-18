package com.shubham.flowapi_ceezycode.SharedFlow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shubham.flowapi_ceezycode.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SharedFlowActivity1 : AppCompatActivity() {

    // 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_flow1)

        // So far what we've learned, we know the flows are cold natured, means if there's not any subscriber then
        // they will not produce any data.
        // And we also learned that there can be multiple consumers and every consumer will gets its own unique
        // object of flow and it will start from the very start even if it has started collecting late than others.
        // In simple words: Every consumer will get a independent flow object.

        // Shared Flow: Here we don't want consumer to get independent flow object, consumers should share single
        // object of flow, we want multiple consumers to share single flow object from producer.
        // Because of this we call shared flow as "Hot Flow".
        // Means that it will produce the data and if there are multiple consumers then all consumers will get
        // same data.
        // Ex: it is like Movie Theatre, everyone will see the same picture, doesn't matter who came on time or who came
        // late.
        // If consumer joins late then it will lose on previous data.

        GlobalScope.launch(Dispatchers.Main) {

            val result = producer()
            result.collect {
                Log.d("CHEEZYFLOWS", "Item1: $it")
            }
        }

        GlobalScope.launch(Dispatchers.Main) {

            val result = producer()
            delay(2500) // Added it to check what happens if this consumer starts consuming late
            result.collect {
                Log.d("CHEEZYFLOWS", "Item2: $it")
            }
        }

        // Now as you can see from the logs, because our second consumer is joining 2.5 seconds late, it is not
        // receiving earlier values, it is receiving values from when it has joined.


    }

    //    private fun producer() = flow<Int> {
    private fun producer(): Flow<Int> {  // even if we're returning "MutableSharedFlow" we are still mentioned "Flow" here
        // it is like when we mention mutable live data into view-model and keep it private to view-model only
        // and then expose live-data for outside classes(Activity/fragments)
        // The same way we'll create the object of MutableSharedFlow and only expose object of SharedFlow or Flow
        // so that no one else can emit the values without consent


        /*val list  = listOf(1, 2, 3, 4, 5)

            list.forEach {
                delay(1000)
                emit(it)
            }
        }*/

        // Implementing shared flow
        // We have 2 classes: 1] MutableSharedFlow  2] SharedFlow

        // now because we're not using "flow<T> { }" block, we need to use coruotine scope otherwise we will not
        // be able to launch any suspend functions like "emit".

//        val mutableSharedFow = MutableSharedFlow<Int>()
        val mutableSharedFow = MutableSharedFlow<Int>(2) // passing replay value here
        // the default value is 0, if any consumer joins late, then the values of capacity how much we had passed
        // as replay will send them again for consumers, for ex: if we pass 2 as reply value and even if any consumer
        // joins late then he'll get 2 previous values from the point he joined.

        GlobalScope.launch {

            val list = listOf(1, 2, 3, 4, 5)

            list.forEach {
                mutableSharedFow.emit(it)
                delay(1000)
            }
        }

        // In the "SharedFlow" we also have the property of "replay".
        // If we want to store some items, means kinda like a buffer so that if any consumer joins late then
        // he will get "some" old values which had stored

        return mutableSharedFow
    }

}