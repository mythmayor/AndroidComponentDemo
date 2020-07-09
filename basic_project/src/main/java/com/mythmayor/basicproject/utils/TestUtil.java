package com.mythmayor.basicproject.utils;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by mythmayor on 2020/7/8.
 */
public class TestUtil {

    public static void main(String[] args) {
        testRxJava();
    }

    private static void testRxJava() {
        //Sample01
        String[] names = {"aaa", "bbb", "ccc", "ddd", "eee", "fff"};
        Observable.fromArray(names).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("onNext - " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError - " + e.getCause().getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

    }
}
