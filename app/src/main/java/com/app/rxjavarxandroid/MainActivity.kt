package com.app.rxjavarxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var observer: Observer<String>
    val greetings = "Welcome to RxJava"
    lateinit var myObservable: Observable<String>
    val TAG = "MyActivity"

    lateinit var disposable: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myObservable = Observable.just(greetings)
        observer = object : Observer<String> {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
            }

            override fun onSubscribe(d: Disposable) {
                Log.e(TAG, "onSubscribe method called")
                disposable=d

            }

            override fun onNext(t: String) {
                Log.e(TAG, "onNext method called")

                txtvw.setText(t)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError method called")

            }

        }
        myObservable.subscribe(observer)


    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}
