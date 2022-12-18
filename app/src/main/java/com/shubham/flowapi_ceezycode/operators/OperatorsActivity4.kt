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
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class OperatorsActivity4 : AppCompatActivity() {

    // 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators4)


        // Non-Terminal operators:

        GlobalScope.launch(Dispatchers.Main) {

            getNotes().map {
                FormattedNote(it.id, it.isActive, it.title.uppercase(), it.description)
            }.filter {
                it.isActive
            }.collect() {
                Log.d("CHEEZYFLOWS", it.toString())
            }

            // So as you can see only active values are filtered and title is converted to uppercase

        }

        // Buffer operator
        GlobalScope.launch(Dispatchers.Main) {

            val time = measureTimeMillis {

                producer()
                    .buffer(3)  //if the items are producing faster than consumer, we can buffer them, so that consumer can easily consume them
                        // so here we have passed 3, so it will buffer maximum of 3 items if producer is producing faster than consumer
                        // there is not any rule to define the capacity, you can decide and define as per your requirement
                    .collect {
                    // lets suppose this collect operator/consumer is taking some extra time to process complex data
                    delay(1500) // This delay is to mimic the extra processing time
                    // so basically consumer is taking more time to consume each element than producer
                    Log.d("CHEEZYFLOWS", it.toString())
                }
            }

            Log.d("CHEEZYFLOWS", "Consumer required time: $time")

            // Output Log:
            // 18:06:11.813  D  1
            // 18:06:14.323  D  2
            // 18:06:16.832  D  3
            // 18:06:19.346  D  4
            // 18:06:21.859  D  5
            // 18:06:21.861  D  Consumer required time: 12566

            // Now as you can see the consumer is taking 2566 ms more time than producer to consume the data

            // producer takes 1 second to produce 1 item
            // then it waits for consumer to consume it, which takes 1.5 second
            // so 1 + 1.5 = 2.5 second for each item, so for 5 items it is taking 12.5 seconds


            // Now after adding buffer the output is:
            // Output Log:
            // 18:38:32.033  D  1
            // 18:38:33.540  D  2
            // 18:38:35.048  D  3
            // 18:38:36.555  D  4
            // 18:38:38.063  D  5
            // 18:38:38.068  D  Consumer required time: 8624

            // so as you can see, now it takes less time.

            // Now as you can see producer takes 1 second to produce 1 item
            // And this time it is not waiting for consumer to consume it, so consumer is consuming parallel
            // so 1.5 =second for consuming each item, so for 5 items it is taking 7.5 seconds, but still it is
            // taking 1.5 second more, don't know the exact reason behind it, maybe it is taking to buffer the data.

            // Till now to solve such problem what we used to do is if consumer is unable to consume huge data
            // then we used to block producer temporarily until consumer becomes ready to consume more data & same
            // is the case with vice versa situation, so we used to do using thread blocking.
            // So due to buffer capacity producer is not being blocked and data stored inside buffer until consumer
            // becomes available.
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
































