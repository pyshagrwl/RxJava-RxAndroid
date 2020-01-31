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


        myObservableJustWithArray = Observable.just(40,30,30,40,40,60,45,44,44)

        myObservableJustWithArray.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .distinct()
            .subscribe(getArrayObserver())
    }


    private fun getArrayObserver(): Observer<Int> {
        return object : Observer<Int> {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
            }

            override fun onNext(t: Int) {
                Log.e(TAG, "onNext method called")
                    Log.e(TAG, "value of i $t")

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError method called")

            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}


