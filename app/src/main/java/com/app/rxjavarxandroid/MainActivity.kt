package com.app.rxjavarxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private val TAG = "MyActivity"

    private lateinit var disposable: Disposable

    private lateinit var myObservableJustWithArray: Observable<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        myObservableJustWithArray = Observable.range(20,40)

        myObservableJustWithArray.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(4)
            .subscribe(getArrayObserver())
    }


    private fun getArrayObserver(): Observer<List<Int>> {
        return object : Observer<List<Int>> {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
            }

            override fun onNext(t: List<Int>) {
                Log.e(TAG, "onNext method called")
                for(i in t){
                    Log.e(TAG,"value of i $i")
                }

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError method called")

            }

            override fun onSubscribe(d: Disposable) {
                disposable =d
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}


