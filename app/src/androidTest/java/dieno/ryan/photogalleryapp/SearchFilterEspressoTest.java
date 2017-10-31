package dieno.ryan.photogalleryapp;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import dieno.ryan.photogalleryapp.SearchFilters.SearchFilter;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Created by Ryan on 10/6/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SearchFilterEspressoTest {
    @Rule
    public ActivityTestRule<GalleryActivity> mActivityRule =
            new ActivityTestRule<>(GalleryActivity.class);

    @Test
    public void SearchByDateTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String expectedID = "1";
        ArrayList<String> expectedIDList = new ArrayList<String>();
        expectedIDList.add(expectedID);
        SearchFilter sFilter = new SearchFilter(appContext);
        ArrayList<String> ids = sFilter.SearchByDate("01/01/17", "02/01/17");
        assertEquals(ids, expectedIDList);
    }

    @Test
    public void SearchByLocationTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String expectedID = "2";
        ArrayList<String> expectedIDList = new ArrayList<String>();
        expectedIDList.add(expectedID);
        SearchFilter sFilter = new SearchFilter(appContext);
        ArrayList<String> ids = sFilter.SearchByLocation("Seattle", "USA");
        assertEquals(ids, expectedIDList);
    }

    @Test
    public void SearchByKeywordTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String expectedID = "3";
        ArrayList<String> expectedIDList = new ArrayList<String>();
        expectedIDList.add(expectedID);
        SearchFilter sFilter = new SearchFilter(appContext);
        ArrayList<String> ids = sFilter.SearchByKeyword("tiger");
        assertEquals(ids, expectedIDList);
    }

    @Test
    public void ensureDateFilterWroks() {

        // Go to filter activity and set dates
        onView(withId(R.id.button_gallery_filter)).perform(click());
        onView(withId(R.id.filter_start_date)).perform(click());
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 1, 1));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.filter_end_date)).perform(click());
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 2, 1));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.filter_date_checkbox)).perform(click());
        onView(withId(R.id.button_filter_gallery)).perform(click());

        // Expected test data
        String expectedID = "1";
        ArrayList<String> expectedIDList = new ArrayList<String>();
        expectedIDList.add(expectedID);

        // Get current gallery data
        GalleryActivity mActivity = (GalleryActivity) getActivityInstance();
        ArrayList<String> ids = mActivity.currentIdList;

        assertEquals(expectedIDList, ids);
    }

    @Test
    public void ensureLocationFilterWroks() {

        // Go to filter activity and set dates
        onView(withId(R.id.button_gallery_filter)).perform(click());
        onView(withId(R.id.filter_city)).perform(replaceText("Seattle"));
        onView(withId(R.id.filter_country)).perform(replaceText("USA"));
        onView(withId(R.id.filter_location_checkbox)).perform(click());
        onView(withId(R.id.button_filter_gallery)).perform(click());

        // Expected test data
        String expectedID = "2";
        ArrayList<String> expectedIDList = new ArrayList<String>();
        expectedIDList.add(expectedID);

        // Get current gallery data
        GalleryActivity mActivity = (GalleryActivity) getActivityInstance();
        ArrayList<String> ids = mActivity.currentIdList;

        assertEquals(expectedIDList, ids);
    }

    @Test
    public void ensureKeywordFilterWroks() {

        // Go to filter activity and set dates
        onView(withId(R.id.button_gallery_filter)).perform(click());
        onView(withId(R.id.filter_keyword)).perform(replaceText("tiger"));
        onView(withId(R.id.filter_keyword_checkbox)).perform(click());
        onView(withId(R.id.button_filter_gallery)).perform(click());

        // Expected test data
        String expectedID = "3";
        ArrayList<String> expectedIDList = new ArrayList<String>();
        expectedIDList.add(expectedID);

        // Get current gallery data
        GalleryActivity mActivity = (GalleryActivity) getActivityInstance();
        ArrayList<String> ids = mActivity.currentIdList;

        assertEquals(expectedIDList, ids);
    }

    private Activity getActivityInstance(){
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable(){
            public void run(){
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }
}
