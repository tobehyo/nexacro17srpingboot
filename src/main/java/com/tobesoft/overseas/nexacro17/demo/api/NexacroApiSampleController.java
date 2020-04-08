package com.tobesoft.overseas.nexacro17.demo.api;

import com.nexacro17.xapi.data.*;
import com.nexacro17.xapi.tx.PlatformException;
import com.nexacro17.xapi.tx.PlatformRequest;
import com.nexacro17.xapi.tx.PlatformResponse;
import com.nexacro17.xapi.tx.PlatformType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/services/nexacroapi")
//@CrossOrigin(origins = "http://localhost")
@RestController
public class NexacroApiSampleController {

    private static final Logger logger = LogManager.getLogger(NexacroApiSampleController.class);

    @GetMapping
    public void getDataNexacroApiSampleController(HttpServletRequest request, HttpServletResponse response) throws IOException, PlatformException {

        String strCharset = "utf-8";
        //String platformType = PlatformType.CONTENT_TYPE_SSV;
        String platformType = PlatformType.CONTENT_TYPE_XML;
        //logger.error(request.getParameter("param"));
        PlatformRequest platformRequest = new PlatformRequest(request.getInputStream(), platformType);
        platformRequest.receiveData();

//        PlatformData inPD = platformRequest.getData();
//
//        VariableList inVariableList = inPD.getVariableList();
//        DataSetList inDatdasetList = inPD.getDataSetList();

//        logger.error(inDatdasetList.get(0).getString(0, 0));

        PlatformResponse platformResponse = new PlatformResponse(response.getOutputStream(), platformType, strCharset);
        PlatformData outPD = platformRequest.getData();
        VariableList    outVariableList  = new VariableList();
        DataSetList     outDataSetList   = new DataSetList();

        try {

            DataSet outDataSet0  = new DataSet("output");
            outDataSet0.addColumn("Column0",        DataTypes.STRING, 20);
            outDataSet0.addColumn("Column1",        DataTypes.STRING, 256);
            outDataSet0.addColumn("Column2",        DataTypes.STRING, 256);
            outDataSet0.addColumn("Column3",        DataTypes.STRING, 256);
            outDataSet0.addColumn("Column4",        DataTypes.STRING, 256);

            outDataSet0.newRow();
            outDataSet0.set(0, "Column0", "테스트");
            outDataSet0.newRow();
            outDataSet0.set(1, "Column0", "테스트");


            outDataSetList.add(outDataSet0);

            outVariableList.add("ErrorCode", 0);
            outVariableList.add("ErrorMsg",  "Success");
            outVariableList.add("argTest",  "variable id");


        } catch(Exception e) {

            outVariableList.add("ErrorMsg",  e);
            outVariableList.add("ErrorCode", -1);
            e.printStackTrace();

        } finally {

            outPD.setDataSetList(outDataSetList);
            outPD.setVariableList(outVariableList);
            platformResponse.setData(outPD);
            platformResponse.sendData();
        }
    }
    
    @PostMapping
    public void postDataNexacroApiSampleController(HttpServletRequest request, HttpServletResponse response) throws IOException, PlatformException {

        String strCharset = "utf-8";
        String platformType = PlatformType.CONTENT_TYPE_SSV;

        PlatformRequest platformRequest = new PlatformRequest(request.getInputStream(), platformType);
        platformRequest.receiveData();
        PlatformData inPD = platformRequest.getData();

        VariableList inVariableList = inPD.getVariableList();
        DataSetList inDatdasetList = inPD.getDataSetList();
        
        System.out.println(inVariableList.getString(0));
        System.out.println(inDatdasetList.get(0).getString(0, 0));

        PlatformResponse platformResponse = new PlatformResponse(response.getOutputStream(), platformType, strCharset);
        PlatformData outPD = platformRequest.getData();
        VariableList    outVariableList  = new VariableList();
        DataSetList     outDataSetList   = new DataSetList();

        try {

            DataSet outDataSet0  = new DataSet("output");
            outDataSet0.addColumn("Column0",        DataTypes.STRING, 20);
            outDataSet0.addColumn("Column1",        DataTypes.STRING, 256);
            outDataSet0.addColumn("Column2",        DataTypes.STRING, 256);
            outDataSet0.addColumn("Column3",        DataTypes.STRING, 256);
            outDataSet0.addColumn("Column4",        DataTypes.STRING, 256);

            outDataSet0.newRow();
            outDataSet0.set(0, "Column0", "테스트");
            outDataSet0.newRow();
            outDataSet0.set(1, "Column0", "테스트");


            outDataSetList.add(outDataSet0);

            outVariableList.add("ErrorCode", 0);
            outVariableList.add("ErrorMsg",  "Success");
            outVariableList.add("argTest",  "variable id");


        } catch(Exception e) {

            outVariableList.add("ErrorMsg",  e);
            outVariableList.add("ErrorCode", -1);
            e.printStackTrace();

        } finally {

            outPD.setDataSetList(outDataSetList);
            outPD.setVariableList(outVariableList);
            platformResponse.setData(outPD);
            platformResponse.sendData();
        }
    }
}
