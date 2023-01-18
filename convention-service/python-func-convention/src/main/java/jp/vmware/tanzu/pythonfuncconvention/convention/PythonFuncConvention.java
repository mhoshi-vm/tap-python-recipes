package jp.vmware.tanzu.pythonfuncconvention.convention;

import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1HTTPGetAction;
import io.kubernetes.client.openapi.models.V1PodTemplateSpec;
import io.kubernetes.client.openapi.models.V1Probe;
import jp.vmware.tanzu.pythonfuncconvention.model.BOM;
import jp.vmware.tanzu.pythonfuncconvention.model.ImageConfig;
import jp.vmware.tanzu.pythonfuncconvention.model.PodConventionContextSpec;
import jp.vmware.tanzu.pythonfuncconvention.model.PodConventionContextStatus;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Primary
public class PythonFuncConvention implements Convention {

	@Override
	public PodConventionContextStatus handler(PodConventionContextSpec podConventionContextSpec,
			PodConventionContextStatus podConventionContextStatus) {
		String[] appliedConventions = { "addPythonFuncConvention" };

		V1PodTemplateSpec podTemplateSpec = podConventionContextSpec.template();
		ImageConfig[] imageConfigs = podConventionContextSpec.imageConfig();


		for (ImageConfig imageConfig : imageConfigs) {
			BOM[] boms = imageConfig.boms();
			for (BOM bom : boms) {
				String jsonRaw = new String(bom.raw());
				JsonParser springParser = JsonParserFactory.getJsonParser();
				Map<String, Object> bomObj = springParser.parseMap(jsonRaw);
				if (bomObj.get("Artifacts") != null) {
					List<Object> artifacts = (List<Object>) bomObj.get("Artifacts");
					for (Object artifact : artifacts) {
						if (artifact instanceof Map) {
							Map<String, Object> artifactMap = (Map<String, Object>) artifact;

							if (artifactMap.get("Name") != null
									&& artifactMap.get("Name").equals("Python Invoker Deps")) {
								List<Object> cpes = (List<Object>) artifactMap.get("CPEs");

								for (Object cpe : cpes) {
									if (cpe.toString().contains("fedora-infrastructure:flask-healthz")
											&& podTemplateSpec.getSpec() != null
											&& podTemplateSpec.getSpec().getContainers() != null
											&& podTemplateSpec.getSpec().getContainers().size() > 0) {
										V1Probe liveness = new V1Probe();

										V1HTTPGetAction livenessHttp = new V1HTTPGetAction();
										livenessHttp.setPath("/health/live");
										livenessHttp.setPort(new IntOrString("http"));

										liveness.setHttpGet(livenessHttp);
										liveness.setInitialDelaySeconds(10);
										liveness.setPeriodSeconds(3);
										liveness.setFailureThreshold(20);

										V1Probe readiness = new V1Probe();
										V1HTTPGetAction readinessHttp = new V1HTTPGetAction();
										readinessHttp.setPath("/health/ready");
										readinessHttp.setPort(new IntOrString("http"));

										readiness.setHttpGet(readinessHttp);
										readiness.setInitialDelaySeconds(10);
										readiness.setPeriodSeconds(3);
										readiness.setFailureThreshold(20);


										V1Container container = podTemplateSpec.getSpec().getContainers().get(0);

										/*container.setLivenessProbe(liveness);*/

										container.setReadinessProbe(readiness);

									}
								}
							}
						}
					}
				}
			}
		}

		return new PodConventionContextStatus(podTemplateSpec, appliedConventions);
	}

}
