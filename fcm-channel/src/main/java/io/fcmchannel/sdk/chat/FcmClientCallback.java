package io.fcmchannel.sdk.chat;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by John Cordeiro on 5/11/17.
 */

abstract class FcmClientCallback<T> implements Callback<T> {

    private final FcmClientChatPresenter presenter;

    FcmClientCallback(FcmClientChatPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        presenter.onRequestFailed();
    }
}
