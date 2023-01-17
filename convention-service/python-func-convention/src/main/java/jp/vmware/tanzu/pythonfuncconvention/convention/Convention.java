package jp.vmware.tanzu.pythonfuncconvention.convention;

import jp.vmware.tanzu.pythonfuncconvention.model.PodConventionContextSpec;
import jp.vmware.tanzu.pythonfuncconvention.model.PodConventionContextStatus;

public interface Convention {

	PodConventionContextStatus handler(PodConventionContextSpec podConventionContextSpec,
			PodConventionContextStatus podConventionContextStatus);

}
