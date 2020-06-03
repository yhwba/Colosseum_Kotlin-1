package kr.co.tjoeun.colosseum_kotlin.datas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topic implements Serializable {

    private int id;
    private String title;
    private String imageUrl;
    private List<TopicSide> sideList = new ArrayList<>();
    private List<TopicReply> replyList = new ArrayList<>();


    public static Topic getTopicFromJson(JSONObject jsonObject) {
        Topic topic = new Topic();

        try {
            topic.id = jsonObject.getInt("id");
            topic.title = jsonObject.getString("title");
            topic.imageUrl = jsonObject.getString("img_url");

//            같이 따라오는 진영들을 목록에 추가
            JSONArray sides = jsonObject.getJSONArray("sides");
            for ( int i = 0; i <sides.length(); i++){
                JSONObject side = sides.getJSONObject(i);
                TopicSide topicSide = TopicSide.getTopicSideFromJson(side);
                topic.sideList.add(topicSide);
            }

//            따라오는 의견 목록을 추가 파싱
            if(!jsonObject.isNull("replies")){

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return topic;
    }

    public Topic() {
    }

    public Topic(int id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<TopicSide> getSideList() {
        return sideList;
    }

    public List<TopicReply> getReplyList() {
        return replyList;
    }


}
