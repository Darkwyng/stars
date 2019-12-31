package com.pim.stars.gadget.imp;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@EnableConfigurationProperties(GadgetProperties.class)
@ConfigurationProperties(prefix = "gadget")
@Getter
@Setter
public class GadgetProperties {

	private List<String> gadgetFilePaths = Arrays.asList("com/pim/stars/gadget/imp/gadgets.xml");
	private List<String> hullFilePaths = Arrays.asList("com/pim/stars/gadget/imp/hulls.xml");
}
