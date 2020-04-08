package com.tobesoft.overseas.nexacro17.demo.config;

import com.nexacro17.xeni.services.GridExportImportServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class ServletRegistrationBeenConfig {

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                // set nexacro-xeni context params
                servletContext.setInitParameter("export-path", "/src/main/webapp/export");
                servletContext.setInitParameter("import-path", "/src/main/webapp/import");
                servletContext.setInitParameter("monitor-cycle-time", "3");
                servletContext.setInitParameter("file-storage-time", "1");
                servletContext.setInitParameter("numFmt-lang", "ko");
            }
        };
    }

    @Bean
    public ServletRegistrationBean nexacroExcelExportImportServletRegistrationBean() {
        return new ServletRegistrationBean(new GridExportImportServlet(), "/XExportImport", "/XImport");
    }
}
