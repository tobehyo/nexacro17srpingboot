package com.tobesoft.overseas.nexacro17.demo.test;

import com.nexacro17.xapi.tx.PlatformRequest;
import com.tobesoft.overseas.nexacro17.demo.api.NexacroFileUploadDownloadControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NexacroApiTestController {


    private static final Logger log = LogManager.getLogger(NexacroFileUploadDownloadControl.class);

    @RequestMapping("/services/updateDataListMap.do")
    public void updateNexacroData(@ParamDataSet(name = "input1") PlatformRequest dataList) {

    }
}
