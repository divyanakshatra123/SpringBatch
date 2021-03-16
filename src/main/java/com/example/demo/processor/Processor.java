package com.example.demo.processor;

import com.example.demo.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<User,User> {

    private static final Map<String,String> m = new HashMap<>();

    public Processor() {
        m.put("it","IT DEPT");
        m.put("hr","HR DEPT");
        m.put("hr","FINANCE DEPT");
    }

    @Override
    public User process(User user) throws Exception {
        String deptCode = user.getDept();
        user.setDept(m.get(deptCode));
        return user;
    }
}
