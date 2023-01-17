package jp.vmware.tanzu.pythonfuncconvention.model;

import io.kubernetes.client.openapi.models.V1PodTemplateSpec;

public record PodConventionContextSpec(V1PodTemplateSpec template, ImageConfig[] imageConfig) {
}
