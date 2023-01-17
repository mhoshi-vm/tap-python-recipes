package jp.vmware.tanzu.pythonfuncconvention.model;

import io.kubernetes.client.openapi.models.V1PodTemplateSpec;

public record PodConventionContextStatus(V1PodTemplateSpec template, String[] appliedConventions) {
}
