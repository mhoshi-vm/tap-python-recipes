package jp.vmware.tanzu.pythonfuncconvention.model;

import io.kubernetes.client.openapi.models.V1ObjectMeta;

public record PodConventionContext(String apiVersion, String kind, V1ObjectMeta metadata, PodConventionContextSpec spec,
		PodConventionContextStatus status) {
}
