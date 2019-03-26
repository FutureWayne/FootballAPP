package mobile_computing.project.football.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import mobile_computing.project.football.Adapters.GoalsAdapter;
import mobile_computing.project.football.Models.Goal;
import mobile_computing.project.football.Models.Match;
import mobile_computing.project.football.R;
import mobile_computing.project.football.Utilities.Constants;
import mobile_computing.project.football.Utilities.ImageProcessor;

public class ResultDetailsActivity extends AppCompatActivity {

    private ListView mGoalsList;
    private ImageView mTeam1Logo;
    private ImageView mTeam2Logo;
    private TextView mTeam1Name;
    private TextView mTeam2Name;
    private TextView mFinalScore;
    private TextView mMatchStart;
    private TextView mActivityHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.background_dark));
        }

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGoalsList = findViewById(R.id.goals_list);
        mTeam1Logo = findViewById(R.id.team1_logo);
        mTeam2Logo = findViewById(R.id.team2_logo);
        mFinalScore = findViewById(R.id.final_score);
        mTeam1Name = findViewById(R.id.team1);
        mTeam2Name = findViewById(R.id.team2);
        mMatchStart = findViewById(R.id.match_start);
        mActivityHeader = findViewById(R.id.activity_header);

        // set the header
        mActivityHeader.setText(getString(R.string.match_details));

        Bundle bundle = getIntent().getExtras();
        ArrayList<Goal> goalsList = null;
        Match match = null;
        if(bundle != null){
            match = (Match) bundle.get(Constants.GAME_RESULT_CLICKED);
        }

        if (match != null){

            mTeam1Name.setText(match.getmTeamName());
            mTeam2Name.setText(match.getmTeamTwoName());

            try {
                mTeam1Logo.setImageBitmap(ImageProcessor.getImageFromUrl(match.getmTeamIconUrl()));
                mTeam2Logo.setImageBitmap(ImageProcessor.getImageFromUrl(match.getmTeamTwoIconUrl()));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final String final_score = match.getmTeamScore() + " " + getString(R.string.colon) +
                    " " + match.getmTeamTwoScore();
            final String hidden_score = "Hidden";
            mFinalScore.setText(hidden_score);

            mMatchStart.setText(getMatchDateAndTime(match.getmMatchDate()));
            mGoalsList.setVisibility(View.INVISIBLE);

            // set the content for goals list
            goalsList = match.getmGoalsList();

            Button score_button = findViewById(R.id.score_hide);
            score_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFinalScore.setText(hidden_score);
                    mGoalsList.setVisibility(View.INVISIBLE);
                }
            });

            Button score_display = findViewById(R.id.score_display);
            score_display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFinalScore.setText(final_score);
                    mGoalsList.setVisibility(View.VISIBLE);
                }
            });

            Button highlights = findViewById(R.id.highlights);
            highlights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("http://sports.pptv.com/sportslive?sectionid=151344&matchid=234698");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
            });
        }

        GoalsAdapter adapter = new GoalsAdapter(this, goalsList);
        mGoalsList.setAdapter(adapter);





    }

    /**
     * Get the formatted date and time
     * @param dateTime
     * @return the string value
     */
    @NonNull
    private String getMatchDateAndTime(String dateTime){

        String[] arr = dateTime.split("T");

        String[] date_arr = arr[0].split("-");
        String date = date_arr[2] + getString(R.string.dot) + date_arr[1] +
                getString(R.string.dot) + date_arr[0];

        String time = arr[1].substring(0, arr[1].lastIndexOf(":")) + " " + getString(R.string.uhr);

        return getString(R.string.match_start) + " " + date + " " + time;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                return true;
        }



        return super.onOptionsItemSelected(item);
    }
}