package com.shubham.flowapi_ceezycode.FlowCoroutineContext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shubham.flowapi_ceezycode.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ExceptionHandlingActivity1 : AppCompatActivity() {

    // 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception_handling1)

        // Now exception can either come at producer side or on the consumer side

        GlobalScope.launch(Dispatchers.Main) {

            try {
                producer()
                    .collect {
                        Log.d("CHEEZYFLOWS", "$it, Collector Thread: ${Thread.currentThread().name}")
                        throw java.lang.Exception("Error in Collector")
                    }
            } catch (e: java.lang.Exception) {
                Log.d("CHEEZYFLOWS", e.message.toString())
                // Now if any exception comes at producer side, it will be catched here
                // So as you can see, if the error occurs in collector side then that also can be catched here
                // But how can you differentiate/segregate the errors - from producer side | from consumer side
                // So we also want to handle exception from producer at producer side, for this we can use "catch" at producer side
            }
        }
    }

    fun producer() = flow<Int> {
        val list = listOf(1, 2, 3, 4, 5)
        list.forEach {
            delay(1000)
            Log.d("CHEEZYFLOWS", "Emitter Thread: ${Thread.currentThread().name}")
            emit(it)
//            throw java.lang.Exception("Error in emitter") // Commented this throwing as going to learn how to handle exception if it occurs on collector side.
            // Throwing this error to mimic the error is occurred on the producer side.

            throw java.lang.Exception("Error in emitter")
        }
    }.catch {

        Log.d("CHEEZYFLOWS", "Emitter catch: ${it.message}")
        // Exception arose from producer will be catched here
        // There's one more benefit of this "catch" block: You can also define fallback elements
        // means if you want to emit something else if any error arise, then you can do that here

        // if any error arised on producer that will be catched here, and try-catch() from consumer side will not be invoked.

        emit(-1) // fallback element
    }
    // You can define multiple catch blocks, as we did with "flowOn"


}













