package com.app.chartdemo

class Test {

    fun main(){
        foo {
            return@foo
        }
    }

    inline fun foo(crossinline f: () -> Unit) {
        bar { f() }
    }

    fun bar(f: () -> Unit) {
        f()
    }

}