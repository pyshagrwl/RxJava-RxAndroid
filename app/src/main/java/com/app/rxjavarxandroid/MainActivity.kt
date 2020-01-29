package com.app.rxjavarxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var observer: DisposableObserver<Int>
    private val TAG = "MyActivity"

    private lateinit var myObservableJustWithArray: Observable<Int>

    private var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        myObservableJustWithArray = Observable.range(20,40)

        compositeDisposable.add(
            myObservableJustWithArray
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getArrayObserver())
        )
    }


    private fun getArrayObserver(): DisposableObserver<Int> {
        observer = object : DisposableObserver<Int>() {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
            }

            override fun onNext(t: Int) {
                Log.e(TAG, "onNext method called: $t")

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError method called")

            }

        }
        return observer
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}


