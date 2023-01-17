SOURCE_IMAGE = os.getenv("SOURCE_IMAGE", default='harbor.lespaulstudioplus.info/library/my-convention')
LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')

k8s_custom_deploy(
    'my-convention',
    apply_cmd="tanzu apps workload apply my-convention --live-update=true" +
               " --local-path " + LOCAL_PATH +
               " --source-image " + SOURCE_IMAGE +
               " --type web" +
               " --build-env BP_JVM_VERSION=17" +
               " --yes >/dev/null" +
               " && kubectl get workload my-convention -o yaml",
    delete_cmd="tanzu apps workload delete my-convention --yes",
    deps=['pom.xml', './target/classes'],
    container_selector='workload',
    live_update=[
      sync('./target/classes', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('my-convention', port_forwards=["8080:8080"],
            extra_pod_selectors=[{'serving.knative.dev/service': 'my-convention'}])
allow_k8s_contexts('tap-full')