package com.xz.ignite.web;

import com.xz.ignite.web.filter.EncodingFilter;
import com.xz.ignite.web.restful.BaseRestful;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by Administrator on 2020/1/10.
 */
public class Start {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        SelectChannelConnector serverConnector = new SelectChannelConnector();
        serverConnector.setPort(8080);
        server.addConnector(serverConnector);

        ResourceConfig resourceConfig = new ResourceConfig(BaseRestful.class);

        //Jersey类ServletContainer从HttpServlet继承,故可传入Jetty类ServletContextHandler.addServlet方法
        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder("rest",servletContainer) ;

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addFilter(new FilterHolder(EncodingFilter.class),"/*", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addServlet(servletHolder, "/*");

        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
