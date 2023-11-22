DESCRIPTION = "Kairos agent"
SRC_URI = "https://github.com/kairos-io/kairos-agent/releases/download/v2.4.1-qcs6490/kairos-agent-v2.4.1-qcs6490-Linux-arm64.tar.gz"
SRC_URI += "file://10_c6490.yaml"
SRC_URI += "file://cos-setup-reconcile.timer"
SRC_URI += "file://cos-setup-reconcile.service"
SRC_URI += "file://cos-setup-fs.service"
SRC_URI += "file://cos-setup-boot.service"
SRC_URI += "file://cos-setup-network.service"
SRC_URI += "file://cos-setup-initramfs.service"
SRC_URI += "file://cos-setup-rootfs.service"
SRC_URI += "file://kairos-agent.service"
SRC_URI += "file://motd"
SRC_URI += "file://bash.bashrc.local"
SRC_URI += "file://cos-setup-reconcile"
# No idea how the license checksum works, this just disables it completely
LICENSE = "CLOSED"
# Disable url checksum
SRC_URI[sha256sum] = "6674c4cddfce36279c72b1bec5ef98798760c3e9aedfa5435101684f5c4e4f0e"
FILES_${PN} += "${base_bindir}/kairos-agent \
    /oem/10_c6490.yaml \
    /etc/systemd/system/cos-setup-reconcile.timer \
    /etc/systemd/system/cos-setup-reconcile.service \
    /etc/systemd/system/cos-setup-fs.service \
    /etc/systemd/system/cos-setup-boot.service \
    /etc/systemd/system/cos-setup-network.service \
    /etc/systemd/system/cos-setup-initramfs.service \
    /etc/systemd/system/cos-setup-rootfs.service \
    /etc/systemd/system/kairos-agent.service \
    /etc/motd \
    /etc/bash.bashrc.local \
    /usr/bin/cos-setup-reconcile \
"

pkg_postinst_${PN} += "${do_postinstall_enable_services}"
# Remove warning about binary being stripped, we know.
INSANE_SKIP_${PN} += "already-stripped"

do_install () {
    install -d -p ${D}/etc
    install -d -p ${D}${base_bindir}
    install -d -p ${D}/oem
    install -d -p ${D}/etc/systemd/system/
    install -d -p ${D}/usr/bin/
    cp ${WORKDIR}/kairos-agent ${D}${base_bindir}/kairos-agent
    chown root.root ${D}${base_bindir}/kairos-agent
    # install other files
    install -m 0644 -p ${WORKDIR}/bash.bashrc.local ${D}/etc/bash.bashrc.local
    install -m 0644 -p ${WORKDIR}/motd ${D}/etc/motd
    install -m 0755 -p ${WORKDIR}/cos-setup-reconcile ${D}/usr/bin/cos-setup-reconcile
    # install oem file specific for the board
    install -m 0755 -p ${WORKDIR}/10_c6490.yaml ${D}/oem/10_c6490.yaml
    # Install services and link them
    install -m 0644 -p ${WORKDIR}/cos-setup-reconcile.timer ${D}/etc/systemd/system/cos-setup-reconcile.timer
    install -m 0644 -p ${WORKDIR}/cos-setup-reconcile.service ${D}/etc/systemd/system/cos-setup-reconcile.service
    install -m 0644 -p ${WORKDIR}/cos-setup-fs.service ${D}/etc/systemd/system/cos-setup-fs.service
    install -m 0644 -p ${WORKDIR}/cos-setup-boot.service ${D}/etc/systemd/system/cos-setup-boot.service
    install -m 0644 -p ${WORKDIR}/cos-setup-network.service ${D}/etc/systemd/system/cos-setup-network.service
    install -m 0644 -p ${WORKDIR}/cos-setup-initramfs.service ${D}/etc/systemd/system/cos-setup-initramfs.service
    install -m 0644 -p ${WORKDIR}/cos-setup-rootfs.service ${D}/etc/systemd/system/cos-setup-rootfs.service
    install -m 0644 -p ${WORKDIR}/kairos-agent.service ${D}/etc/systemd/system/kairos-agent.service
}


do_postinstall_enable_services() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        systemctl --root=$D enable cos-setup-reconcile.timer
        systemctl --root=$D enable cos-setup-fs.service
        systemctl --root=$D enable cos-setup-boot.service
        systemctl --root=$D enable cos-setup-network.service
        systemctl --root=$D enable cos-setup-initramfs.service
        systemctl --root=$D enable cos-setup-rootfs.service
    fi
}

