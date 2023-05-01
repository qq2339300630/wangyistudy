package com.example.dynamicstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class MainActivity : AppCompatActivity(), DbOperation {
    lateinit var db: DbOperation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = Proxy.newProxyInstance(
            DbOperation::class.java.classLoader,
            arrayOf(DbOperation::class.java),
            DbHandle(this)
        ) as DbOperation
    }

    inner class DbHandle(private val db: DbOperation) : InvocationHandler {
        override fun invoke(p0: Any?, p1: Method?, p2: Array<Any>?): Any? {
            Log.e("caojiajun", "开始备份数据")
            save()
            Log.e("caojiajun", "等待操作")
            return p1!!.invoke(db,*(p2 ?: emptyArray()))
        }

    }

    fun jump(view: View) {
        db.insert("你就是条狗")
        db.update()
    }

    override fun insert(str:String) {
        Log.e("caojiajun", str)
    }

    override fun delete() {

    }

    override fun update() {
        Log.e("caojiajun", "来打我呀")
    }

    override fun save() {

    }
}