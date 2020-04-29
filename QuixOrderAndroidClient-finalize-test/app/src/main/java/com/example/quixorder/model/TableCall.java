package com.example.quixorder.model;

import com.example.quixorder.adapter.server.IServerTask;

import java.util.Date;

public class TableCall implements IServerTask {
    private String table;
    private String server;
    private String status;
    private Date startTime;
    private Date finishTime;
    private String documentID;

    public TableCall () {
        startTime = new Date();
    }

    public String getTable() {
        return table;
    }

    public String getServer() {
        return server;
    }

    public String getStatus() {
        return status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public String getDocumentID() { return documentID; }

    public void setDocumentID(String id) {documentID = id; }

    @Override
    public int getType() {
        return IServerTask.TABLE_CALL;
    }
}
