package com.example.pageviewstudy

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.nineoldandroids.view.ViewHelper


class ParallaxContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), ViewPager.OnPageChangeListener {
    lateinit var fragments: MutableList<ParallaxFragment>
    lateinit var adapter: ParallaxPagerAdapter
    var iv_main: ImageView? = null

    fun setUp(vararg childIds: Int) {
        fragments = mutableListOf()
        for (element in childIds) {
            val f = ParallaxFragment()
            val args = Bundle()
            args.putInt("layoutId", element)
            f.arguments = args
            fragments.add(f)
        }
        val vp = ViewPager(context)
        vp.id = R.id.parallax_pager
        val activity = context as SplashActivity
        adapter = ParallaxPagerAdapter(activity.supportFragmentManager, fragments)
        vp.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        vp.adapter = adapter
        vp.setOnPageChangeListener(this)
        addView(vp, 0)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        var outFragment: ParallaxFragment? = null
        if (position > 0)
            outFragment = fragments[position - 1]
        var inFragment: ParallaxFragment? = fragments[position]
        if (outFragment != null) {
            val outViews = outFragment.parallaxViews
            if (outViews != null) {
                for (view in outViews) {
                    var tag: ParallaxViewTag? =
                        view.getTag(R.id.parallax_view_tag) as ParallaxViewTag?
                    if (tag == null) {
                        continue
                    }
                    ViewHelper.setTranslationX(view, (width - positionOffsetPixels) * tag.xIn)
                    ViewHelper.setTranslationY(view, (width - positionOffsetPixels) * tag.yIn)
                }
            }
        }

        if (inFragment != null) {
            val inViews = inFragment.parallaxViews
            for (view in inViews) {
                var tag: ParallaxViewTag? =
                    view.getTag(R.id.parallax_view_tag) as ParallaxViewTag?
                if (tag == null) {
                    continue
                }
                ViewHelper.setTranslationX(view, (0 - positionOffsetPixels) * tag.yIn)
                ViewHelper.setTranslationY(view, (0 - positionOffsetPixels) * tag.yOut)
            }
        }

    }

    override fun onPageSelected(position: Int) {
        if (position == adapter.count - 1) {
            iv_main?.visibility = INVISIBLE
        } else {
            iv_main?.visibility = VISIBLE
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        val animation = iv_main?.background as AnimationDrawable
        when (state) {
            ViewPager.SCROLL_STATE_DRAGGING -> {
                animation.start()
            }
            ViewPager.SCROLL_STATE_IDLE -> {
                animation.stop()
            }
        }
    }
}