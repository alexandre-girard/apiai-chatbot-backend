package fr.tm.ima.pocs.chatbot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//LOGGER.debug("Ajout d'un resourceHandler vers /resources");
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
	}

}
