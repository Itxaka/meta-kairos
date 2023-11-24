# bring systemd so we can use SYSTEMD_SERVICE_${PN} to enable our services
inherit systemd

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
SRC_URI[sha256sum] = "db112a2c6215dc8d556ea07baa8d931e25169b64576c5f00bdad4fea9eff12af"

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

# Remove warning about binary being stripped, we know.
INSANE_SKIP_${PN} += "already-stripped"

# Enable services
SYSTEMD_SERVICE_${PN} = " \
    cos-setup-reconcile.timer \
    cos-setup-fs.service \
    cos-setup-boot.service \
    cos-setup-network.service \
    cos-setup-initramfs.service \
    cos-setup-rootfs.service \
"

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
