package iu.e510.message.board.dbserver;

public class DMBReply {
    int mReplyId;
    int mPostId;
    String mReplyOwner;
    String mReplyDescription;
    int mReplyUpVotes;
    int mReplyDownVotes;
    long mReplyTimeStamp;

    public int getReplyId() {
        return mReplyId;
    }

    public void setReplyId(int mReplyId) {
        this.mReplyId = mReplyId;
    }

    public int getPostId() {
        return mPostId;
    }

    public void setPostId(int mPostId) {
        this.mPostId = mPostId;
    }

    public String getReplyOwner() {
        return mReplyOwner;
    }

    public void setReplyOwner(String mReplyOwner) {
        this.mReplyOwner = mReplyOwner;
    }

    public String getReplyDescription() {
        return mReplyDescription;
    }

    public void setReplyDescription(String mReplyDescription) {
        this.mReplyDescription = mReplyDescription;
    }

    public int getReplyUpVotes() {
        return mReplyUpVotes;
    }

    public void setReplyUpVotes(int mReplyUpVotes) {
        this.mReplyUpVotes = mReplyUpVotes;
    }

    public int getReplyDownVotes() {
        return mReplyDownVotes;
    }

    public void setReplyDownVotes(int mReplyDownVotes) {
        this.mReplyDownVotes = mReplyDownVotes;
    }

    public long getReplyTimeStamp() {
        return mReplyTimeStamp;
    }

    public void setReplyTimeStamp(long mReplyTimeStamp) {
        this.mReplyTimeStamp = mReplyTimeStamp;
    }
}
