package kr.co.tjoeun.colosseum_kotlin;

import android.app.ApplicationErrorReport;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.tjoeun.colosseum_kotlin.adapters.TopicReplyAdapter;
import kr.co.tjoeun.colosseum_kotlin.databinding.ActivityViewOldTopicBinding;
import kr.co.tjoeun.colosseum_kotlin.databinding.ActivityViewTopicBinding;
import kr.co.tjoeun.colosseum_kotlin.datas.Topic;
import kr.co.tjoeun.colosseum_kotlin.datas.TopicSide;
import kr.co.tjoeun.colosseum_kotlin.utils.ServerUtil;

public class ViewOldTopicActivity extends BaseActivity {

    ActivityViewOldTopicBinding binding;

    Topic mTopic;
    TopicReplyAdapter mTopicReplyAdapter;
    int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_old_topic);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {


    }

    @Override
    public void setValues() {

        oldTopicId = getIntent().getIntExtra("topic_id", -1);

        if (topicId == -1) {

            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        getOldTopicFromServer();
    }

    void getOldTopicFromServer() {
        ServerUtil.getRequestTopicById(mContext, topicId, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("토픽상세정보", json.toString());

                try {
                    JSONObject data = json.getJSONObject("data");
                    JSONObject topic = data.getJSONObject("topic");

                    mTopic = Topic.getTopicFromJson(topic);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setTopicValuesToUi();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void setTopicValuesToUi() {

        binding.oldTopicTitleTxt.setText(mTopic.getTitle());
        Glide.with(mContext).load(mTopic.getImageUrl()).into(binding.oldTopicImg);

        binding.firstSideTitleTxt.setText(mTopic.getSideList().get(0).getTitle());
        binding.secondSideTitleTxt.setText(mTopic.getSideList().get(1).getTitle());

        binding.firstSideVoteCountTxt.setText(String.format("%,d표", mTopic.getSideList().get(0).getVoteCount()));
        binding.secondSideVoteCountTxt.setText(String.format("%,d표", mTopic.getSideList().get(1).getVoteCount()));

        TopicSide[] topicSides = new TopicSide[2];
        for (int i =0; i <topicSides.length; i++){
            topicSides[i]= mTopic.getSideList().get(i);
        }

        mTopicReplyAdapter = new TopicReplyAdapter(mContext, R.layout.topic_reply_list_item, mTopic.getReplyList(),topicSides);
        binding.replyListView.setAdapter(mTopicReplyAdapter);
//
//        int mySideIndex = mTopic.getMySideIndex();
//
//        if (mySideIndex ==-1) {
//            binding.voteToFirstSideBtn.setEnabled(true);
//            binding.voteToSecondSideBtn.setEnabled(true);
//        }
//        else if(mySideIndex == 0){
//            binding.voteToFirstSideBtn.setEnabled(false);
//            binding.voteToSecondSideBtn.setEnabled(true);
//        }
//        else {
//            binding.voteToFirstSideBtn.setEnabled(true);
//            binding.voteToSecondSideBtn.setEnabled(false);
//        }



    }
}
