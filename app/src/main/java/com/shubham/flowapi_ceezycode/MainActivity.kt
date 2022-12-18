package com.shubham.flowapi_ceezycode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // With suspending functions or coroutines we get data only once
        // Lets suppose if we are fetching a list of data from API and we are using AryncAwait for API calls,
        // here we need to wait for that entire list to be fetched in order to perform some operation
        // but what if we can update the UI after 1st element of that list is received, and after that we can update
        // the UI again after 2nd element is received and we can go on like that, in this way user will not have to wait
        // until entire list is fetched, he will be able to see what have been fetched until now, that is called stream
        // of data, like any video LiveStream work, it immediately displays the data that has been received while fetching
        // the next data at the same time, well guess what? Flow does exactly the same thing.

        CoroutineScope(Dispatchers.Main).launch {  // This is consuming data (ie Consumer)

            getUserNames().forEach {
                Log.d("CHEEZYFLOWS", it)
            }
        }

        // but it doesn't mean you should use stream/flow everywhere, you can use traditional method where one time
        // operation is needed (ie not needed streams/continuous data)
        // For some scenarios you have to use suspending functions coroutines operations
        // And for some other scenarios where continuous data needed you have to use Channels/Flows

    }


    private suspend fun getUserNames(): List<String> { // This fun is producing data (ie Producer)

        val list = mutableListOf<String>()
        list.add(getUser(1))
        list.add(getUser(2))
        list.add(getUser(3))
        list.add(getUser(4))
        list.add(getUser(5))

        // Now our UI thread need to wait until this list gets completed, which takes 1 second time for fetching
        // each user, only after entire list is completed this function will return the list object value (UI
        // Thread getUserNames() will be keep suspended until its operation finished), not until that.
        // but what if we want to update UI everytime after each user received?

        return list
    }

    private suspend fun getUser(id: Int): String {
        delay(1000)
        return "User$id"
    }
}