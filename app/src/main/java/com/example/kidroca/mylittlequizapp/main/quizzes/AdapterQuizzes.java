package com.example.kidroca.mylittlequizapp.main.quizzes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.kidroca.mylittlequizapp.R;
import com.example.kidroca.mylittlequizapp.data.VolleySingelton;
import com.example.kidroca.mylittlequizapp.data.extra.AnimationUtils;
import com.example.kidroca.mylittlequizapp.main.quizzes.models.quiz.Quiz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kidroca on 17.1.2016 г..
 */
public class AdapterQuizzes extends RecyclerView.Adapter<AdapterQuizzes.QuizViewHolder> {

    private static final String DEFAULT_AVATAR_URL = "http://sparkplaza.com/wp-content/uploads/free-sign.gif";

    private LayoutInflater mInflater;
    private List<Quiz> mListQuizzes = Collections.emptyList();
    private VolleySingelton mVolleySingelton;
    private ImageLoader mImageLoader;

    private DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");

    // keep track of the previous position for animations where scrolling down requires a
    // different animation compared to scrolling up
    private int mPreviousPosition = 0;

    public AdapterQuizzes(Context context) {
        this.mInflater = LayoutInflater.from(context);
        mVolleySingelton = VolleySingelton.getInstance();
        mImageLoader = mVolleySingelton.getImageLoader();
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.quiz_item, parent, false);
        QuizViewHolder viewHolder = new QuizViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {

        Quiz current = mListQuizzes.get(position);

        loadImages(current.avatarUrl, holder);

        holder.title.setText(current.title);
        String formattedDate = mFormatter.format(current.createdOn);
        holder.createdOn.setText(formattedDate);
        holder.rating.setRating((float) current.rating);
        holder.rating.setAlpha(1.0F);

        if (position > mPreviousPosition) {
            AnimationUtils.animateSunblind(holder, true);
//            AnimationUtils.animateSunblind(holder, true);
//            AnimationUtils.animate1(holder, true);
//            AnimationUtils.animate(holder,true);
        } else {
            AnimationUtils.animateSunblind(holder, false);
//            AnimationUtils.animateSunblind(holder, false);
//            AnimationUtils.animate1(holder, false);
//            AnimationUtils.animate(holder, false);
        }

        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListQuizzes.size();
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.mListQuizzes = quizzes;
        notifyDataSetChanged();
    }

    private void loadImages(String url, final QuizViewHolder holder) {
        if (url == null || url.isEmpty()) {
            url = DEFAULT_AVATAR_URL;
        }

        mImageLoader.get(url, new ImageLoader.ImageListener() {

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.avatar.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                // Nqma nishto, drugiq pat.
            }
        });
    }

    class QuizViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.quizTitle) TextView title;
        @InjectView(R.id.quizThumbnail)ImageView avatar;
        @InjectView(R.id.quizCreatedOn) TextView createdOn;
        @InjectView(R.id.quizRating) RatingBar rating;

        public QuizViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
