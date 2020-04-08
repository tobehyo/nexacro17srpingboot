package com.tobesoft.overseas.nexacro17.demo.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

// ctrl + shift + o
@RestController
public class NexacroCheckLicenseController {
	@RequestMapping("/services/license")
    public void getNexacroLicenseInfo(HttpServletResponse response) throws IOException {
        OutputStream out = response.getOutputStream();
        new com.nexacro17.xapi.util.JarInfo().info(out);
    }

}
