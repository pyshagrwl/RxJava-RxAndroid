package com.app.rxjavarxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var observer: DisposableObserver<String>
    val greetings = "Welcome to RxJava"
    lateinit var myObservable: Observable<String>
    val TAG = "MyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myObservable = Observable.just(greetings)

        observer = object : DisposableObserver<String>() {
            override fun onComplete() {
                Log.e(TAG, "onComplete method called")
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
        observer.dispose()
    }

}
