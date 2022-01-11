package com.example.pageviewstudy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View

class ParallaxLayoutInflater : LayoutInflater {

    private var fragment: ParallaxFragment? = null

    constructor(original: LayoutInflater, context: Context) : super(original, context)

    constructor(original: LayoutInflater, context: Context, fragment: ParallaxFragment) : super(
        original,
        context
    ) {
        this.fragment = fragment
    }

    override fun cloneInContext(newContext: Context?): LayoutInflater {
        return ParallaxLayoutInflater(this, context, fragment!!)
    }


    inner class ParallaxFactor(val inflater: LayoutInflater) : Factory2 {

        private val sClassPrefix = arrayOf("android.widget", "android.view")
        var attrIds =
            IntArray(6) { R.attr.a_in;R.attr.a_out;R.attr.x_in;R.attr.x_out; R.attr.y_in;R.attr.y_out }

        override fun onCreateView(
            parent: View?,
            name: String,
            context: Context,
            attrs: AttributeSet
        ): View? {
            val view = createMyView(name, attrs)
            if (view != null) {
                val a = context.obtainStyledAttributes(attrs, attrIds)
                if (a != null && a.length() > 6) {
                    val tag = ParallaxViewTag.TagBuild()
                        .setAlphaIn(a.getFloat(0, 0f))
                        .setAlphaOut(a.getFloat(1, 0f))
                        .setXIn(a.getFloat(2, 0f))
                        .setXOut(a.getFloat(3, 0f))
                        .setYIn(a.getFloat(4, 0f))
                        .setXOut(a.getFloat(5, 0f))
                        .build()
                }
                fragment!!.parallaxViews.add(view)
                a.recycle()
            }
            return view
        }

        override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
            return null
        }

        private fun createMyView(name: String, attrs: AttributeSet): View? {
            if (name.contains(".")) {
                return reflectView(name, null, attrs)
            } else {
                for (prefix in sClassPrefix) {
                    return reflectView(name, prefix, attrs)
                }
            }
            return null
        }

        private fun reflectView(name: String, prefix: String?, attrs: AttributeSet): View {
            return inflater.createView(name, prefix, attrs)
        }

    }

    companion object {
        const val TAG = "happy"
    }
}