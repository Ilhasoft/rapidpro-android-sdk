package io.rapidpro.sdk.chat.tags;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.rapidpro.sdk.R;
import io.rapidpro.sdk.core.models.FlowRule;
import io.rapidpro.sdk.core.models.TypeValidation;

/**
 * Created by john-mac on 6/30/16.
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private List<FlowRule> rules;
    private OnTagClickListener onTagClickListener;

    public TagsAdapter(List<FlowRule> rules, OnTagClickListener onTagClickListener) {
        addRules(rules);
        this.onTagClickListener = onTagClickListener;
    }

    private void addRules(List<FlowRule> rules) {
        this.rules = new ArrayList<>();
        for (FlowRule rule : rules) {
            if (getTypeValidationByRule(rule) == TypeValidation.Choice) {
                this.rules.add(rule);
            }
        }
    }

    @NonNull
    private TypeValidation getTypeValidationByRule(FlowRule rule) {
        return TypeValidation.getTypeValidationForRule(rule);
    }

    @Override
    public TagsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(TagsAdapter.ViewHolder holder, int position) {
        holder.bind(rules.get(position));
    }

    @Override
    public int getItemCount() {
        return rules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView text;
        private FlowRule rule;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.fcm_client_item_rule, null));
            text = (TextView) itemView.findViewById(R.id.text);
            text.setOnClickListener(onTagClickListener);
        }

        public void bind(FlowRule rule) {
            this.rule = rule;
            text.setText(getFirstFromMap(rule.getCategory()));
        }

        private View.OnClickListener onTagClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagsAdapter.this.onTagClickListener.onTagClick(getResponseFromRule());
            }
        };

        @Nullable
        private String getResponseFromRule() {
            String response = rule.getTest().getBase();
            if(response == null && rule.getTest().getTest() != null
            && rule.getTest().getTest().values().size() > 0) {
                response = getFirstFromMap(rule.getTest().getTest());
            }
            return response;
        }
    }

    private String getFirstFromMap(HashMap<String, String> map) {
        String ruleValues = map.values().iterator().next();
        if (!TextUtils.isEmpty(ruleValues)) {
            return ruleValues.split(" ")[0];
        }
        return ruleValues;
    }

}
