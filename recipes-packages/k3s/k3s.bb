SUMMARY = "Production-Grade Container Scheduling and Management"
DESCRIPTION = "Lightweight Kubernetes, intended to be a fully compliant Kubernetes."
HOMEPAGE = "https://k3s.io/"
LICENSE = "CLOSED"
SRC_URI = "https://github.com/k3s-io/k3s/releases/download/v1.28.3+k3s2/k3s-arm64"
SRC_URI[sha256sum] = "cd85b904fb7662a290190a0af249af559e94e5765361d926c669bac52074ad95"
SRC_URI += "file://k3s.conf"
FILES_${PN} += "/usr/local/bin/k3s /etc/modules-load.d/k3s.conf"
# Remove warning about binary being stripped, we know.
INSANE_SKIP_${PN} += "already-stripped"

do_install() {
  bbwarn "Install K3S"
  chmod +x ${WORKDIR}/k3s-arm64
  install -d ${D}/usr/local/bin/
  cp ${WORKDIR}/k3s-arm64 ${D}/usr/local/bin/k3s
  bbwarn "Add load modules for K3S"
  install -m 0755 ${WORKDIR}/k3s.conf -D ${D}/etc/modules-load.d/k3s.conf
}

