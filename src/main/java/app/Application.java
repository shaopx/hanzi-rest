package app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan(basePackages = {
        "controller", "app", "data"})
public class Application extends SpringBootServletInitializer {

    @Autowired
    private HanZiRepository repository;

    @Autowired
    private TestcollectionsRepository testDataRepository;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);


    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);


    }

}
