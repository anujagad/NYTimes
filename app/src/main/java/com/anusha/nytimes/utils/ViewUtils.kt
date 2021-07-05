package com.anusha.nytimes.utils

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Transition
import androidx.transition.TransitionSet
import com.anusha.nytimes.R

import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.util.*
import kotlin.math.pow

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun MenuItem.gone() {
    isVisible = false
}

fun MenuItem.visible() {
    isVisible = true
}

fun View.disable() {
    isEnabled = false
}

fun View.enable() {
    isEnabled = true
}


fun View.scaleView(
    startScale: Float,
    endScale: Float,
    duration: Long,
    repeatInfinite: Boolean = false,
    listener: () -> Unit = {}
) {
    val anim = ScaleAnimation(
        startScale, endScale,
        startScale, endScale,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    ) // Pivot point of Y scaling
    anim.apply {
        fillAfter = true // Needed to keep the result of the animation
        this.duration = duration
        if (repeatInfinite) {
            repeatCount = Animation.INFINITE
            repeatMode = Animation.RESTART
        } else {
            interpolator = OvershootInterpolator()
        }
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                listener.invoke()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }
    this.startAnimation(anim)
}

fun View.blinkBackground(
    colorFrom: Int,
    colorTo: Int,
    duration: Long,
    isReversed: Boolean = false,
    listener: () -> Unit = {}
) {
    val objectAnimator = ObjectAnimator.ofObject(
        this,
        "backgroundColor",
        ArgbEvaluator(),
        colorFrom,
        colorTo
    )

// 2
    objectAnimator.repeatCount = 1
    objectAnimator.repeatMode = ValueAnimator.REVERSE

// 3
    objectAnimator.duration = duration
    objectAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            listener.invoke()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }
    })
    objectAnimator.start()
}

fun ViewGroup.inflateView(idRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(idRes, this, attachToRoot)

fun Context.inflateView(idRes: Int): View = LayoutInflater.from(this).inflate(idRes, null)

fun ViewGroup.forEach(consumer: (View) -> Unit) = forEachIndexed { _, view -> consumer(view) }

fun ViewGroup.forEachIndexed(consumer: (index: Int, view: View) -> Unit) {
    for (index in 0 until childCount) consumer(index, this.getChildAt(index))
}



infix fun NestedScrollView.smoothlyScrollToTopWithTiming(duration: Long) {
    post {
        ValueAnimator.ofFloat(0f, y).apply {
            this.duration = duration
            addUpdateListener { animator ->
                smoothScrollTo(0, (y - animator?.animatedValue as Float).toInt())
            }
        }.start()
    }
}

infix fun ScrollView.smoothlyScrollToTopWithTiming(duration: Long) {
    post {
        ValueAnimator.ofFloat(0f, y).apply {
            this.duration = duration
            addUpdateListener { animator ->
                smoothScrollTo(0, (y - animator?.animatedValue as Float).toInt())
            }
        }.start()
    }
}

fun View.setBackgroundTint(color: Int) {
    var buttonDrawable = background
    buttonDrawable = DrawableCompat.wrap(buttonDrawable)
    //the color is a direct color int and not a color resource
    DrawableCompat.setTint(buttonDrawable, color)
    background = buttonDrawable
}

fun ViewGroup.getScrollByHorizontal(view: View): Int {
    val intArray = intArrayOf(0, 0)
    view.getLocationOnScreen(intArray)
    val onScreenLeft = intArray[0]

    return when {
        onScreenLeft < 0 -> {
            onScreenLeft - 50
        }
        (onScreenLeft + view.width) > width -> {
            onScreenLeft + view.width - width + 50
        }

        else -> {
            0
        }
    }
}

inline fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit = {}) {
    snack(resources.getString(messageRes), length, f)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit = {}) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit = {}) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit = {}) {
    setAction(action, listener)
    color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
}
