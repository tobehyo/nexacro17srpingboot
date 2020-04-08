//package com.tobesoft.overseas.nexacro17.demo.api;
//
//import com.nexacro17.xapi.data.*;
//import com.nexacro17.xapi.tx.PlatformException;
//import com.nexacro17.xapi.tx.PlatformResponse;
//import com.nexacro17.xapi.tx.PlatformType;
//import com.oreilly.servlet.MultipartRequest;
//import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.util.Enumeration;
//
//@Controller
//public class NexacroFileUploadControl {
//
//    private static final Logger logger = LogManager.getLogger(NexacroLargeDataController.class);
//
//    @RequestMapping(value = "/services/fileUpload.do", method = RequestMethod.POST)
//    public void NexacroFileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, PlatformException {
//
//        String strCharset = "UTF-8";
//
//        String contextRealPath	= request.getSession().getServletContext().getRealPath("/");
//        String getFolderName	= request.getParameter("filefolder");
//
//        String savePath = contextRealPath + getFolderName + File.separator;
//        logger.debug(savePath);
//        int maxSize = 1000 * 1024 * 1024;
//
//        File extFolder = new File(savePath);
//
//        if(!extFolder.exists())
//        {
//            extFolder.mkdir();
//        }
//
//        request.setCharacterEncoding(strCharset);
//
//        String platformType = PlatformType.CONTENT_TYPE_XML;
//        PlatformResponse platformResponse = new PlatformResponse(response.getOutputStream(), platformType, strCharset);
//        PlatformData resData = new PlatformData();
//        VariableList resVarList = resData.getVariableList();
//
//        try
//        {
//            MultipartRequest multi = new MultipartRequest(request, savePath, maxSize, strCharset, new DefaultFileRenamePolicy());
//
//            Enumeration files = multi.getFileNames();
//
//            //String getFolderName = multi.getParameter("PATH");
//
//            DataSet ds = new DataSet("filefolder");
//
//            ds.addColumn(new ColumnHeader("fileName", DataTypes.STRING));
//            ds.addColumn(new ColumnHeader("fileSize", DataTypes.LONG));
//            ds.addColumn(new ColumnHeader("fileType", DataTypes.STRING));
//            ds.addColumn(new ColumnHeader("savePath", DataTypes.STRING));
//
//            while (files.hasMoreElements())
//            {
//                String name = (String)files.nextElement();
//
//                String fileName = multi.getFilesystemName(name);
//                String type		= multi.getContentType(name);
//
//                File f = multi.getFile(name);
//                int row = ds.newRow(0);
//                ds.set(row, "fileName", fileName);
//                ds.set(row, "fileType", type);
//
//                if (f != null)
//                {
//                    ds.set(row, "savePath", f.getPath());
//                    ds.set(row, "fileSize", f.length());
//                }
//            }
//
//            resData.addDataSet(ds);
//            resVarList.add("ErrorCode", 0);
//            resVarList.add("ErrorMsg", "Success" );
//        }
//        catch (Exception e)
//        {
//            logger.debug(e.getMessage());
//            DataSet dsError = new DataSet("ds_error");
//            dsError.addColumn(new ColumnHeader("ErrorMsg", DataTypes.STRING));
//            dsError.addColumn(new ColumnHeader("ErrorCode", DataTypes.LONG));
//
//            dsError.set(dsError.newRow(), "ErrorCode", -1);
//            dsError.set(dsError.newRow(), "ErrorMsg", e.getMessage());
//
//            resData.addDataSet(dsError);
//        }
//
//        platformResponse.setData(resData);
//        platformResponse.sendData();
//
////        HttpPlatformResponse res = new HttpPlatformResponse(response, request);
////        res.setContentType(platformType);
////        res.setCharset("UTF-8");
////        res.setData(resData);
////        res.sendData();
//    }
//}
