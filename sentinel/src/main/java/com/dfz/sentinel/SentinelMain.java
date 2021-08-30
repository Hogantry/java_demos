package com.dfz.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SentinelMain {

    public static void main(String[] args) {
//        System.setProperty("csp.sentinel.dashboard.server", "localhost:8888");
        // 配置规则
        initFlowRules();
        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                // 被保护的业务逻辑
                System.out.println("hello world");
            } catch (BlockException e) {
                // 处理被流控的逻辑
                System.out.println("block!");
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @SentinelResource
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);

    }

}
