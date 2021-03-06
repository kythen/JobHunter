package com.cszjo.jobhunter.model.analysis;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Han on 2017/4/23.
 */
public class AnalysisResult {

    private List<String> category = Lists.newArrayList("平均薪资", "最高薪资", "最低薪资");
    private List<String> taskNames = Lists.newArrayList();
    private List<ItemResult> results = Lists.newArrayList();

    public AnalysisResult() {

        ItemResult avarge = new ItemResult();
        avarge.setName("平均薪资");
        ItemResult max = new ItemResult();
        max.setName("最高薪资");
        ItemResult min = new ItemResult();
        min.setName("最低薪资");

        results.add(avarge);
        results.add(max);
        results.add(min);
    }

    public List<String> getCategory() {
        return category;
    }
    public List<String> getTaskNames() {
        return taskNames;
    }

    public List<ItemResult> getResults() {
        return results;
    }
}
