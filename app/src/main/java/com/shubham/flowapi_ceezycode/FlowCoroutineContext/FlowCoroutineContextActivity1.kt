package com.shubham.flowapi_ceezycode.FlowCoroutineContext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shubham.flowapi_ceezycode.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FlowCoroutineContextActivity1 : AppCompatActivity() {

    // 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_coroutine_context1)

        GlobalScope.launch(Dispatchers.Main) {

            producer()
                .map {
                    delay(1000)
                    it * 2
                    Log.d("CHEEZYFLOWS", "Map Thread: ${Thread.currentThread().name}")
                    // Output: Map Thread - DefaultDispatcher-worker-1
                }
                .flowOn(Dispatchers.IO)  // This is how we can use multiple "flowOn", now code above this flowOn
                    // will execute on "IO" and code below this "flowOn" will execute on "Main(UI)" thread
                .filter {
                    delay(500)
                    Log.d("CHEEZYFLOWS", "Filter Thread: ${Thread.currentThread().name}")
                    // Output: Filter Thread - DefaultDispatcher-worker-1
                    it < 8
                }

                // Operations above flowOn will execute on "Dispatchers.IO Thread"

                //.flowOn(Dispatchers.IO)  // So basically this producer() will execute on "IO Thread"
                .flowOn(Dispatchers.Main)
                    // And this collect will be executed on "Main Thread" as we are launching this consumer
                    // under "GlobalScope.launch(Dispatchers.Main)

                .collect {
                Log.d("CHEEZYFLOWS", "Collector Thread: ${Thread.currentThread().name}")
                // Output: Collector Thread - main
            }
        }

        // So as you can see from logs, both emitter and collector are executing on the same thread: "Main Thread"
        // Case 1: On which coroutine context you call "collect" on the same coroutine context the "producer" will execute

        // Case 2: But what if I want to collect on "Main Thread" but produce on some other thread.

    }


    fun producer() = flow<Int> {

        // Case 2 Solution:

//        withContext(Dispatchers.IO) {  // commented after using flowOn

            // Now trying this way to produce on "IO Thread" will give you runtime error: "Flow invariant is violated".
            // That's because compiler is telling: you are consuming on "Main Thread" but producing on "IO Thread".

            // Actually flow preserve it's coroutine context, flow assumes that the context you are using to
            // collect/consume the flow on the same context you must be emitting/producing them.
            // But if you want to consume and produce on different contexts, in this case you'll have to explicitly
            // tell the flow about this.

            val list = listOf(1, 2, 3, 4, 5)
            list.forEach {
                delay(1000)
                Log.d("CHEEZYFLOWS", "Emitter Thread: ${Thread.currentThread().name}")
                // Output: Emitter Thread - DefaultDispatcher-worker-1
                emit(it)
            }
//        }
    }



}























