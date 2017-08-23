package io.fcmchannel.sdk.core.models.network;

import java.util.List;

/**
 * Created by gualberto on 6/14/16.
 */
public class ApiResponse<T> {

    private String next;

    private String previous;

    private List<T> results;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Response{" +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}
