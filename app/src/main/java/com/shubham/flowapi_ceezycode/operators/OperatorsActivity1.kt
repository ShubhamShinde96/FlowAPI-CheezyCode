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

class OperatorsActivity1 : AppCompatActivity() {

    // 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators1)

        GlobalScope.launch(Dispatchers.Main) {
            val data: Flow<Int> = producer().onStart {

                // if we want to emit any additional value before it start emitting then we can do that here
                emit(-1) //like this, although remember this is just additional value
                Log.d("CHEEZYFLOWS", "Starting consuming, ie collect")
            }.onCompletion {

                // like we can emit additional value in onStart(), we can also emit extra values after flow is
                // completed, over here inside onCompletion
                emit(6)
                Log.d("CHEEZYFLOWS", "Completed consuming, ie collect")
            }.onEach {
                Log.d("CHEEZYFLOWS", "About to emit value: $it")
            }

            data.collect {
                Log.d("CHEEZYFLOWS", "- 1: $it")
            }
        }

        // In kotlin flows, there are many events, like we want to use an event like: we want an event when the
        // flow is started and we want to execute some code at that time.
        // Similarly we also have an event when data consumption is completed.
    }


    fun producer() = flow<Int> {
        val list = listOf(1, 2, 3, 4, 5)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }

}