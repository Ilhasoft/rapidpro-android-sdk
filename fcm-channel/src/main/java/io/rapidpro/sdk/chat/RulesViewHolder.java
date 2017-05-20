package io.rapidpro.sdk.chat;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.rapidpro.sdk.R;
import io.rapidpro.sdk.chat.tags.OnTagClickListener;
import io.rapidpro.sdk.chat.tags.TagsAdapter;
import io.rapidpro.sdk.core.models.FlowRuleset;
import io.rapidpro.sdk.util.SpaceItemDecoration;

/**
 * Created by John Cordeiro on 5/20/17.
 * Copyright © 2017 rapidpro-android-sdk, Inc. All rights reserved.
 */

class RulesViewHolder extends RecyclerView.ViewHolder {

    private final OnTagClickListener onTagClickListener;
    private RecyclerView tags;

    RulesViewHolder(Context context, ViewGroup parent, OnTagClickListener onTagClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.fcm_client_item_tags, parent, false));
        this.onTagClickListener = onTagClickListener;
        tags = (RecyclerView) itemView.findViewById(R.id.tags);

        SpaceItemDecoration tagsItemDecoration = new SpaceItemDecoration();
        tagsItemDecoration.setHorizontalSpaceWidth(parent.getPaddingBottom());

        tags.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        tags.addItemDecoration(tagsItemDecoration);
    }

    void bind(FlowRuleset rulesets) {
        TagsAdapter tagsAdapter = new TagsAdapter(rulesets.getRules(), onTagClickListener);
        tags.setAdapter(tagsAdapter);
    }
}
