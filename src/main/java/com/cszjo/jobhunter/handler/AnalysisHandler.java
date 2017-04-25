package com.cszjo.jobhunter.handler;

import com.cszjo.jobhunter.model.ClawerTask;
import com.cszjo.jobhunter.model.JobInfo;
import com.cszjo.jobhunter.model.analysis.AnalysisResult;
import com.cszjo.jobhunter.model.analysis.ItemResult;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Han on 2017/4/23.
 */
@Component
public class AnalysisHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(AnalysisHandler.class);

    public AnalysisResult analysis(List<JobInfo> lists, AnalysisResult result, ClawerTask clawerTask) {

        if (lists == null || lists.size() == 0) {
            LOGGER.error("analysis job info has a error, job info is null or empty");
            return null;
        }

        double size = 0;
        long sumMoney = 0;
        double maxMoney = 0;
        double minMoney = Double.MAX_VALUE;

        for (JobInfo jobInfo : lists) {

            int money = getMoneyFormMaxAndMinMoney(jobInfo.getMaxMoney());
            if (money != 0) {

                size++;
                sumMoney += money;
                maxMoney = maxMoney > money ? maxMoney : money;
                minMoney = minMoney < money ? minMoney : money;
            }

            LOGGER.info("analysis a job info, job name = {}, money = {}", jobInfo.getJobName(), money);
        }

        double average = sumMoney / size;

        List<String> taskNames = result.getTaskNames();

        taskNames.add(clawerTask.getTaskName());

        List<ItemResult> results = result.getResults();
        ItemResult itemResult = new ItemResult();
        itemResult.setName(clawerTask.getTaskName());

        List<Double> r = Lists.newArrayList();
        r.add(average);
        r.add(maxMoney);
        r.add(minMoney);
        itemResult.setData(r);

        results.add(itemResult);

        return result;
    }

    private int getMoneyFormMaxAndMinMoney(String money) {

        int maxMoney = 0;
        int minMoney = 0;

        //18k-30k la gou template
        if (money.endsWith("k")) {

            String[] maxAndMinMoney = money.split("-");
            String maxMoneyStr = maxAndMinMoney[0];
            String minMoneyStr = maxAndMinMoney[1];

            if (maxMoneyStr.endsWith("k")) {
                maxMoney = Integer.parseInt(maxMoneyStr.substring(0, maxMoneyStr.length() - 1));
            }

            if (minMoneyStr.endsWith("k")) {
                minMoney = Integer.parseInt(minMoneyStr.substring(0, minMoneyStr.length() - 1));
            }

            return (maxMoney + minMoney) / 2;
        }

        //6000-8000 zhao ping template
        if (money.endsWith("0")) {

            String[] maxAndMinMoney = money.split("-");

            if (maxAndMinMoney.length == 2) {
                maxMoney = Integer.parseInt(maxAndMinMoney[0]);
                minMoney = Integer.parseInt(maxAndMinMoney[1]);

                return (maxMoney + minMoney) / 2;
            }
        }

        return 0;
    }
}
