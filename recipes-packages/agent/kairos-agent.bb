DESCRIPTION = "Kairos agent"
SRC_URI = "https://github.com/Itxaka/kairos-agent/releases/download/v2.5.6-qcs6490/kairos-agent-v2.5.6-qcs6490-Linux-arm64.tar.gz"
SRC_URI += "file://10_c6490.yaml"
SRC_URI += "file://cos-setup-reconcile.timer"
SRC_URI += "file://cos-setup-reconcile.service"
SRC_URI += "file://cos-setup-fs.service"
SRC_URI += "file://cos-setup-boot.service"
SRC_URI += "file://cos-setup-network.service"
# No idea how the license checksum works, this just disables
LICENSE = "CLOSED"
# Disable url checksum
BB_STRICT_CHECKSUM = "0"
FILES_${PN} += "${base_bindir}/kairos-agent \
    /oem/10_c6490.yaml \
    /etc/systemd/system/cos-setup-reconcile.timer \
    /etc/systemd/system/cos-setup-reconcile.service \
    /etc/systemd/system/cos-setup-fs.service \
    /etc/systemd/system/cos-setup-boot.service \
    /etc/systemd/system/cos-setup-network.service"

pkg_postinst_${PN} += "${do_postinstall_enable_services}"
DEPENDS += "parted gptfdisk e2fsprogs util-linux rsync sudo"


do_install () {
    install -d ${D}${base_bindir}
    cp ${WORKDIR}/kairos-agent ${D}${base_bindir}/kairos-agent
    chown root.root ${D}${base_bindir}/kairos-agent
    # install oem file specific for the board
    install -d -p ${D}/oem
    install -m 0755 ${WORKDIR}/10_c6490.yaml ${D}/oem/10_c6490.yaml
    # Install services and link them
    install -d -p ${D}/etc/systemd/system/
    install -m 0644 ${WORKDIR}/cos-setup-reconcile.timer ${D}/etc/systemd/system/cos-setup-reconcile.timer
    install -m 0644 ${WORKDIR}/cos-setup-reconcile.service ${D}/etc/systemd/system/cos-setup-reconcile.service
    install -m 0644 ${WORKDIR}/cos-setup-fs.service ${D}/etc/systemd/system/cos-setup-fs.service
    install -m 0644 ${WORKDIR}/cos-setup-boot.service ${D}/etc/systemd/system/cos-setup-boot.service
    install -m 0644 ${WORKDIR}/cos-setup-network.service ${D}/etc/systemd/system/cos-setup-network.service
}


do_postinstall_enable_services() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        systemctl --root=$D enable cos-setup-reconcile.timer
        systemctl --root=$D enable cos-setup-fs.service
        systemctl --root=$D enable cos-setup-boot.service
        systemctl --root=$D enable cos-setup-network.service
    fi
}

