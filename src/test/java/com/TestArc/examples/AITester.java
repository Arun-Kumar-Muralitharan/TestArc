package com.TestArc.examples;

import com.TestArc.core.AI.chatGPTTestGenerator;
import org.junit.jupiter.api.Test;

public class AITester {

    @Test
    public  void aiTestGeneration()
    {
        String prompt;
        String testName;
        try
        {
            prompt = chatGPTTestGenerator.generateCodeForUI("OMSEndtoEnd",
                    "Navigate to https://credit-qa.go-yubi.in login and then navigate to 'https://credit-qa.go-yubi.in/my-applications', " +
                            "then select the Tab named 'All', and then click the order named 'AKM Group Prt Ltd, and now wait for the new tab to load." +
                            "Now select the Tab named 'Tasks & Activities'." +
                            "Now Hover on 'EoI approval' and then click on 'Accept'");
            System.out.println(prompt);
        }
        catch (Exception e)
        {
            System.out.println("Error in AI Test Generation");
        }
    }
}
