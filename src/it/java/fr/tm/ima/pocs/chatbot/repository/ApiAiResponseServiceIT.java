package fr.tm.ima.pocs.chatbot.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.tm.ima.pocs.chatbot.ChatbotDemoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ChatbotDemoApplication.class)
public class ApiAiResponseServiceIT {
    @Autowired
    private ApiAiResponseService apiAiResponseService;
    
    @Test
    public void test() {
        System.out.println(apiAiResponseService.countConnectedUser());
    }
}
