package jp.vmware.tanzu.pythonfuncconvention.model;

import com.fasterxml.jackson.databind.JsonNode;

public record ImageConfig(String image, BOM[] boms, JsonNode config) {
}
