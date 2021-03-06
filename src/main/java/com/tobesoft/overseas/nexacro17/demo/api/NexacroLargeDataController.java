package com.tobesoft.overseas.nexacro17.demo.api;

import com.nexacro17.xapi.data.*;
import com.nexacro17.xapi.tx.PlatformException;
import com.nexacro17.xapi.tx.PlatformRequest;
import com.nexacro17.xapi.tx.PlatformResponse;
import com.nexacro17.xapi.tx.PlatformType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class NexacroLargeDataController {

    private static final Logger logger = LogManager.getLogger(NexacroLargeDataController.class);

    @RequestMapping("/services/ssvRead.do")
    public void getSsvData(@RequestParam("rowcount") String rowCount, HttpServletRequest request, HttpServletResponse response) throws IOException, PlatformException {

        String strCharset = "utf-8";
        String platformType = PlatformType.CONTENT_TYPE_SSV;
        //String platformType = PlatformType.CONTENT_TYPE_XML;
        //logger.error(request.getParameter("param"));

        PlatformRequest platformRequest = new PlatformRequest(request.getInputStream(), null);
        platformRequest.receiveData();

        PlatformResponse platformResponse = new PlatformResponse(response.getOutputStream(), platformType, strCharset);
        PlatformData outPD = platformRequest.getData();
        VariableList outVariableList  = new VariableList();
        DataSetList outDataSetList   = new DataSetList();

        try {

            DataSet outDataSet0  = new DataSet("output");
            outDataSet0.addColumn("id",        DataTypes.STRING, 50);
            outDataSet0.addColumn("first_name",        DataTypes.STRING, 50);
            outDataSet0.addColumn("last_name",        DataTypes.STRING, 50);
            outDataSet0.addColumn("email",        DataTypes.STRING, 50);
            outDataSet0.addColumn("gender",        DataTypes.STRING, 50);
            outDataSet0.addColumn("ip_address",        DataTypes.STRING, 50);
            outDataSet0.addColumn("state",        DataTypes.STRING, 50);
            outDataSet0.addColumn("street",        DataTypes.STRING, 50);
            outDataSet0.addColumn("date",        DataTypes.STRING, 50);
            outDataSet0.addColumn("domain",        DataTypes.STRING, 50);
            outDataSet0.addColumn("guid",        DataTypes.STRING, 50);

            if(rowCount.equals("")) {
                rowCount = "10000";
            }

            int nRow;
            String path = request.getServletContext().getRealPath("/") + "dat/ssv_" + rowCount + ".dat";
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

//            int nRow;
//            if(rowcount == "") {
//                rowcount = "100000";
//            }
//            int cnt = Integer.parseInt(rowcount);
//            for(int i = 0; i < cnt; i++) {
//
//                // Output Dataset add row
//                nRow = outDataSet0.newRow();
//
//                // set value to Output Dataset
//                outDataSet0.set(nRow, "id",          i+1);
//                outDataSet0.set(nRow, "first_name",  "Jack"+String.format("%06d", i));
//                outDataSet0.set(nRow, "last_name",   "Weaver");
//                outDataSet0.set(nRow, "email",       "jweaver0@elpais.com");
//                outDataSet0.set(nRow, "gender",      "Male");
//                outDataSet0.set(nRow, "ip_address",  "83.140.165.49");
//                outDataSet0.set(nRow, "state",       "Connecticut");
//                outDataSet0.set(nRow, "street",      "Rieder");
//                outDataSet0.set(nRow, "date",        "2016-07-08");
//                outDataSet0.set(nRow, "domain",      "icio.us");
//                outDataSet0.set(nRow, "guid",        "4a677fd8-1d06-4b53-9287-7612e1d793d3");
//            }


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
