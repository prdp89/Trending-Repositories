package com.gojek.trendingrepo


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        //simulate fetch of data
        Thread.sleep(5000)
        testOnItemClick()

        Thread.sleep(3000)

        //perform swipe
        testSwipeToRefresh()

        //test Options Menu Click
        testOptionsMenu()
    }

    private fun testOnItemClick() {
        val cardView = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.rv_repo_list)
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView.perform(click())
    }

    private fun testSwipeToRefresh() {
        onView(withId(R.id.swipe_refresh_layout))
            .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))

        Thread.sleep(3000)
    }

    private fun testOptionsMenu() {
        Thread.sleep(300)

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())


        Thread.sleep(250)

        val appCompatTextView = onView(
            allOf(
                withId(R.id.title), withText("Refresh"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    private fun withCustomConstraints(
        action: ViewAction,
        constraints: Matcher<View>
    ): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController?, view: View?) {
                action.perform(uiController, view)
            }
        }
    }
}
