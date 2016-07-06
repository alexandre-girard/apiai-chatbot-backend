package fr.tm.ima.pocs.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
//@PropertySource(value = "file:///${confIma}/chatbot-demo/parametrage.properties")
public class ChatbotDemoApplication extends SpringBootServletInitializer {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotDemoApplication.class);
	
	/**
	 * Classe de l'application.
	 */
	private static Class<ChatbotDemoApplication> applicationClass = ChatbotDemoApplication.class;

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	/**
	 * Déclaration de la servlet Dispatcher.
	 *
	 * @return
	 */
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	/**
	 * JBOSS 7.1 specific: you need to map dispatcherServlet to /* .
	 *
	 * @return ServletRegistrationBean
	 */
	@Bean
	public ServletRegistrationBean dispatcherServletRegistration() {
		LOGGER.info("Initialisation spécifique de l'application pour JBOSS 7.1");

		ServletRegistrationBean registration = new ServletRegistrationBean(
				dispatcherServlet(), "/*");
		registration
				.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
		return registration;
	}

	/**
	 * Execution avec Serveur Embarqué.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
	    System.setProperty("http.proxyHost", "proxyweb.ima.intra");
        System.setProperty("http.proxyPort", "3128");
        System.setProperty("https.proxyHost", "proxyweb.ima.intra");
        System.setProperty("https.proxyPort", "3128");
	    
		SpringApplication.run(applicationClass, args);
	}
}
