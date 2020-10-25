package com.test.tddandroid

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.core.util.Preconditions
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    bundle: Bundle? = null, resId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}
) {
    val mainActivityIntent = Intent.makeMainActivity(
        (ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        ))
    ).putExtra(FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, resId)

    ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(
                T::class.java.classLoader,

                ),
            T::class.java.name
        )
        fragment.arguments = bundle
        activity.supportFragmentManager.beginTransaction().add(android.R.id.content, fragment, "")
            .commitNow()
        (fragment as T).action()
    }


}