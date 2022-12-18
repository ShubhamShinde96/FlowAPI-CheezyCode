package com.shubham.flowapi_ceezycode.SharedFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shubham.flowapi_ceezycode.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StateFlowActivity1 : AppCompatActivity() {

    // 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow1)

        // State flow is also kind of SharedFlow, it is also Hot-Natured but it maintains a state for us.
        // Means it maintains the last value of flow.
        // StateFlow will provide latest value(last value), basically you can consider it as a single value buffer
        // doesn't matter if consumer joins late or on time, it will gat the last latest single value from state-flow
        // now after all the operations if flow emits any other new value then all the registers consumers will get
        // that value and even after that someone joins then that consumer will get latest value (last emitted value).


        GlobalScope.launch(Dispatchers.Main) {

            val result = producer()

//            delay(2500)

            // Remember, as this is a Hot Flow, the items will keep producing even if there's no consumer
            // so basically as we are collecting it by 2.5 seconds of delay, the items before that should've been
            // lost, right?
            // and as per the result (logs) you can see that you're only getting the items which are emitted after the delay
            // because of Hot state flow, you are only getting latest emitted values.

//            delay(6000) // now what will happen
            // You'll not receive any data here, because there was no data present after 6 seconds because as this
            // is a hot stream

            // but if we use state flow then things will change, lets change the shared flow with state flow inside/
            // producer() method

//            delay(6000) # // now if you keep this delay then you'll directly get last emitted value 30
            // as state flow maintains the state of last emitted value and provide it whenever consumer joins.
            // if you remove all delays then you'll first get 10 as it was initial value, then 20 & then 30

            // if this is SharedFlow and if we try to consume it after 6 seconds then we will not get any value

            /*result.collect { #
                Log.d("CHEEZYFLOWS", "Item: $it") #
            } #  */

            // Now there is one more property in state flow, to see it I'm commenting out all above active code representing #
//            result.value  // as you can see, we have property "value", this value represents last/latest updated
            // value of state flow.
            Log.d("CHEEZYFLOWS", "Latest/Last value: ${result.value}")

            delay(4500) // comment this and below line to see changes in output then uncomment it
            Log.d("CHEEZYFLOWS", "Latest/Last value: ${result.value}")

            result.collect {
                Log.d("CHEEZYFLOWS", "Item: $it")
                Log.d("CHEEZYFLOWS", "Latest/Last value: ${result.value}")
            }



        }

    }

//    private fun producer(): Flow<Int> {
    // Now there is one more property in state flow, to see it I'm commenting out above line
    private fun producer(): StateFlow<Int> {

        val mutableStateFlow = MutableStateFlow(10) // need to pass initial value

        GlobalScope.launch {
            delay(2000)
            mutableStateFlow.emit(20)
            delay(2000)
            mutableStateFlow.emit(30)
        }

        return mutableStateFlow
    }


    // So the difference between live data and state flow is

    // 1st difference: in case of live data all the transformations happens on Main thread, means all the operators
    // execute on main thread, like map, filter, switchMap such operators execute on main thread so this is a
    // disadvantage, as UI gets slow and laggy if we're playing with complex or large data
    // as state flow is a flow and it has property of flowOn(Thread) so we can execute it on any thread.

    // 2nd difference: In live data we have limited operators, but in state flow we have more operators than live-data

    // 3rd difference: live-data is lifecycle aware, it needs lifecycle if activity, fragment.
    // but we have some places where there's no lifecycle for ex: a repository
    // so in such cases we can use flow
}






















