package com.geminno.youstudy.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by KeryZhang on 6/23/2016.
 */
public class QuestionBean implements Serializable {
    public boolean success;
    public List<Question> data = new ArrayList<Question>();


    //必须是static类型的
    public static class Question implements Serializable {
        public Integer commentId;
        public Integer userId;
        public String userName;
        public Integer pid;
        public String date;
        public Integer likes;
        public Integer commentNumber;
        private String contentTitle;
        private String content;
        private String contentPicturePosition;
        public Integer commentType;
        public String tableName;

        public Question() {
        }

        public Question(Integer commentId, Integer userId, String userName, Integer pid, String date, Integer likes, Integer commentNumber, String contentTitle, String content, String contentPicturePosition, Integer commentType, String tableName) {
            this.commentId = commentId;
            this.userId = userId;
            this.userName = userName;
            this.pid = pid;
            this.date = date;
            this.likes = likes;
            this.commentNumber = commentNumber;
            this.contentTitle = contentTitle;
            this.content = content;
            this.contentPicturePosition = contentPicturePosition;
            this.commentType = commentType;
            this.tableName = tableName;
        }

        public Integer getCommentId() {
            return commentId;
        }

        public void setCommentId(Integer commentId) {
            this.commentId = commentId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getPid() {
            return pid;
        }

        public void setPid(Integer pid) {
            this.pid = pid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getLikes() {
            return likes;
        }

        public void setLikes(Integer likes) {
            this.likes = likes;
        }

        public Integer getCommentNumber() {
            return commentNumber;
        }

        public void setCommentNumber(Integer commentNumber) {
            this.commentNumber = commentNumber;
        }

        public String getContentTitle() {
            return contentTitle;
        }

        public void setContentTitle(String contentTitle) {
            this.contentTitle = contentTitle;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentPicturePosition() {
            return contentPicturePosition;
        }

        public void setContentPicturePosition(String contentPicturePosition) {
            this.contentPicturePosition = contentPicturePosition;
        }

        public Integer getCommentType() {
            return commentType;
        }

        public void setCommentType(Integer commentType) {
            this.commentType = commentType;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        @Override
        public String toString() {
            return "Question{" +
                    "commentId=" + commentId +
                    ", userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", pid=" + pid +
                    ", date='" + date + '\'' +
                    ", likes=" + likes +
                    ", commentNumber=" + commentNumber +
                    ", contentTitle='" + contentTitle + '\'' +
                    ", content='" + content + '\'' +
                    ", contentPicturePosition='" + contentPicturePosition + '\'' +
                    ", commentType=" + commentType +
                    ", tableName='" + tableName + '\'' +
                    '}';
        }
    }
}