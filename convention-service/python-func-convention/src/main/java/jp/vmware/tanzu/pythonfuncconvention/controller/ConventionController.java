package jp.vmware.tanzu.pythonfuncconvention.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.kubernetes.client.custom.IntOrString;
import jp.vmware.tanzu.pythonfuncconvention.convention.Convention;
import jp.vmware.tanzu.pythonfuncconvention.model.IntOrStringSerializer;
import jp.vmware.tanzu.pythonfuncconvention.model.PodConventionContext;
import jp.vmware.tanzu.pythonfuncconvention.model.PodConventionContextStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConventionController {

	Convention convention;

	public ConventionController(Convention convention) {
		this.convention = convention;
	}

	@PostMapping(value = "/convention", produces = MediaType.APPLICATION_JSON_VALUE)
	public PodConventionContext Convention(@RequestBody PodConventionContext podConventionContext) throws JsonProcessingException {

		PodConventionContextStatus status = this.convention.handler(podConventionContext.spec(),
				podConventionContext.status());

		PodConventionContext podConventionContext1 = new PodConventionContext(podConventionContext.apiVersion(), podConventionContext.kind(),
				podConventionContext.metadata(), podConventionContext.spec(), status);

		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();

		module.addSerializer(IntOrString.class, new IntOrStringSerializer());
		mapper.registerModule(module);

		String indentedString = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(podConventionContext1);

		System.out.println(indentedString);

		/*
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(new PodConventionContext(podConventionContext.apiVersion(), podConventionContext.kind(),
				podConventionContext.metadata(), podConventionContext.spec(), status))*/
		return new PodConventionContext(podConventionContext.apiVersion(), podConventionContext.kind(),
				podConventionContext.metadata(), podConventionContext.spec(), status);
	}

}
