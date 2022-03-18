package com.jccorp.tma;

//import com.jccorp.tma.health.TemplateHealthCheck;
import com.jccorp.tma.model.dao.TaskDAO;
import com.jccorp.tma.model.entity.Task;
import com.jccorp.tma.resources.TaskResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class ServiceTaskManagementApplication extends Application<ServiceTaskManagementConfiguration> {
    public static void main(String[] args) throws Exception {
        new ServiceTaskManagementApplication().run(args);
    }

    private final HibernateBundle<ServiceTaskManagementConfiguration> hibernateBundle =
            new HibernateBundle<>(Task.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(ServiceTaskManagementConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };


    @Override
    public String getName() {
        return "task-management-be";
    }

    @Override
    public void initialize(Bootstrap<ServiceTaskManagementConfiguration> bootstrap) {

        bootstrap.addBundle(new MigrationsBundle<>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ServiceTaskManagementConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);

        bootstrap.addBundle(new SwaggerBundle<>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ServiceTaskManagementConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });

    }

    @Override
    public void run(ServiceTaskManagementConfiguration configuration,
                    Environment environment) {
        final TaskDAO dao = new TaskDAO(hibernateBundle.getSessionFactory());


        final TaskResource resource = new TaskResource(
                dao);

        //final TemplateHealthCheck healthCheck =
        //        new TemplateHealthCheck(configuration.getTemplate());
        //environment.healthChecks().register("template", healthCheck);
        //environment.healthChecks().register("database", new DatabaseHealthCheck(database));

        environment.jersey().register(resource);

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }
}
