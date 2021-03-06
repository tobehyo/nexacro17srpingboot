package com.tobesoft.overseas.nexacro17.demo.api;

import com.nexacro17.xapi.data.*;
import com.nexacro17.xapi.tx.PlatformException;
import com.nexacro17.xapi.tx.PlatformRequest;
import com.nexacro17.xapi.tx.PlatformResponse;
import com.nexacro17.xapi.tx.PlatformType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class NexacroPivotDataController {
    @RequestMapping("/services/ssvPivotDataRead.do")
    public void getSsvPivotDatar(@RequestParam("rowcount") String rowCount,  HttpServletRequest request, HttpServletResponse response) throws IOException, PlatformException {

        String strCharset = "utf-8";
        String platformType = PlatformType.CONTENT_TYPE_SSV;

        PlatformRequest platformRequest = new PlatformRequest(request.getInputStream(), null);
        platformRequest.receiveData();

        PlatformResponse platformResponse = new PlatformResponse(response.getOutputStream(), platformType, strCharset);
        PlatformData outPD = platformRequest.getData();
        VariableList outVariableList  = new VariableList();
        DataSetList outDataSetList   = new DataSetList();

        try {

            DataSet outDataSet0  = new DataSet("output");
            outDataSet0.addColumn("col1",  DataTypes.STRING, 50);
            outDataSet0.addColumn("col2",  DataTypes.STRING, 50);
            outDataSet0.addColumn("col3",  DataTypes.STRING, 50);
            outDataSet0.addColumn("col4",  DataTypes.STRING, 50);
            outDataSet0.addColumn("col5",  DataTypes.STRING, 50);
            outDataSet0.addColumn("col6",  DataTypes.STRING, 50);
            outDataSet0.addColumn("col7",  DataTypes.STRING, 50);
            outDataSet0.addColumn("col8",  DataTypes.STRING, 50);
            outDataSet0.addColumn("col9",  DataTypes.STRING, 50);
            outDataSet0.addColumn("col10", DataTypes.STRING, 50);
            outDataSet0.addColumn("col11", DataTypes.STRING, 50);
            outDataSet0.addColumn("col12", DataTypes.INT, 8);
            outDataSet0.addColumn("col13", DataTypes.INT, 8);
            outDataSet0.addColumn("col14", DataTypes.INT, 8);
            outDataSet0.addColumn("col15", DataTypes.INT, 8);
            outDataSet0.addColumn("col16", DataTypes.INT, 8);


            if(rowCount.equals("")) {
                rowCount = "10000";
            }

            int nRow;

            String path = request.getServletContext().getRealPath("/") + "dat/test" + rowCount + ".dat";
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line;
            char a = (char) 0x1e;
            char b = (char) 0x1f;
            String RS = String.valueOf(a);
            String US = String.valueOf(b);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(RS);
                for (String str : values) {
                    String[] cols = str.split(US);

                    int colCnt = cols.length;
                    nRow = outDataSet0.newRow();
                    for(int i=1; i< colCnt; i++) {
                        outDataSet0.set(nRow, i-1, cols[i]);
                    }
                }
            }
            br.close();

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
