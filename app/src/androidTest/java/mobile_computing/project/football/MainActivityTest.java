package mobile_computing.project.football;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import mobile_computing.project.football.Activities.GameResultsActivity;
import mobile_computing.project.football.Activities.MainActivity;
import mobile_computing.project.football.Activities.RankingActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;


public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        Button ranking = mainActivity.findViewById(R.id.ranking);
        Button gameResults = mainActivity.findViewById(R.id.game_results);
        assertNotNull(ranking);
        assertNotNull(gameResults);
    }

    // Add monitors for activities
    Instrumentation.ActivityMonitor ranking_monitor = getInstrumentation()
            .addMonitor(RankingActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor game_results_monitor = getInstrumentation()
            .addMonitor(GameResultsActivity.class.getName(), null, false);

    @Test
    public void testRankingButton() {
        assertNotNull(mainActivity.findViewById(R.id.ranking));

        // Ranking clicked
        onView(withId(R.id.ranking)).perform(click());
        Activity rankingActivity = getInstrumentation()
                .waitForMonitorWithTimeout(ranking_monitor,5000);
        assertNotNull(rankingActivity);
    }

    @Test
    public void testGameResultsButton() {
        assertNotNull(mainActivity.findViewById(R.id.game_results));

        // Game Results Clicked
        onView(withId(R.id.game_results)).perform(click());
        Activity gameResultsActivity = getInstrumentation()
                .waitForMonitorWithTimeout(game_results_monitor,5000);
        assertNotNull(gameResultsActivity);
    }



    @After
    public void tearDown() throws Exception{
        mainActivity = null;
    }
}
