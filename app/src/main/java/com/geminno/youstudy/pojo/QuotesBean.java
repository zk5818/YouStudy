package com.geminno.youstudy.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KeryZhang on 7/8/2016.
 */
public class QuotesBean implements Serializable {

    public boolean success;
    public List<Quotes> data_quotes = new ArrayList<Quotes>();

    public static class Quotes implements Serializable {
        public Integer commentId;
        public String userName;
        public String contentTitle;
        public String content;

        public Integer getCommentId() {
            return commentId;
        }

        public void setCommentId(Integer commentId) {
            this.commentId = commentId;
        }

        public String getContentTitle() {
            return contentTitle;
        }

        public void setContentTitle(String contentTitle) {
            this.contentTitle = contentTitle;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}