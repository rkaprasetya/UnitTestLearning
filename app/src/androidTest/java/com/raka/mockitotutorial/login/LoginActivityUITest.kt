package com.raka.mockitotutorial.login

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.raka.mockitotutorial.MainActivity
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.RootMatchers.*
import androidx.test.espresso.action.ViewActions.*
import org.hamcrest.CoreMatchers.*
import com.raka.mockitotutorial.R
import androidx.test.espresso.assertion.ViewAssertions.*
import org.junit.*
import org.junit.runner.RunWith

/**
 * Tutorial espresso
 * https://www.vogella.com/tutorials/AndroidTestingEspresso/article.html
 */
@RunWith(AndroidJUnit4::class)
class LoginActivityUITest {
    @JvmField
    @Rule
    var mActivityRule:ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun login_activityLaunchedSuccessfully(){
        ActivityScenario.launch(LoginActivity::class.java)
    }
    @Test
    fun login_buttonNotNull(){
        onView(withId(R.id.btn_login)).check(matches(notNullValue() ));
        onView(withId(R.id.btn_login)).check(matches(withText("Login")));
    }
    @Test
    fun login_fail_emptyUsername(){
        onView(withId(R.id.et_username)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withText(R.string.username_empty)).inRoot(withDecorView(not(mActivityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }
    @Test
    fun login_fail_wrongEmailFormat(){
        onView(withId(R.id.et_username)).perform(typeText("raka"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withText(R.string.wrong_email)).inRoot(withDecorView(not(mActivityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }
    @Test
    fun login_fail_emptyPassword(){
        ScreenRobot.withRobot(LoginScreenRobot::class.java)
            .provideActivityContext(mActivityRule.activity)
            .inputUsernameAndPasswordAndClickLoginButton("rka@gmail.com","")
            .verifyToastMessageDisplayed(R.string.password_empty)
    }
    @Test
    fun login_fail_errorServer(){
        ScreenRobot.withRobot(LoginScreenRobot::class.java)
            .provideActivityContext(mActivityRule.activity)
            .inputUsernameAndPasswordAndClickLoginButton("rka@gmail.com","password")
            .verifyToastMessageDisplayed(R.string.login_fail)
    }
    @Test
    fun login_fail_progressBarDisplayed(){
        ScreenRobot.withRobot(LoginScreenRobot::class.java)
            .inputUsernameAndPasswordAndClickLoginButton("rka@gmail.com","password")
            .verifyProgressBarDisplayed()
    }
    class LoginScreenRobot:ScreenRobot<LoginScreenRobot>(){
        fun inputUsernameAndSelectLoginButton(text:String):LoginScreenRobot{
            return insertTextIntoView(R.id.et_username,text).clickOnView(R.id.btn_login)
        }

        fun inputUsernameAndPasswordAndClickLoginButton(username:String,password:String):LoginScreenRobot{
            return insertTextIntoView(R.id.et_username,username)
                .insertTextIntoView(R.id.et_password,password)
                .clickOnView(R.id.btn_login)
        }

        fun verifyToastMessageDisplayed(stringId:Int):LoginScreenRobot{
            return checkDialogueWithTextIsDisplayed(stringId)
        }

        fun verifyProgressBarDisplayed():LoginScreenRobot{
            return checkIsDisplayed(R.id.pb_login)
        }

    }
}