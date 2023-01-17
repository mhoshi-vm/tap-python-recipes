package jp.vmware.tanzu.pythonfuncconvention.convention;

import io.kubernetes.client.openapi.models.V1PodTemplateSpec;
import jp.vmware.tanzu.pythonfuncconvention.model.PodConventionContextSpec;
import jp.vmware.tanzu.pythonfuncconvention.model.PodConventionContextStatus;
import org.springframework.stereotype.Component;

@Component
public class DoNothingConvention implements Convention {

	@Override
	public PodConventionContextStatus handler(PodConventionContextSpec podConventionContextSpec,
			PodConventionContextStatus podConventionContextStatus) {

		String[] appliedConventions = { "do-nothing" };

		V1PodTemplateSpec podTemplateSpec = podConventionContextSpec.template();

		return new PodConventionContextStatus(podTemplateSpec, appliedConventions);
	}

}
