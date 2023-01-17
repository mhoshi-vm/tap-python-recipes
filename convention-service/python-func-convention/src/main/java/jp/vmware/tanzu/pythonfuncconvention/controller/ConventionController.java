package jp.vmware.tanzu.pythonfuncconvention.controller;

import jp.vmware.tanzu.pythonfuncconvention.convention.Convention;
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
	public PodConventionContext Convention(@RequestBody PodConventionContext podConventionContext) {

		PodConventionContextStatus status = this.convention.handler(podConventionContext.spec(),
				podConventionContext.status());

		return new PodConventionContext(podConventionContext.apiVersion(), podConventionContext.kind(),
				podConventionContext.metadata(), podConventionContext.spec(), status);
	}

}
